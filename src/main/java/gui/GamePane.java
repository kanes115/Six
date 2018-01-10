package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.*;

public class GamePane extends Pane {

    public static  final int CARDS_IN_MOVE = 2;
    private static final double IMAGE_BUTTON_WIDTH = 100;
    private static final double MARGIN_WIDTH = 10;
    private static final double IMAGE_BUTTON_HEIGHT = 145;
    private static final int CARDS_IN_COLUMN = 4;
    private static final int CARDS_IN_ROW = 6;
    private static final double PREF_WIDTH = CARDS_IN_ROW*(IMAGE_BUTTON_WIDTH+MARGIN_WIDTH);
    private static final double PREF_HEIGHT = CARDS_IN_COLUMN*(IMAGE_BUTTON_HEIGHT+MARGIN_WIDTH);
    private List<Row> rows = new ArrayList<>();
    private Queue<ImageButton> checkedImageButtons;

    public GamePane() {
        super();
        setPrefSize(PREF_WIDTH,PREF_HEIGHT);
        setStyle("-fx-background-color: green;");
        checkedImageButtons = new ArrayDeque<>();
        initImageButtons();
    }

    public Queue<ImageButton> getCheckedImageButtons() {
        return checkedImageButtons;
    }

    private void initImageButtons() {
        initEmptyImageView();
        Random randomGenerator = new Random();
        for (int y = 0; y < CARDS_IN_COLUMN; y++){
            Row row = new Row();
            for (int x = 0; x < CARDS_IN_ROW; x++) {
                ImageButton btn = new ImageButton(
                        Card.values()[randomGenerator.nextInt(53)],
                        MARGIN_WIDTH+x*(IMAGE_BUTTON_WIDTH+MARGIN_WIDTH),
                        MARGIN_WIDTH+y*(IMAGE_BUTTON_HEIGHT+MARGIN_WIDTH),
                        IMAGE_BUTTON_WIDTH,
                        IMAGE_BUTTON_HEIGHT
                );
                btn.setOnAction(e -> checkImageButton(btn));
                row.addCard(btn);
                getChildren().add(btn);
            }
            rows.add(row);
        }
    }

    private void initEmptyImageView() {
        ImageView emptyCardView = new ImageView(new Image(getClass().getResourceAsStream(Card.CARD_EMPTY.getPathToFilename())));
        emptyCardView.setFitWidth(IMAGE_BUTTON_WIDTH);
        emptyCardView.setFitHeight(IMAGE_BUTTON_HEIGHT);
        EmptyImageViewCard.setEmptyCardImage(emptyCardView);
    }

    private void checkImageButton(ImageButton btn) {
        if (checkedImageButtons.contains(btn))
            return;
        if (checkedImageButtons.size() == CARDS_IN_MOVE)
            checkedImageButtons.remove().setChecked(false);
        btn.setChecked(true);
        checkedImageButtons.add(btn);
    }

}
