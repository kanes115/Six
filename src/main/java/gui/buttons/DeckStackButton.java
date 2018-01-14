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
public class DeckStackButton extends StackButton {

    public DeckStackButton(StackPosition stackPosition, double layoutX, double layoutY, double width, double height, String imageUrl) {
        super(stackPosition, layoutX, layoutY, width, height, imageUrl);
    }

    public DeckStackButton(StackPosition stackPosition, double layoutX, double layoutY, double width, double height) {
        this(stackPosition, layoutX, layoutY, width, height, AppConstants.REVERSED_CARD_URL);
    }

    @Override
    public void reloadImage() {
        String imageUrl;

        if (getStackPosition().isEmpty()) {
            imageUrl = ImagePathsFactory.getPathToCardImage(getStackPosition());
        }else{
            imageUrl = AppConstants.REVERSED_CARD_URL;
        }

        ImageView imageView = GuiTools.createImageView(getWidth(), getHeight(), imageUrl);
        setGraphic(imageView);
    }
}
