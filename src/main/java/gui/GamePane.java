package gui;

import javafx.scene.layout.Pane;

import java.util.ArrayDeque;
import java.util.Queue;

public class GamePane extends Pane {

    private static final double IMAGE_BUTTON_WIDTH = 100;
    private static final double MARGIN_WIDTH = 10;
    private static final double IMAGE_BUTTON_HEIGHT = 145;
    private static final int CARDS_IN_COLUMN = 4;
    private static final int CARDS_IN_ROW = 8;
    private static final double PREF_WIDTH = CARDS_IN_ROW*(IMAGE_BUTTON_WIDTH+MARGIN_WIDTH);
    private static final double PREF_HEIGHT = CARDS_IN_COLUMN*(IMAGE_BUTTON_HEIGHT+MARGIN_WIDTH);
    private Queue<ImageButton> checkedImageButtons;

    public GamePane() {
        super();
        setPrefSize(PREF_WIDTH,PREF_HEIGHT);
        setStyle("-fx-background-color: green;");
        checkedImageButtons = new ArrayDeque<>();
        initImageButtons();
    }

    private void initImageButtons() {
        for (int y = 0; y < CARDS_IN_COLUMN; y++)
            for (int x = 0; x < CARDS_IN_ROW; x++) {
                ImageButton btn = new ImageButton(
                        "/gui/cards/2_of_clubs.png",
                        MARGIN_WIDTH+x*(IMAGE_BUTTON_WIDTH+MARGIN_WIDTH),
                        MARGIN_WIDTH+y*(IMAGE_BUTTON_HEIGHT+MARGIN_WIDTH),
                        IMAGE_BUTTON_WIDTH,
                        IMAGE_BUTTON_HEIGHT
                );
                btn.setOnAction(e -> checkImageButton(btn));
                getChildren().add(btn);
            }
    }

    private void checkImageButton(ImageButton btn) {
        if (checkedImageButtons.contains(btn))
            return;
        if (checkedImageButtons.size() == 2)
            checkedImageButtons.remove().setChecked(false);
        btn.setChecked(true);
        checkedImageButtons.add(btn);
    }

}
