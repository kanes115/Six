package gui.buttons;

import game.Positions.Position;
import game.Positions.StackPosition;
import gui.GuiTools;
import gui.ImagePathsFactory;
import gui.dictionary.AppConstants;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by michaello on 14.01.18.
 */
public class RejectedCardsStackButton extends StackButton{

    public RejectedCardsStackButton(StackPosition stackPosition, double layoutX, double layoutY, double width, double height, String imageUrl) {
        super(stackPosition, layoutX, layoutY, width, height, imageUrl);
    }

    public RejectedCardsStackButton(StackPosition stackPosition, double layoutX, double layoutY, double width, double height) {
        this(stackPosition, layoutX, layoutY, width, height, ImagePathsFactory.getPathToCardImage(stackPosition));
    }


    @Override
    public void reloadImage() {
        String imageUrl = null;

        imageUrl = ImagePathsFactory.getPathToCardImage(getPosition());

        ImageView imageView = GuiTools.createImageView(getWidth(), getHeight(), imageUrl);
        setGraphic(imageView);
    }
}
