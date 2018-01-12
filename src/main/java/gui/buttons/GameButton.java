package gui.buttons;

import game.Positions.Position;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Created by michaello on 10.01.18.
 */
public class GameButton extends Button {
    static final String STYLE_NORMAL = "-fx-background-color: white; -fx-padding: 0;";
    static final String STYLE_PRESSED = "-fx-background-color: grey; -fx-padding: 0;";

    private Position position;

    public GameButton(Position position, double layoutX, double layoutY, double width, double height, String imageUrl) {
        this.position = position;
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(imageUrl)));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        setGraphic(imageView);
        setStyle(STYLE_NORMAL);

        setOnMousePressed(e -> setStyle(STYLE_PRESSED));
        setOnMouseReleased(e -> setStyle(STYLE_NORMAL));
        setLayoutX(layoutX);
        setLayoutY(layoutY);
    }

    public Position getPosition() {
        return position;
    }

    public void setChecked(boolean value) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(50), this);
        double scale = value ? 0.9 : 1.0;
        transition.setToX(scale);
        transition.setToY(scale);
        transition.play();
    }
}
