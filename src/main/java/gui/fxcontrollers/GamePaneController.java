package gui.fxcontrollers;

import game.GameController;
import game.State;
import gui.GamePane;
import gui.GuiTools;
import gui.Main;
import gui.MoveExecutor;
import gui.buttons.ButtonList;
import gui.dictionary.AppConstants;
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
                    GuiTools.showAlertDialog("Przegrana", "Przegrałeś Grę,", null);
                } else {
                    GuiTools.showAlertDialog("Wygrana", "Wygrałeś grę, Ave ty :D", null);
                }
                Main.replaceStage(AppConstants.INTRO_STAGE_URL);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void btnDeckToMatrixOnAction(ActionEvent actionEvent) {
        if (!checkNumberOfChoosenCards(cardsChosenByUser, 2)) {
            return;
        }
        moveExecutor.performFromStackMove();
        checkFinishGameStatus();
    }

    @FXML
    public void btnDeleteDuplicateOnAction(ActionEvent actionEvent) {
        if (!checkNumberOfChoosenCards(cardsChosenByUser, 1)) {
            cardsChosenByUser.clearWholeListExceptDeckButton();
            return;
        }
        moveExecutor.performDeleteDuplicateMove();
        checkFinishGameStatus();
    }

    @FXML
    public void btnDeleteUnnecessaryPairOnAction(ActionEvent actionEvent) {
        if (!checkNumberOfChoosenCards(cardsChosenByUser, 2)) {
            cardsChosenByUser.clearWholeListExceptDeckButton();
            return;
        }
        moveExecutor.performDeleteUnnecessaryPairMove();
        checkFinishGameStatus();

    }

    @FXML
    public void btnInsideMatrixRelocationOnAction(ActionEvent actionEvent) {
        if (!checkNumberOfChoosenCards(cardsChosenByUser, 2)) {
            cardsChosenByUser.clearWholeListExceptDeckButton();
            return;
        }
        moveExecutor.performInsideMatrixRelocationMove();
        checkFinishGameStatus();
    }

    @FXML
    public void btnAssignColorOnCard(ActionEvent actionEvent) {
        if (!checkNumberOfChoosenCards(cardsChosenByUser, 1)) {
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

    private boolean checkNumberOfChoosenCards(ButtonList buttons, int expectedNumber) {
        if (expectedNumber == buttons.size()) return true;
        if (expectedNumber == 2) {
            GuiTools.showAlertDialog("Błędny ruch", "Nie zaznaczono dwóch kart", "Zaznacz dwie karty");
        } else if (expectedNumber == 1) {
            GuiTools.showAlertDialog("Błędny ruch", "Nie zaznaczono dokładnie jednej karty", "Zaznacz wyłącznie jedną kartę");
        }
        buttons.clearWholeListExceptDeckButton();
        return false;
    }
}
