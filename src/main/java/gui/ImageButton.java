package gui;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ImageButton extends Button {

    private static final String STYLE_NORMAL = "-fx-background-color: white; -fx-padding: 0;";
    private static final String STYLE_PRESSED = "-fx-background-color: grey; -fx-padding: 0;";

    private Card card;

    public ImageButton(Card card, double layoutX, double layoutY, double width, double height) {
        this.card = card;
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(card.getPathToFilename())));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        setGraphic(imageView);
        setStyle(STYLE_NORMAL);

        setOnMousePressed(e -> setStyle(STYLE_PRESSED));
        setOnMouseReleased(e -> setStyle(STYLE_NORMAL));
        setLayoutX(layoutX);
        setLayoutY(layoutY);
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void move(double toX, double toY) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(100), this);
        transition.setToX(toX);
        transition.setToY(toY);
        transition.play();
    }

    public void setChecked(boolean value) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(50), this);
        double scale = value ? 0.9 : 1.0;
        transition.setToX(scale);
        transition.setToY(scale);
        transition.play();
    }

}