package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class Controller {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField txtUsername;

    @FXML
    private Label lblInfo;

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

    }

    @FXML
    public void initialize() {
        borderPane.setCenter(new GamePane());
    }
}
