package gui.buttons;

import game.Positions.StackPosition;
import gui.GamePane;
import gui.GuiTools;
import gui.ImagePathsFactory;
import javafx.scene.image.ImageView;

/**
 * Created by michaello on 14.01.18.
 */
public class RejectedCardsStackButton extends StackButton {

    public RejectedCardsStackButton(StackPosition stackPosition, double layoutX, double layoutY, double width, double height, String imageUrl, GamePane gamePane) {
        super(stackPosition, layoutX, layoutY, width, height, imageUrl, gamePane);
    }

    public RejectedCardsStackButton(StackPosition stackPosition, double layoutX, double layoutY, double width, double height, GamePane gamePane) {
        this(stackPosition, layoutX, layoutY, width, height, ImagePathsFactory.getPathToCardImage(stackPosition), gamePane);
    }


    @Override
    public void reloadImage() {
        String imageUrl = null;

        imageUrl = ImagePathsFactory.getPathToCardImage(getPosition());

        ImageView imageView = GuiTools.createImageView(getWidth(), getHeight(), imageUrl);
        setGraphic(imageView);
    }
}
