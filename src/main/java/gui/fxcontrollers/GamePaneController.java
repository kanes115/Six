package gui.fxcontrollers;

import gui.Card;
import gui.EmptyImageViewCard;
import gui.GamePane;
import gui.buttons.ImageButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.util.Queue;

public class GamePaneController {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField txtUsername;

    @FXML
    private Label lblInfo;

    @FXML
    public void btnPerformDeckToMatrixOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void btnDeleteDuplicateOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void btnDeleteUnnecessaryPairOnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void btnNewGameOnAction() {

    }

    @FXML
    public void btnRevertMoveOnAction() {
    }

    @FXML
    public void btnHintOnAction() {

    }

    @FXML
    public void btnPerformMoveOnAction() {
        GamePane gamePane = (GamePane) borderPane.getCenter();
        Queue<ImageButton> checkedImageButtons = gamePane.getCheckedImageButtons();

        if(checkedImageButtons.size() != GamePane.CARDS_IN_MOVE){
            showAlertDialog("Błędny ruch", "Nie zaznaczono dwóch kart - nie można wykonać ruchu", "Zaznacz dwie karty");
            return;
        }

        ImageButton endButton = checkedImageButtons.poll();
        ImageButton startButton = checkedImageButtons.poll();

        //Only for demonstration - in future it will be checked in game module
        if(startButton.getCard().equals(endButton.getCard())){
            setEmptyCard(startButton);
            setEmptyCard(endButton);
        }else{
            swapCard(startButton,endButton);
        }

        startButton.setChecked(false);
        endButton.setChecked(false);
    }

    @FXML
    public void initialize() {
        borderPane.setCenter(new GamePane());
    }

    private void showAlertDialog(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }


    private void setEmptyCard(ImageButton  button) {
        button.setGraphic(EmptyImageViewCard.getEmptyCardImage());
        button.setCard(Card.CARD_EMPTY);
    }

    //Just for demonstration
    private void swapCard(ImageButton firstButton, ImageButton secondButton) {
        Node firstImage = firstButton.getGraphic();
        Card firstCard = firstButton.getCard();

        firstButton.setGraphic(secondButton.getGraphic());
        firstButton.setCard(secondButton.getCard());

        secondButton.setGraphic(firstImage);
        secondButton.setCard(firstCard);
    }

}
