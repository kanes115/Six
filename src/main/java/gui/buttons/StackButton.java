package gui.buttons;

import game.Card;
import game.Positions.StackPosition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Created by michaello on 10.01.18.
 */
public class StackButton extends GameButton {

    public StackButton(StackPosition stackPosition, double layoutX, double layoutY, double width, double height, String imageUrl) {
        super(stackPosition, layoutX ,layoutY, width ,height , imageUrl);
    }

    public StackPosition getStackPosition(){
        return (StackPosition) super.getPosition();
    }

}
