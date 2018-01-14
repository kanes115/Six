package gui.fxcontrollers;

import game.GameController;
import game.MoveResponse;
import game.Moves.*;
import game.Positions.CasualPosition;
import game.Positions.DeckPosition;
import game.Positions.Position;
import game.Positions.RejectedPosition;
import game.State;
import gui.*;
import gui.buttons.DeckStackButton;
import gui.buttons.GameButton;
import gui.buttons.ImageButton;
import gui.buttons.StackButton;
import gui.dictionary.AppConstants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.util.LinkedList;
import java.util.List;

public class GamePaneController {

    private static GameController gameController;

    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField txtUsername;
    @FXML
    private Label lblInfo;

    public static GameController getGameController() {
        return gameController;
    }

    @FXML
    public void btnDeckToMatrixOnAction(ActionEvent actionEvent) {
        LinkedList<GameButton> checkedImageButtons = (LinkedList<GameButton>) getListCard();
        if (!checkNumberOfChoosenCards(checkedImageButtons, 2)) {
            return;
        }
        Move move;
        if ((move = initializeDeckToMatrixMove(checkedImageButtons)) == null) {
            GuiTools.showAlertDialog("Błędny ruch", "Nie mozna zainicjalizowac ruchu", null);
            return;
        }

        MoveResponse response = gameController.tryMove(move);
        if (response.wasOk()) {
            //TODO consider another implementation (observer)
            reloadAllImages();
            GamePane gamePane = getGamePane();
            gamePane.getCardFromStack().setImage(null);
            clearWholeList(checkedImageButtons);
        } else {
            GuiTools.showAlertDialog("Błędny ruch", response.getErrorMessage(), null);
            clearList(checkedImageButtons);
        }
        checkFinishGameStatus();
    }


    @FXML
    public void btnDeleteDuplicateOnAction(ActionEvent actionEvent) {
        LinkedList<GameButton> checkedImageButtons = (LinkedList<GameButton>) getListCard();
        if (!checkNumberOfChoosenCards(checkedImageButtons, 1)) {
            clearList(checkedImageButtons);
            return;
        }

        if (!checkButtonType(StackButton.class, checkedImageButtons.get(0))) {
            GuiTools.showAlertDialog("Błędny ruch", "Zaznaczona karta musi byc z talli  lub stosu kart odrzuconych", null);
            clearList(checkedImageButtons);
            return;
        }


        GameButton button = checkedImageButtons.get(0);
        Move move = new DeleteDuplicate(button.getPosition());

        MoveResponse response = gameController.tryMove(move);
        if (response.wasOk()) {
            //TODO consider another implementation (observer)
            reloadAllImages();
            getGamePane().getCardFromStack().setImage(null);
            clearWholeList(checkedImageButtons);
        } else {
            GuiTools.showAlertDialog("Błędny ruch", response.getErrorMessage(), null);
            clearList(checkedImageButtons);
        }
        checkFinishGameStatus();

    }

    @FXML
    public void btnDeleteUnnecessaryPairOnAction(ActionEvent actionEvent) {
        LinkedList<GameButton> checkedImageButtons = (LinkedList<GameButton>) getListCard();

        if (!checkNumberOfChoosenCards(checkedImageButtons, 2)) {
            clearList(checkedImageButtons);
            return;
        }

        GameButton first = checkedImageButtons.get(0);
        GameButton second = checkedImageButtons.get(1);

        Move move = new DeleteUnnecessaryPair(second.getPosition(), first.getPosition());
        MoveResponse response = gameController.tryMove(move);

        if (response.wasOk()) {
            second.reloadImage();
            first.reloadImage();
            getGamePane().getCardFromStack().setImage(null);
            clearWholeList(checkedImageButtons);
        } else {
            GuiTools.showAlertDialog("Błędny ruch", response.getErrorMessage(), null);
            clearList(checkedImageButtons);
        }
        checkFinishGameStatus();

    }

    @FXML
    public void btnInsideMatrixRelocationOnAction(ActionEvent actionEvent) {
        LinkedList<GameButton> checkedImageButtons = (LinkedList<GameButton>) getListCard();

        if (!checkNumberOfChoosenCards(checkedImageButtons, 2)) {
            clearList(checkedImageButtons);
            return;
        }

        if (!checkButtonType(ImageButton.class, checkedImageButtons.get(0), checkedImageButtons.get(1))) {
            GuiTools.showAlertDialog("Błędny ruch", "Zaznaczone karty muszą być brane z planszy", null);
            clearList(checkedImageButtons);
            return;
        }


        ImageButton first =  (ImageButton) checkedImageButtons.get(0);
        ImageButton second = (ImageButton)  checkedImageButtons.get(1);

        Move move = new InsideMatrixRelocation(first.getPosition(), second.getPosition());
        MoveResponse response = gameController.tryMove(move);

        if (response.wasOk()) {
            reloadImage(second);
            reloadImage(first);

            game.Row gameRow = second.getPosition().getRow();
            gui.Row guiRow = second.getRow();
            if (!guiRow.isColorChoosen() && gameRow.isColorAssigned()) {
                guiRow.getColorImage().setImage(new Image(getClass().getResourceAsStream("/gui/cards/" + gameRow.getColor().name().toLowerCase() + ".png")));
                guiRow.setColorChoosen(true);
            }

        } else {
            GuiTools.showAlertDialog("Błędny ruch", response.getErrorMessage(), null);
        }
        clearList(checkedImageButtons);
        checkFinishGameStatus();

    }

    @FXML
    public void btnAssignColorOnCard(ActionEvent actionEvent) {
        LinkedList<GameButton> checkedImageButtons = (LinkedList<GameButton>) getListCard();
        if (!checkNumberOfChoosenCards(checkedImageButtons, 1)) {
            clearList(checkedImageButtons);
            return;
        }

        if (!checkButtonType(ImageButton.class, checkedImageButtons.get(0))) {
            GuiTools.showAlertDialog("Błędny ruch", "Zaznaczona karta musi być brana z planszy", null);
            clearList(checkedImageButtons);
            return;
        }
        ImageButton first =  (ImageButton) checkedImageButtons.get(0);
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

        clearList(checkedImageButtons);
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
        gameController = new GameController(false);
        borderPane.setCenter(new GamePane());
    }


    public static void checkFinishGameStatus() {
        try {
            if (getGameController().getGameState().equals(State.WON)) {
                GuiTools.showAlertDialog("Wygrana", "Wygrałeś grę, Ave ty :D", null);
                Main.replaceStage(AppConstants.INTRO_STAGE);

            } else if (getGameController().getGameState().equals(State.LOST)) {
                GuiTools.showAlertDialog("Przegrana", "Przegrałeś Grę,", null);
                Main.replaceStage(AppConstants.INTRO_STAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<GameButton> getListCard() {
        GamePane gamePane = getGamePane();
        return gamePane.getCheckedImageButtons();
    }

    private GamePane getGamePane() {
        return (GamePane) borderPane.getCenter();
    }

    private void reloadImage(GameButton button) {
        String imageUrl = ImagePathsFactory.getPathToCardImage(button.getPosition());

        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(imageUrl)));
        imageView.setFitWidth(button.getWidth());
        imageView.setFitHeight(button.getHeight());
        button.setGraphic(imageView);
    }


    private void reloadAllImages() {
        GamePane gamePane= getGamePane();
        gamePane.getRejectedCards().reloadImage();
        gamePane.getDeck().reloadImage();
        getGamePane().getGuiRows().forEach(
                row -> row.getCards().forEach(btn -> btn.reloadImage())
        );
    }

    private void clearList(List<GameButton> checkedImageButtons) {
        checkedImageButtons.stream()
                .filter(btn -> !(btn instanceof DeckStackButton))
                .forEach(btn -> btn.setChecked(false));
        checkedImageButtons.removeIf(btn -> !(btn instanceof DeckStackButton));


    }

    private void clearWholeList(List<GameButton> checkedImageButtons) {
        checkedImageButtons.stream()
                .forEach(btn -> btn.setChecked(false));

        checkedImageButtons.clear();
    }

    private boolean checkNumberOfChoosenCards(List<GameButton> buttons, int expectedNumber) {
        if (expectedNumber == buttons.size()) return true;
        if (expectedNumber == 2) {
            GuiTools.showAlertDialog("Błędny ruch", "Nie zaznaczono dwóch kart", "Zaznacz dwie karty");
        } else if (expectedNumber == 1) {
            GuiTools.showAlertDialog("Błędny ruch", "Nie zaznaczono dokładnie jednej karty", "Zaznacz wyłącznie jedną kartę");
        }
        clearList(buttons);
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

    private Move initializeDeckToMatrixMove(LinkedList<GameButton> checkedImageButtons) {
        GameButton first = checkedImageButtons.get(0);
        GameButton second = checkedImageButtons.get(1);

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
