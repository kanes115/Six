package gui;

import game.GameController;
import gui.buttons.GameButton;
import gui.buttons.ImageButton;
import gui.buttons.StackButton;
import gui.fxcontrollers.GamePaneController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GamePane extends Pane {

    public static final int MAX_CARDS_IN_MOVE = 2;
    private static final double IMAGE_BUTTON_WIDTH = 100;
    private static final double MARGIN_WIDTH = 10;
    private static final double IMAGE_BUTTON_HEIGHT = 145;
    private static final int CARDS_IN_COLUMN = 4;
    private static final int CARDS_IN_ROW = 6;
    private static final double PREF_WIDTH = CARDS_IN_ROW * (IMAGE_BUTTON_WIDTH + MARGIN_WIDTH);
    private static final double PREF_HEIGHT = CARDS_IN_COLUMN * (IMAGE_BUTTON_HEIGHT + MARGIN_WIDTH);

    private List<Row> guiRows = new ArrayList<>();
    private List<GameButton> checkedImageButtons = new LinkedList<>();
    private StackButton rejectedCards;
    private StackButton deck;

    public GamePane() {
        super();
        setPrefSize(PREF_WIDTH, PREF_HEIGHT);
        setStyle("-fx-background-color: green;");
        initImageButtons();
    }

    public List<GameButton> getCheckedImageButtons() {
        return checkedImageButtons;
    }

    private void initImageButtons() {
        List<game.Row> gameRows = GamePaneController.getGameController().getBoard().getRows();
        initEmptyImageView();
        for (int y = 0; y < CARDS_IN_COLUMN; y++) {
            Row row = new Row();

            for (int x = 0; x < CARDS_IN_ROW; x++) {
                ImageButton btn = new ImageButton(gameRows.get(y).getPositions().get(x), row,
                        MARGIN_WIDTH + x * (IMAGE_BUTTON_WIDTH + MARGIN_WIDTH),
                        MARGIN_WIDTH + y * (IMAGE_BUTTON_HEIGHT + MARGIN_WIDTH),
                        IMAGE_BUTTON_WIDTH,
                        IMAGE_BUTTON_HEIGHT
                );
                btn.setOnAction(e -> checkImageButton(btn));
                row.addCard(btn);
                getChildren().add(btn);
            }
            guiRows.add(row);
        }
        initDeckAndRejectedStack();
    }

    private void initDeckAndRejectedStack() {

        GameController gameController = GamePaneController.getGameController();
        deck = new StackButton(gameController.getBoard().getDeckPosition(), MARGIN_WIDTH + 9 * (IMAGE_BUTTON_WIDTH + MARGIN_WIDTH),
                MARGIN_WIDTH + 1 * (IMAGE_BUTTON_WIDTH + MARGIN_WIDTH), IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT, "/gui/cards/card_reverse.png");
        deck.setOnAction(e -> checkImageButton(deck));
        deck.setText("Talia");
        getChildren().add(deck);

        rejectedCards = new StackButton(gameController.getBoard().getRejectedPosition(), MARGIN_WIDTH + 9 * (IMAGE_BUTTON_WIDTH + MARGIN_WIDTH),
                MARGIN_WIDTH + 3 * (IMAGE_BUTTON_WIDTH + MARGIN_WIDTH), IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT, "/gui/cards/card_reverse.png");
        rejectedCards.setOnAction(e -> checkImageButton(rejectedCards));
        rejectedCards.setText("Odrzucone");
        getChildren().add(rejectedCards);
    }

    private void initEmptyImageView() {
        ImageView emptyCardView = new ImageView(new Image(getClass().getResourceAsStream("/gui/icon.jpg")));
        emptyCardView.setFitWidth(IMAGE_BUTTON_WIDTH);
        emptyCardView.setFitHeight(IMAGE_BUTTON_HEIGHT);
        EmptyImageViewCard.setEmptyCardImage(emptyCardView);
    }

    private void checkImageButton(GameButton btn) {
        if (checkedImageButtons.contains(btn)) {
            checkedImageButtons.remove(btn);
            btn.setChecked(false);
            return;
        }
        if (checkedImageButtons.size() == MAX_CARDS_IN_MOVE)
            return;

        btn.setChecked(true);
        checkedImageButtons.add(btn);
    }

}
