package gui.fxcontrollers;

import game.GameController;
import game.MoveResponse;
import game.Moves.Move;
import game.Positions.CasualPosition;
import game.Positions.Position;
import game.State;
import gui.GamePane;
import gui.GuiTools;
import gui.Main;
import gui.MoveExecutor;
import gui.buttons.ButtonList;
import gui.buttons.CardButton;
import gui.buttons.GameButton;
import gui.dictionary.AppConstants;
import gui.i18n.CodesI18n;
import gui.i18n.I18n;
import hints.HintsPositions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

import java.util.List;
import java.util.Map;

import static gui.buttons.GameButton.STYLE_HINTED;

public class GamePaneController {

    private static GameController gameController;
    private static ButtonList cardsChosenByUser;
    private static MoveExecutor moveExecutor;

    @FXML
    private Label currentResult = new Label();
    @FXML
    private Button btnAssignColorToRow = new Button();
    @FXML
    private Button btnInsideMatrixRelocation = new Button();
    @FXML
    private Button btnDeleteUnnecessaryPair = new Button();
    @FXML
    private Button btnDeleteDuplicate = new Button();
    @FXML
    private Button btnDeckToMatrix = new Button();

    @FXML
    private MenuButton hintsMenu = new MenuButton();
    @FXML
    private MenuItem hintsDuplicate = new MenuItem();
    @FXML
    private MenuItem hintsUnnecessaryPair = new MenuItem();
    @FXML
    private MenuItem hintsActionable = new MenuItem();
    @FXML
    private Button btnNewGame = new Button();
    @FXML
    private Button btnRevertMove = new Button();
    @FXML
    private Label userNickname = new Label();

    private HintsPositions hints;
    private GamePane gamePane;
    private Map<Position, GameButton> positionsToButton;

    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField txtUsername;
    @FXML
    private Label lblInfo;

    public static GameController getGameController() {
        return gameController;
    }

    public static void checkFinishGameStatus() {
        I18n i18n = I18n.getInstance();
        try {
            if (getGameController().getGameState().equals(State.WON) || (getGameController().getGameState().equals(State.LOST))) {
                if (getGameController().getGameState().equals(State.LOST)) {
                    GuiTools.showAlertDialog(i18n.getString(CodesI18n.DEFEAT), i18n.getString(CodesI18n.YOU_LOST_GAME), null);
                } else {
                    GuiTools.showAlertDialog(i18n.getString(CodesI18n.VICTORY), i18n.getString(CodesI18n.YOU_WIN_GAME), null);
                }
                Main.replaceStage(AppConstants.INTRO_STAGE_URL);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean makeDeletePairOrInsideMatrixRelocationMove(Move move) {
        MoveResponse response = gameController.tryMove(move);

        if (response.wasOk()) {
            cardsChosenByUser.get(0).reloadImage();
            cardsChosenByUser.get(1).reloadImage();
            assignRow((CardButton) cardsChosenByUser.get(1));

        } else {
            GuiTools.showAlertDialog(I18n.getInstance().getString(CodesI18n.INCORRECT_MOVE), response.getErrorMessage(), null);
        }
        cardsChosenByUser.clearWholeListExceptDeckButton();
        return response.wasOk();
    }

    private static void assignRow(CardButton button) {
        game.Row gameRow = button.getPosition().getRow();
        gui.Row guiRow = button.getRow();
        if (!guiRow.isColorChoosen() && gameRow.isColorAssigned()) {
            guiRow.getColorImage().setImage(new Image(AppConstants.PATH_TO_CARDS_IMAGES + gameRow.getColor().name().toLowerCase() + ".png"));
            guiRow.setColorChoosen(true);
        }
    }

    @FXML
    public void btnDeckToMatrixOnAction(ActionEvent actionEvent) {
        if (!checkNumberOfChosenCards(cardsChosenByUser, 2)) {
            return;
        }
        moveExecutor.performFromStackMove();
        checkFinishGameStatus();
    }

    @FXML
    public void btnDeleteDuplicateOnAction(ActionEvent actionEvent) {
        if (!checkNumberOfChosenCards(cardsChosenByUser, 1)) {
            cardsChosenByUser.clearWholeListExceptDeckButton();
            return;
        }
        moveExecutor.performDeleteDuplicateMove();
        checkFinishGameStatus();
    }

    @FXML
    public void btnDeleteUnnecessaryPairOnAction(ActionEvent actionEvent) {
        if (!checkNumberOfChosenCards(cardsChosenByUser, 2)) {
            cardsChosenByUser.clearWholeListExceptDeckButton();
            return;
        }
        moveExecutor.performDeleteUnnecessaryPairMove();
        checkFinishGameStatus();

    }

    @FXML
    public void btnInsideMatrixRelocationOnAction(ActionEvent actionEvent) {
        if (!checkNumberOfChosenCards(cardsChosenByUser, 2)) {
            cardsChosenByUser.clearWholeListExceptDeckButton();
            return;
        }
        moveExecutor.performInsideMatrixRelocationMove();
        checkFinishGameStatus();
    }

    @FXML
    public void btnAssignColorOnCard(ActionEvent actionEvent) {
        if (!checkNumberOfChosenCards(cardsChosenByUser, 1)) {
            cardsChosenByUser.clearWholeListExceptDeckButton();
            return;
        }

        moveExecutor.performAssignColorOnRowMove();
        checkFinishGameStatus();
    }

    @FXML
    public void showActionable(ActionEvent actionEvent) {
        List<Position> positions = hints.getActionables();
        positions.forEach(pos -> positionsToButton.get(pos).setStyle(STYLE_HINTED));

    }

    @FXML
    public void showUnnecessaryPair(ActionEvent actionEvent) {
        List<Position[]> positions = hints.getUnnecessaryPairs();

        for (int i = 0; i < positions.size(); i++) {
            Position[] possArray = positions.get(i);
            for (int j = 0; j < possArray.length; j++) {
                positionsToButton.get(possArray[j]).setStyle(STYLE_HINTED);
            }
        }
    }

    @FXML
    public void showDuplicates(ActionEvent actionEvent) {
        List<CasualPosition> positions = hints.getDeletableDuplicates();
        positions.forEach(pos -> positionsToButton.get(pos).setStyle(STYLE_HINTED));

    }

    @FXML
    public void btnNewGameOnAction() {
        initialize();
    }

    @FXML
    public void btnRevertMoveOnAction() {
    }

    @FXML
    public void btnHintOnAction() {

    }

    @FXML
    public void initialize() {
        loadI18n();
        cardsChosenByUser = new ButtonList();
        gameController = new GameController(false);
        gamePane = new GamePane(cardsChosenByUser);
        positionsToButton = gamePane.getPositionsToButtons();
        borderPane.setCenter(gamePane);

        moveExecutor = new MoveExecutor(cardsChosenByUser, gamePane, gameController);
        hints = new HintsPositions(gameController.getBoard());
    }

    private void loadI18n() {
        I18n i18n = I18n.getInstance();
        currentResult.setText(i18n.getString(CodesI18n.CURRENT_RESULT));
        btnInsideMatrixRelocation.setText(i18n.getString(CodesI18n.BTN_INSIDE_MATRIX_RELOCATION));
        btnDeleteUnnecessaryPair.setText(i18n.getString(CodesI18n.BTN_DELETE_UNNECESSARY_PAIRS));
        btnDeleteDuplicate.setText(i18n.getString(CodesI18n.BTN_DELETE_DUPLICATE));
        btnDeckToMatrix.setText(i18n.getString(CodesI18n.BTN_DECK_TO_MATRIX));

        hintsMenu.setText(i18n.getString(CodesI18n.HINTS_MENU));
        hintsDuplicate.setText(i18n.getString(CodesI18n.HINTS_DUPLICATES));
        hintsUnnecessaryPair.setText(i18n.getString(CodesI18n.HINTS_UNNECESSARY_PAIRS));
        hintsActionable.setText(i18n.getString(CodesI18n.HINTS_ACTIONABLE));
        btnNewGame.setText(i18n.getString(CodesI18n.BTN_NEWGAME));
        btnRevertMove.setText(i18n.getString(CodesI18n.BTN_REVERT_MOVE));
        userNickname.setText(i18n.getString(CodesI18n.USER_NICKNAME));
        btnAssignColorToRow.setText(i18n.getString(CodesI18n.ASSIGN_COLOR_TO_ROW));
    }

    private boolean checkNumberOfChosenCards(ButtonList buttons, int expectedNumber) {
        I18n i18n = I18n.getInstance();
        if (expectedNumber == buttons.size()) return true;
        if (expectedNumber == 2) {
            GuiTools.showAlertDialog(i18n.getString(CodesI18n.INCORRECT_MOVE), i18n.getString(CodesI18n.REQUIRED_TWO_CARDS), i18n.getString(CodesI18n.SELECT_TWO_CARDS));
        } else if (expectedNumber == 1) {
            GuiTools.showAlertDialog(i18n.getString(CodesI18n.INCORRECT_MOVE), i18n.getString(CodesI18n.REQUIRED_EXACTLY_ONE_CARD), i18n.getString(CodesI18n.SELECT_EXACTLY_ONE_CARD));
        }
        buttons.clearWholeListExceptDeckButton();
        return false;
    }

}
