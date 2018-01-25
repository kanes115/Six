package gui.buttons;

import game.Positions.StackPosition;
import gui.GamePane;
import gui.GuiTools;
import gui.ImagePathsFactory;
import gui.dictionary.AppConstants;
import javafx.scene.image.ImageView;

/**
 * Created by michaello on 14.01.18.
 */
public class DeckStackButton extends StackButton {

    public DeckStackButton(StackPosition stackPosition, double layoutX, double layoutY, double width, double height, String imageUrl, GamePane gamePane) {
        super(stackPosition, layoutX, layoutY, width, height, imageUrl, gamePane);
    }

    public DeckStackButton(StackPosition stackPosition, double layoutX, double layoutY, double width, double height, GamePane gamePane) {
        this(stackPosition, layoutX, layoutY, width, height, AppConstants.REVERSED_CARD_URL, gamePane);
    }

    @Override
    public void reloadImage() {
        String imageUrl;

        if (getStackPosition().isEmpty()) {
            imageUrl = ImagePathsFactory.getPathToCardImage(getStackPosition());
        } else {
            imageUrl = AppConstants.REVERSED_CARD_URL;
        }

        ImageView imageView = GuiTools.createImageView(getWidth(), getHeight(), imageUrl);
        setGraphic(imageView);
        gamePane.getTakenCardFromStack().setImage(null);
    }
}
