package gui;

import gui.buttons.CardButton;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Row {

    private List<CardButton> cards = new ArrayList<>();
    private boolean isColorChoosen;
    private ImageView colorImage;

    public Row(ImageView colorImage) {
        this.isColorChoosen = false;
        this.colorImage = colorImage;
    }

    public List<CardButton> getCards() {
        return cards;
    }

    public ImageView getColorImage() {
        return colorImage;
    }

    public void setColorImage(ImageView colorImage) {
        this.colorImage = colorImage;
    }

    public void addCard(CardButton button) {
        cards.add(button);
    }

    public boolean isColorChoosen() {
        return isColorChoosen;
    }

    public void setColorChoosen(boolean colorChoosen) {
        isColorChoosen = colorChoosen;
    }

}
