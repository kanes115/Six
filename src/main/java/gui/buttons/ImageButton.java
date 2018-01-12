package gui.buttons;

import game.Positions.CasualPosition;
import gui.ImagePathsFactory;
import gui.Row;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class ImageButton extends GameButton {

    private Row row;

    public ImageButton(CasualPosition position, Row row, double layoutX, double layoutY, double width, double height, String imageURL) {
        super(position, layoutX, layoutY, width, height, imageURL);
        this.row = row;
    }

    public ImageButton(CasualPosition position, Row row, double layoutX, double layoutY, double width, double height) {
        this(position, row, layoutX, layoutY, width, height, ImagePathsFactory.getPathToCardImage(position.getCard()));
    }

    public CasualPosition getPosition() {
        return (CasualPosition) super.getPosition();
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