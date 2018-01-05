package gui;

import javafx.scene.image.ImageView;

/**
 * Created by michaello on 05.01.18.
 */
public class EmptyImageViewCard {

    private static ImageView emptyCardImage;

    public static ImageView getEmptyCardImage() {
        return emptyCardImage;
    }

    public static void setEmptyCardImage(ImageView emptyCardImage) {
        EmptyImageViewCard.emptyCardImage = emptyCardImage;
    }
}
