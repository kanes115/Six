package gui.fxcontrollers;

import game.GameController;
import game.Moves.DeleteUnnecessaryPair;
import game.Moves.InsideMatrixRelocation;
import game.Moves.Move;
import gui.GamePane;
import gui.ImagePathsFactory;
import gui.buttons.GameButton;
import gui.buttons.ImageButton;
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

    public static GameController getGameController() {
        return gameController;
    }

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField txtUsername;

    @FXML
    private Label lblInfo;

    @FXML
    public void btnDeckToMatrixOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void btnDeleteDuplicateOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void btnDeleteUnnecessaryPairOnAction(ActionEvent actionEvent) {
        LinkedList<GameButton> checkedImageButtons = (LinkedList<GameButton>) getListCard();

        if (!checkNumberOfChoosenCards(checkedImageButtons,2)){
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
        if (gameController.tryMove(move)){
            reloadImage(second);
            reloadImage(first);

        }else {
            //TODO Dialog message should be provided by module game (optional feature)
            showAlertDialog("Błędny ruch", "Coś nie poszlo", null);
        }

        second.setChecked(false);
        first.setChecked(false);
    }

    @FXML
    public void btnInsideMatrixRelocationOnAction(ActionEvent actionEvent) {
        LinkedList<GameButton> checkedImageButtons = (LinkedList<GameButton>) getListCard();

        if (!checkNumberOfChoosenCards(checkedImageButtons,2)){
            return;
        }

        ImageButton first = (ImageButton) checkedImageButtons.remove();
        ImageButton second = (ImageButton) checkedImageButtons.remove();
        Move move = new InsideMatrixRelocation(first.getPosition(), second.getPosition());

        if (gameController.tryMove(move)){
            reloadImage(second);
            reloadImage(first);

        }else {
            showAlertDialog("Błędny ruch", "Coś nie poszlo", null);
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
        gameController = new GameController(new NormalTimer(),false);
        System.out.println(gameController.getTime());
        borderPane.setCenter(new GamePane());

    }

    private void showAlertDialog(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

    private  List<GameButton> getListCard(){
        GamePane gamePane = (GamePane) borderPane.getCenter();
        return gamePane.getCheckedImageButtons();
    }

    private void reloadImage(ImageButton button){
        String imageUrl = ImagePathsFactory.getPathToCardImage(button.getPosition());

        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(imageUrl)));
        imageView.setFitWidth(button.getWidth());
        imageView.setFitHeight(button.getHeight());
        button.setGraphic(imageView);
    }

    private void clearList(List<GameButton> checkedImageButtons) {
        checkedImageButtons.clear();
    }

    private boolean checkNumberOfChoosenCards(List<GameButton> buttons, int expectedNumber){
        if(expectedNumber == buttons.size()) return true;
        if (expectedNumber == 2){
            showAlertDialog("Błędny ruch", "Nie zaznaczono dwóch kart", "Zaznacz dwie kartyss");
        }else if (expectedNumber == 1){
            showAlertDialog("Błędny ruch", "Nie zaznaczono jednej karty", "Zaznacz wyłącznie jedną kartę");
        }
        clearList(buttons);
        return false;
    }

    private boolean checkButtonType(Class <?> buttonType, GameButton ... buttons){
        for (GameButton button: buttons){
            if (buttonType != button.getClass()){
                return false;
            }
        }
        return true;
    }

}
