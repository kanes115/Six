package gui;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

public class Controller {

    @FXML
    private Button testButton;

    @FXML
    public void testButtonOnAction() {
        testButton.setText("Works!");
    }
}
