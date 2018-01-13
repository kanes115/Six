package gui.fxcontrollers;

import com.google.inject.Guice;
import com.google.inject.Injector;
import game.GameController;
import game.MoveChecker;
import game.MoveResponse;
import game.Moves.*;
import game.Positions.CasualPosition;
import game.Positions.DeckPosition;
import game.Positions.Position;
import game.Positions.RejectedPosition;
import game.TimerModule;
import gui.GamePane;
import gui.ImagePathsFactory;
import gui.Row;
import gui.buttons.GameButton;
import gui.buttons.ImageButton;
import gui.buttons.StackButton;
import hints.NormalTimer;
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
        if((move = initializeDeckToMatrixMove(checkedImageButtons)) == null){
            showAlertDialog("Błędny ruch", "Nie mozna zainicjalizowac ruchu", null);
            return;
        }

        MoveResponse response = gameController.tryMove(move);
        if (response.wasOk()) {
            //TODO consider another implementation (observer)
            reloadAllImages();
            GamePane gamePane = getGamePane();
            gamePane.getCardFromStack().setImage(null);
            clearWholeList(checkedImageButtons);
        }else {
            showAlertDialog("Błędny ruch", response.getErrorMessage(), null);
        }
    }


    @FXML
    public void btnDeleteDuplicateOnAction(ActionEvent actionEvent) {
        LinkedList<GameButton> checkedImageButtons = (LinkedList<GameButton>) getListCard();
        if (!checkNumberOfChoosenCards(checkedImageButtons, 1)) {
            clearList(checkedImageButtons);
            return;
        }

        if (!checkButtonType(StackButton.class, checkedImageButtons.get(0))) {
            showAlertDialog("Błędny ruch", "Zaznaczona karta musi byc z talli lub stosu kart odrzuconych", null);
            clearList(checkedImageButtons);
            return;
        }


        GameButton button = checkedImageButtons.get(0);
        Move move = new DeleteDuplicate(button.getPosition(), gameController.getBoard());

        MoveResponse response = gameController.tryMove(move);
        if (response.wasOk()) {
            //TODO consider another implementation (observer)
            reloadAllImages();
            getGamePane().getCardFromStack().setImage(null);
            clearWholeList(checkedImageButtons);
        }else {
            showAlertDialog("Błędny ruch", response.getErrorMessage(), null);
        }

    }

    @FXML
    public void btnDeleteUnnecessaryPairOnAction(ActionEvent actionEvent) {
        LinkedList<GameButton> checkedImageButtons = (LinkedList<GameButton>) getListCard();

        if (!checkNumberOfChoosenCards(checkedImageButtons, 2)) {
            clearList(checkedImageButtons);
            return;
        }

        if (!checkButtonType(ImageButton.class, checkedImageButtons.get(0), checkedImageButtons.get(1))) {
            showAlertDialog("Błędny ruch", "Zaznaczone karty muszą być brane z planszy", null);
            clearList(checkedImageButtons);
            return;
        }

        ImageButton first = (ImageButton) checkedImageButtons.remove();
        ImageButton second = (ImageButton) checkedImageButtons.remove();

        Move move = new DeleteUnnecessaryPair(second.getPosition(), first.getPosition());
        MoveResponse response = gameController.tryMove(move);

        if (response.wasOk()) {
            reloadImage(second);
            reloadImage(first);

        } else {
            showAlertDialog("Błędny ruch", response.getErrorMessage(), null);
        }

        second.setChecked(false);
        first.setChecked(false);
    }

    @FXML
    public void btnInsideMatrixRelocationOnAction(ActionEvent actionEvent) {
        LinkedList<GameButton> checkedImageButtons = (LinkedList<GameButton>) getListCard();

        if (!checkNumberOfChoosenCards(checkedImageButtons, 2)) {
            clearList(checkedImageButtons);
            return;
        }

        if (!checkButtonType(ImageButton.class, checkedImageButtons.get(0), checkedImageButtons.get(1))) {
            showAlertDialog("Błędny ruch", "Zaznaczone karty muszą być brane z planszy", null);
            clearList(checkedImageButtons);
            return;
        }


        ImageButton first = (ImageButton) checkedImageButtons.remove();
        ImageButton second = (ImageButton) checkedImageButtons.remove();
        Move move = new InsideMatrixRelocation(first.getPosition(), second.getPosition(), getGameController().getBoard());

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
            showAlertDialog("Błędny ruch", response.getErrorMessage(), null);
        }

        second.setChecked(false);
        first.setChecked(false);
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
        System.out.println(gameController.getTime());
        borderPane.setCenter(new GamePane());

    }

    private void showAlertDialog(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    private List<GameButton> getListCard() {
        GamePane gamePane = getGamePane();
        return gamePane.getCheckedImageButtons();
    }

    private GamePane getGamePane(){
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
        if(!getGamePane().getRejectedCards().getStackPosition().isEmpty()) {
            reloadImage(getGamePane().getRejectedCards());
        }

        List<Row> rows = getGamePane().getGuiRows();
        for (Row row:rows){
            List<ImageButton> cards = row.getCards();
            for(ImageButton card : cards){
                reloadImage(card);
            }
        }
    }

    private void clearList(List<GameButton> checkedImageButtons) {
        for (int i=0; i<checkedImageButtons.size(); i++){
            if (checkedImageButtons.get(i) instanceof ImageButton){
                checkedImageButtons.get(i).setChecked(false);
                checkedImageButtons.remove(i);
            }
        }
    }

    private void clearWholeList(List<GameButton> checkedImageButtons) {
        for (int i=0;i <checkedImageButtons.size(); i++){
            checkedImageButtons.get(i).setChecked(false);
        }
        checkedImageButtons.clear();
    }

    private boolean checkNumberOfChoosenCards(List<GameButton> buttons, int expectedNumber) {
        if (expectedNumber == buttons.size()) return true;
        if (expectedNumber == 2) {
            showAlertDialog("Błędny ruch", "Nie zaznaczono dwóch kart", "Zaznacz dwie kartyss");
        } else if (expectedNumber == 1) {
            showAlertDialog("Błędny ruch", "Nie zaznaczono jednej karty", "Zaznacz wyłącznie jedną kartę");
        }
        clearList(buttons);
        return false;
    }

    private boolean checkButtonType(Class<?> buttonType, GameButton... buttons) {
        for (GameButton button : buttons) {
            if (button.getClass() != buttonType) {
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
        if(firstPosition instanceof DeckPosition && secondPosition instanceof RejectedPosition){
            move = new DeckToMatrix((DeckPosition) firstPosition, (RejectedPosition)secondPosition, getGameController().getBoard());
        }else if(firstPosition instanceof DeckPosition && secondPosition instanceof CasualPosition){
            move = new DeckToMatrix((DeckPosition) firstPosition, (CasualPosition) secondPosition, getGameController().getBoard());
        }else if(firstPosition instanceof RejectedPosition && secondPosition instanceof CasualPosition) {
            move = new DeckToMatrix((RejectedPosition) firstPosition, (CasualPosition) secondPosition, getGameController().getBoard());
        }
        return move;
    }

}
