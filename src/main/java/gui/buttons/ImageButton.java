package gui.buttons;

import gui.Card;
import gui.Row;
import gui.buttons.GameButton;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ImageButton extends GameButton {

    private Card card;
    private Row row;

    public ImageButton(Card card, Row row, double layoutX, double layoutY, double width, double height) {
        super();
        this.card = card;
        this.row = row;
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

    public Row getRow() {
        return row;
    }

    public void move(double toX, double toY) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(100), this);
        transition.setToX(toX);
        transition.setToY(toY);
        transition.play();
    }
}