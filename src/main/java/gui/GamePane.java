package gui;

import game.Board;
import game.Positions.CasualPosition;
import game.Positions.Position;
import gui.buttons.*;
import gui.fxcontrollers.GamePaneController;
import gui.i18n.CodesI18n;
import gui.i18n.I18n;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePane extends Pane {

    public static final int MAX_CARDS_IN_MOVE = 2;
    private static final double IMAGE_BUTTON_WIDTH = 100;
    private static final double MARGIN_WIDTH = 10;
    private static final double IMAGE_BUTTON_HEIGHT = 145;
    private static final int CARDS_IN_COLUMN = 4;
    private static final int CARDS_IN_ROW = 8;
    private static final double PREF_WIDTH = CARDS_IN_ROW * (IMAGE_BUTTON_WIDTH + MARGIN_WIDTH);
    private static final double PREF_HEIGHT = CARDS_IN_COLUMN * (IMAGE_BUTTON_HEIGHT + MARGIN_WIDTH);
    private static final int SPACE_BETWEEN_CARDS_AND_STACK = 2;
    private static final int ROW_FOR_DECK_STACK = 1;
    private static final int ROW_FOR_REJECTED_CARD_STACK = 2;
    private static final int ROW_FOR_IMAGE_WITH_TAKEN_CARD = 0;
    private final Map<Position, GameButton> positionsToButtons = new HashMap<>();

    private List<Row> guiRows = new ArrayList<>();
    private ButtonList cardsChosenByUser;
    private StackButton rejectedCards;
    private StackButton deck;

    //a picture card displayed when user clicked deck or rejected cards stack
    private TakenCardFromStackButton takenCardFromStack;

    public GamePane(ButtonList cardsChosenByUser) {
        super();
        this.cardsChosenByUser = cardsChosenByUser;
        setPrefSize(PREF_WIDTH, PREF_HEIGHT);
        setStyle("-fx-background-color: green;");
        initImageButtons();
    }

    public List<Row> getGuiRows() {
        return guiRows;
    }

    public TakenCardFromStackButton getTakenCardFromStack() {
        return takenCardFromStack;
    }

    public StackButton getRejectedCards() {
        return rejectedCards;
    }

    public StackButton getDeck() {
        return deck;
    }

    public Map<Position, GameButton> getPositionsToButtons() {
        return positionsToButtons;
    }

    public ButtonList getCardsChosenByUser() {
        return cardsChosenByUser;
    }

    private void initImageButtons() {
        List<game.Row> gameRows = GamePaneController.getGameController().getBoard().getRows();
        for (int y = 0; y < CARDS_IN_COLUMN; y++) {
            ImageView colorImage = initImageView(MARGIN_WIDTH + (CARDS_IN_ROW) * (IMAGE_BUTTON_WIDTH + MARGIN_WIDTH),
                    MARGIN_WIDTH + y * (IMAGE_BUTTON_HEIGHT + MARGIN_WIDTH),
                    IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);

            Row row = new Row(colorImage);


            for (int x = 0; x < CARDS_IN_ROW; x++) {
                CasualPosition casualPosition = gameRows.get(y).getPositions().get(x);
                CardButton btn = new CardButton(casualPosition, row,
                        MARGIN_WIDTH + x * (IMAGE_BUTTON_WIDTH + MARGIN_WIDTH),
                        MARGIN_WIDTH + y * (IMAGE_BUTTON_HEIGHT + MARGIN_WIDTH),
                        IMAGE_BUTTON_WIDTH,
                        IMAGE_BUTTON_HEIGHT,
                        this
                );
                positionsToButtons.put(casualPosition, btn);
//                btn.setOnAction(e -> chooseCardButton(btn));
                row.addCard(btn);
                getChildren().add(btn);
            }
            guiRows.add(row);
        }
        initDeckAndRejectedCardStack();
        takenCardFromStack = new TakenCardFromStackButton(MARGIN_WIDTH + (CARDS_IN_ROW + SPACE_BETWEEN_CARDS_AND_STACK) * (IMAGE_BUTTON_WIDTH + MARGIN_WIDTH),
                MARGIN_WIDTH + ROW_FOR_IMAGE_WITH_TAKEN_CARD * (IMAGE_BUTTON_HEIGHT + MARGIN_WIDTH), IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT, this, (DeckStackButton) deck);
        getChildren().add(takenCardFromStack);
    }


    public CardButton getCardButtonByCordinates(double x, double y) {
        for (int i = 0; i < CARDS_IN_COLUMN; i++) {
            double ya = MARGIN_WIDTH + i * (IMAGE_BUTTON_HEIGHT + MARGIN_WIDTH);
            double yb = MARGIN_WIDTH + (i + 1) * (IMAGE_BUTTON_HEIGHT + MARGIN_WIDTH);
            if (ya <= y && y <= yb) {
                for (int j = 0; j < CARDS_IN_ROW; j++) {
                    double xa = MARGIN_WIDTH + j * (IMAGE_BUTTON_WIDTH + MARGIN_WIDTH);
                    double xb = MARGIN_WIDTH + (j + 1) * (IMAGE_BUTTON_WIDTH + MARGIN_WIDTH);
                    if (xa <= x && x <= xb) {
                        return guiRows.get(i).getCards().get(j);
                    }
                }
            }
        }
        return null;
    }

    public GameButton getGameButtonByCordinates(double x, double y) {
        GameButton gameButton = getCardButtonByCordinates(x, y);
        if (gameButton == null) {
            gameButton = getCardsButtonByCordinates(rejectedCards, x, y);
            if (gameButton == null) {
                gameButton = getCardsButtonByCordinates(takenCardFromStack, x, y);
            }

        }

        return gameButton;
    }

    private GameButton getCardsButtonByCordinates(GameButton button, double x, double y) {
        if ((button.getLayoutX() < x) && x < (button.getLayoutX() + IMAGE_BUTTON_WIDTH) && (button.getLayoutY() < y) && y < (button.getLayoutY() + IMAGE_BUTTON_HEIGHT)) {
            return button;
        }
        return null;
    }

    private void initDeckAndRejectedCardStack() {
        Board board = GamePaneController.getGameController().getBoard();

        deck = new DeckStackButton(board.getDeckPosition(), MARGIN_WIDTH + (CARDS_IN_ROW + SPACE_BETWEEN_CARDS_AND_STACK) * (IMAGE_BUTTON_WIDTH + MARGIN_WIDTH),
                MARGIN_WIDTH + ROW_FOR_DECK_STACK * (IMAGE_BUTTON_HEIGHT + MARGIN_WIDTH), IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT, this);
        deck.setOnAction(e -> getCardFromStack(deck));
        positionsToButtons.put(board.getDeckPosition(), deck);
        getChildren().add(deck);

        rejectedCards = new RejectedCardsStackButton(board.getRejectedPosition(), MARGIN_WIDTH + (CARDS_IN_ROW + SPACE_BETWEEN_CARDS_AND_STACK) * (IMAGE_BUTTON_WIDTH + MARGIN_WIDTH),
                MARGIN_WIDTH + ROW_FOR_REJECTED_CARD_STACK * (IMAGE_BUTTON_HEIGHT + MARGIN_WIDTH), IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT, this); //load empty image
        rejectedCards.setOnAction(e -> chooseCardButton(rejectedCards));
        positionsToButtons.put(board.getRejectedPosition(), rejectedCards);
        getChildren().add(rejectedCards);
    }

    private ImageView initImageView(double layoutX, double layoutY, double width, double height) {
        ImageView imageView = GuiTools.createImageView(layoutX, layoutY, width, height);
        getChildren().add(imageView);
        return imageView;
    }

    private void getCardFromStack(StackButton btn) {

        if (!GamePaneController.getGameController().canBeDragged()) {
            I18n i18n = I18n.getInstance();
            GuiTools.showAlertDialog(i18n.getString(CodesI18n.INCORRECT_MOVE), i18n.getString(CodesI18n.CANNOT_TAKE_CARD_FROM_DECK), null);
            deck.setChecked(false);
        } else {
            chooseCardButton(btn);
            btn.setChecked(true);
            takenCardFromStack.reloadImage();
        }
    }

    private void chooseCardButton(GameButton btn) {
        if (cardsChosenByUser.contains(btn)) {
            if (btn instanceof DeckStackButton) {
                return;
            }
            cardsChosenByUser.remove(btn);
            return;
        }
        if (cardsChosenByUser.size() == MAX_CARDS_IN_MOVE)
            return;

        cardsChosenByUser.add(btn);

    }


}
