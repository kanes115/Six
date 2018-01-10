package gui.buttons;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

import javax.swing.*;

/**
 * Created by michaello on 10.01.18.
 */
public class GameButton extends Button {
    static final String STYLE_NORMAL = "-fx-background-color: white; -fx-padding: 0;";
    static final String STYLE_PRESSED = "-fx-background-color: grey; -fx-padding: 0;";


    public void setChecked(boolean value) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(50), this);
        double scale = value ? 0.9 : 1.0;
        transition.setToX(scale);
        transition.setToY(scale);
        transition.play();
    }
}
