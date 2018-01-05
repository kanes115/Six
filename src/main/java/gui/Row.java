package gui;

import java.util.ArrayList;
import java.util.List;

public class Row {

    private List<ImageButton> cards = new ArrayList<>();
    private boolean isColorChoosen;

    public Row() {
        this.isColorChoosen = false;
    }

    public void addCard(ImageButton button) {
        cards.add(button);
    }

    public boolean isColorChoosen() {
        return isColorChoosen;
    }

    public void setColorChoosen(boolean colorChoosen) {
        isColorChoosen = colorChoosen;
    }
}
