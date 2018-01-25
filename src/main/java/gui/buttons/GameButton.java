package gui.buttons;

import game.Positions.Position;
import gui.GuiTools;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Created by michaello on 10.01.18.
 */
public abstract class GameButton extends Button {
    public static final String STYLE_NORMAL = "-fx-background-color: white; -fx-padding: 0;";
    public static final String STYLE_PRESSED = "-fx-background-color: grey; -fx-padding: 0;";
    public static final String STYLE_HINTED = "-fx-background-color: yellow; -fx-padding: 0;";

    private Position position;

    public GameButton(Position position, double layoutX, double layoutY, double width, double height, String imageUrl) {
        this.position = position;
        ImageView imageView = GuiTools.createImageView(width, height, imageUrl);

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

    abstract public void reloadImage();
}
