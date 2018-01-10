package gui.buttons;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Created by michaello on 10.01.18.
 */
public class StackButton extends GameButton {

    public StackButton(double layoutX, double layoutY, double width, double height, String imageUrl) {
        super();
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
}
