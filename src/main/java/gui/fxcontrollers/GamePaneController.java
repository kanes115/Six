package gui.fxcontrollers;

import game.GameController;
import game.State;
import gui.GamePane;
import gui.GuiTools;
import gui.Main;
import gui.MoveExecutor;
import gui.buttons.ButtonList;
import gui.dictionary.AppConstants;
import gui.i18n.CodesI18n;
import gui.i18n.I18n;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class GamePaneController {

    private static GameController gameController;
    private ButtonList cardsChosenByUser;
    private MoveExecutor moveExecutor;

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
        try {
            if (getGameController().getGameState().equals(State.WON) || (getGameController().getGameState().equals(State.LOST))) {
                if (getGameController().getGameState().equals(State.LOST)) {
                    GuiTools.showAlertDialog(I18n.getString(CodesI18n.DEFEAT), I18n.getString(CodesI18n.YOU_LOST_GAME), null);
                } else {
                    GuiTools.showAlertDialog(I18n.getString(CodesI18n.VICTORY), I18n.getString(CodesI18n.YOU_WIN_GAME), null);
                }
                Main.replaceStage(AppConstants.INTRO_STAGE_URL);
            }

        } catch (Exception e) {
            e.printStackTrace();
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
        cardsChosenByUser = new ButtonList();
        gameController = new GameController(false);
        GamePane gamePane = new GamePane(cardsChosenByUser);
        borderPane.setCenter(gamePane);
        moveExecutor = new MoveExecutor(cardsChosenByUser, gamePane, gameController);
    }

    private boolean checkNumberOfChosenCards(ButtonList buttons, int expectedNumber) {
        if (expectedNumber == buttons.size()) return true;
        if (expectedNumber == 2) {
            GuiTools.showAlertDialog(I18n.getString(CodesI18n.INCORRECT_MOVE), I18n.getString(CodesI18n.REQUIRED_TWO_CARDS), I18n.getString(CodesI18n.SELECT_TWO_CARDS));
        } else if (expectedNumber == 1) {
            GuiTools.showAlertDialog(I18n.getString(CodesI18n.INCORRECT_MOVE), I18n.getString(CodesI18n.REQUIRED_EXACTLY_ONE_CARD), I18n.getString(CodesI18n.SELECT_EXACTLY_ONE_CARD));
        }
        buttons.clearWholeListExceptDeckButton();
        return false;
    }
}
