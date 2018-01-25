package gui.buttons;

import game.Positions.Position;
import gui.GamePane;
import gui.GuiTools;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Created by michaello on 10.01.18.
 */
public abstract class GameButton extends Button {
    public static final String STYLE_NORMAL = "-fx-background-color: white;  -fx-padding: 0;";
    public static final String STYLE_PRESSED = "-fx-background-color: grey;  -fx-padding: 0;";
    public static final String STYLE_HINTED = "-fx-background-color: yellow;  -fx-padding: 0;";

    final GamePane gamePane;
    final ButtonList cardChosenByUser;
    final Position position;
    ImageView cardImageView;
    double previousX, previousY;
    private boolean isChecked = false;

    public GameButton(Position position, double layoutX, double layoutY, double width, double height, String imageUrl, GamePane gamePane) {
        this.position = position;
        ImageView imageView = GuiTools.createImageView(width, height, imageUrl);
        this.gamePane = gamePane;
        this.cardChosenByUser = gamePane.getCardsChosenByUser();

        setGraphic(imageView);
        setStyle(STYLE_NORMAL);

        setOnMousePressed(e -> setStyle(STYLE_PRESSED));
        setLayoutX(layoutX);
        setLayoutY(layoutY);

    }

    public Position getPosition() {
        return position;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean value) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(50), this);
        double scale = value ? 0.9 : 1.0;
        transition.setToX(scale);
        transition.setToY(scale);
        transition.play();
        isChecked = value;
        if (!value) {
            setStyle(STYLE_NORMAL);
        }
    }

    abstract public void reloadImage();
}
