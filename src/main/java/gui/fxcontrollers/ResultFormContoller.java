package gui.fxcontrollers;

import game.GameController;

import gui.GuiTools;
import gui.Main;
import gui.dictionary.AppConstants;
import hints.Highscores;
import hints.HighscoresHTTP;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import score.Score;

/**
 * Created by michaello on 19.01.18.
 */
public class ResultFormContoller {

    public TextField txtUsername;
    public Label resultContent;
    public Label resultLabel;
    public Label timeLabel;
    public Label timeContent;
    private final GameController gameController = GamePaneController.getGameController();
    private final HighscoresHTTP highscoresHTTP = new HighscoresHTTP();

    @FXML
    public void handleSendButtonAction(ActionEvent actionEvent) {
        if(txtUsername.getText().isEmpty()){
            GuiTools.showAlertDialog("Brak nazwy uzytkownika", "Podaj nazwe uzytkownika", null);
            return;
        }
        highscoresHTTP.addScore(new Score(txtUsername.getText(), gameController.getTime()));
        replaceStage();
    }

    @FXML
    public void handleAbortButtonAction(ActionEvent actionEvent) {

        replaceStage();
    }

    @FXML
    public void initialize(){

        //  resultContent.setText();
        timeContent.setText(String.valueOf(gameController.getTime()));
    }

    private void replaceStage(){
        try {
            Main.replaceStage(AppConstants.INTRO_STAGE_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
