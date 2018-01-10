package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class IntroController {

    @FXML
    private BorderPane borderPane;

    @FXML
    public void btnRunGameOnAction(ActionEvent actionEvent) throws Exception {
        Main.replaceStage("mainScene.fxml");
    }
}
