package gui.fxcontrollers;

import game.GameController;
import game.MoveResponse;
import game.Moves.*;
import game.Positions.CasualPosition;
import game.Positions.DeckPosition;
import game.Positions.Position;
import game.Positions.RejectedPosition;
import game.State;
import gui.GamePane;
import gui.GuiTools;
import gui.ImagePathsFactory;
import gui.Main;
import gui.buttons.ButtonList;
import gui.buttons.GameButton;
import gui.buttons.CardButton;
import gui.buttons.StackButton;
import gui.dictionary.AppConstants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class GamePaneController {

    private static GameController gameController;
    private ButtonList cardsChosenByUser;
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
                }else{
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
        Move move;
        if ((move = initializeDeckToMatrixMove(cardsChosenByUser)) == null) {
            GuiTools.showAlertDialog("Błędny ruch", "Nie mozna zainicjalizowac ruchu", null);
            return;
        }

        MoveResponse response = gameController.tryMove(move);
        if (response.wasOk()) {
            //TODO consider another implementation (observer)
            reloadAllImages();
            GamePane gamePane = getGamePane();
            gamePane.getTakenCardFromStack().setImage(null);
            cardsChosenByUser.clearWholeList();
        } else {
            GuiTools.showAlertDialog("Błędny ruch", response.getErrorMessage(), null);
            cardsChosenByUser.clearWholeListExceptDeckButton();
        }
        checkFinishGameStatus();
    }

    @FXML
    public void btnDeleteDuplicateOnAction(ActionEvent actionEvent) {
        if (!checkNumberOfChoosenCards(cardsChosenByUser, 1)) {
            cardsChosenByUser.clearWholeListExceptDeckButton();
            return;
        }

        if (!checkButtonType(StackButton.class, cardsChosenByUser.get(0))) {
            GuiTools.showAlertDialog("Błędny ruch", "Zaznaczona karta musi byc z talli  lub stosu kart odrzuconych", null);
            cardsChosenByUser.clearWholeListExceptDeckButton();
            return;
        }


        GameButton button = cardsChosenByUser.get(0);
        Move move = new DeleteDuplicate(button.getPosition());

        MoveResponse response = gameController.tryMove(move);
        if (response.wasOk()) {
            //TODO consider another implementation (observer)
            reloadAllImages();
            getGamePane().getTakenCardFromStack().setImage(null);
            cardsChosenByUser.clearWholeList();
        } else {
            GuiTools.showAlertDialog("Błędny ruch", response.getErrorMessage(), null);
            cardsChosenByUser.clearWholeListExceptDeckButton();
        }
        checkFinishGameStatus();

    }

    @FXML
    public void btnDeleteUnnecessaryPairOnAction(ActionEvent actionEvent) {
        if (!checkNumberOfChoosenCards(cardsChosenByUser, 2)) {
            cardsChosenByUser.clearWholeListExceptDeckButton();
            return;
        }

        GameButton first = cardsChosenByUser.get(0);
        GameButton second = cardsChosenByUser.get(1);

        Move move = new DeleteUnnecessaryPair(second.getPosition(), first.getPosition());
        MoveResponse response = gameController.tryMove(move);

        if (response.wasOk()) {
            second.reloadImage();
            first.reloadImage();
            getGamePane().getTakenCardFromStack().setImage(null);
            cardsChosenByUser.clearWholeList();
        } else {
            GuiTools.showAlertDialog("Błędny ruch", response.getErrorMessage(), null);
            cardsChosenByUser.clearWholeListExceptDeckButton();
        }
        checkFinishGameStatus();

    }

    @FXML
    public void btnInsideMatrixRelocationOnAction(ActionEvent actionEvent) {
        if (!checkNumberOfChoosenCards(cardsChosenByUser, 2)) {
            cardsChosenByUser.clearWholeListExceptDeckButton();
            return;
        }

        if (!checkButtonType(CardButton.class, cardsChosenByUser.get(0), cardsChosenByUser.get(1))) {
            GuiTools.showAlertDialog("Błędny ruch", "Zaznaczone karty muszą być brane z planszy", null);
            cardsChosenByUser.clearWholeListExceptDeckButton();
            return;
        }


        CardButton first = (CardButton) cardsChosenByUser.get(0);
        CardButton second = (CardButton) cardsChosenByUser.get(1);

        Move move = new InsideMatrixRelocation(first.getPosition(), second.getPosition());
        MoveResponse response = gameController.tryMove(move);

        if (response.wasOk()) {
            second.reloadImage();
            first.reloadImage();

            game.Row gameRow = second.getPosition().getRow();
            gui.Row guiRow = second.getRow();
            if (!guiRow.isColorChoosen() && gameRow.isColorAssigned()) {
                guiRow.getColorImage().setImage(new Image(getClass().getResourceAsStream("/gui/cards/" + gameRow.getColor().name().toLowerCase() + ".png")));
                guiRow.setColorChoosen(true);
            }

        } else {
            GuiTools.showAlertDialog("Błędny ruch", response.getErrorMessage(), null);
        }
        cardsChosenByUser.clearWholeListExceptDeckButton();
        checkFinishGameStatus();

    }

    @FXML
    public void btnAssignColorOnCard(ActionEvent actionEvent) {
        if (!checkNumberOfChoosenCards(cardsChosenByUser, 1)) {
            cardsChosenByUser.clearWholeListExceptDeckButton();
            return;
        }

        if (!checkButtonType(CardButton.class, cardsChosenByUser.get(0))) {
            GuiTools.showAlertDialog("Błędny ruch", "Zaznaczona karta musi być brana z planszy", null);
            cardsChosenByUser.clearWholeListExceptDeckButton();
            return;
        }
        CardButton first = (CardButton) cardsChosenByUser.get(0);
        Move move = new AssignColorOnCard(first.getPosition());
        MoveResponse response = gameController.tryMove(move);

        if (response.wasOk()) {
            first.reloadImage();

            game.Row gameRow = first.getPosition().getRow();
            gui.Row guiRow = first.getRow();
            if (!guiRow.isColorChoosen() && gameRow.isColorAssigned()) {
                guiRow.getColorImage().setImage(new Image(getClass().getResourceAsStream("/gui/cards/" + gameRow.getColor().name().toLowerCase() + ".png")));
                guiRow.setColorChoosen(true);
            }

        } else {
            GuiTools.showAlertDialog("Błędny ruch", response.getErrorMessage(), null);
        }

        cardsChosenByUser.clearWholeListExceptDeckButton();
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
        borderPane.setCenter(new GamePane(cardsChosenByUser));
    }

    private GamePane getGamePane() {
        return (GamePane) borderPane.getCenter();
    }


    private void reloadAllImages() {
        GamePane gamePane = getGamePane();
        gamePane.getRejectedCards().reloadImage();
        gamePane.getDeck().reloadImage();
        gamePane.getGuiRows().forEach(
                row -> row.getCards().forEach(btn -> btn.reloadImage())
        );
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

    private boolean checkButtonType(Class<?> buttonType, GameButton... buttons) {
        for (GameButton button : buttons) {
            if (!buttonType.isInstance(button)) {
                return false;
            }
        }
        return true;
    }

    private Move initializeDeckToMatrixMove(ButtonList cardsChosenByUser) {
        GameButton first = cardsChosenByUser.get(0);
        GameButton second = cardsChosenByUser.get(1);

        Position firstPosition = first.getPosition();
        Position secondPosition = second.getPosition();

        Move move = null;
        if (firstPosition instanceof DeckPosition && secondPosition instanceof RejectedPosition) {
            move = new FromStack((DeckPosition) firstPosition, (RejectedPosition) secondPosition);
        } else if (firstPosition instanceof DeckPosition && secondPosition instanceof CasualPosition) {
            move = new FromStack((DeckPosition) firstPosition, (CasualPosition) secondPosition);
        } else if (firstPosition instanceof RejectedPosition && secondPosition instanceof CasualPosition) {
            move = new FromStack((RejectedPosition) firstPosition, (CasualPosition) secondPosition);
        }
        return move;

    }
}
