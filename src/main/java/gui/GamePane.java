package gui;

import game.Board;
import game.Positions.CasualPosition;
import game.Positions.Position;
import gui.buttons.*;
import gui.fxcontrollers.GamePaneController;
import gui.i18n.CodesI18n;
import gui.i18n.I18n;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class GamePane extends Pane {

    public static final int MAX_CARDS_IN_MOVE = 2;
    private static final double IMAGE_BUTTON_WIDTH = 100;
    private static final double MARGIN_WIDTH = 10;
    private static final double IMAGE_BUTTON_HEIGHT = 145;
    private static final int CARDS_IN_COLUMN = 4;
    private static final int CARDS_IN_ROW = 8;
    private static final double PREF_WIDTH = CARDS_IN_ROW * (IMAGE_BUTTON_WIDTH + MARGIN_WIDTH);
    private static final double PREF_HEIGHT = CARDS_IN_COLUMN * (IMAGE_BUTTON_HEIGHT + MARGIN_WIDTH);
    private static final int  SPACE_BETWEEN_CARDS_AND_STACK = 2;
    private static final int ROW_FOR_DECK_STACK = 1;
    private static final int ROW_FOR_REJECTED_CARD_STACK = 2;
    private static final int ROW_FOR_IMAGE_WITH_TAKEN_CARD = 0;
    private final Map <Position, GameButton> positionsToButtons = new HashMap<>();

    private List<Row> guiRows = new ArrayList<>();
    private ButtonList cardsChosenByUser = new ButtonList();
    private StackButton rejectedCards;
    private StackButton deck;

    //a picture card displayed when user clicked deck or rejected cards stack
    private ImageView takenCardFromStack;

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

    public ImageView getTakenCardFromStack() {
        return takenCardFromStack;
    }

    public StackButton getRejectedCards() {
        return rejectedCards;
    }

    public StackButton getDeck(){return deck;}

    public Map<Position, GameButton> getPositionsToButtons() {
        return positionsToButtons;
    }

    private void initImageButtons() {
        takenCardFromStack =  initImageView(MARGIN_WIDTH + (CARDS_IN_ROW + SPACE_BETWEEN_CARDS_AND_STACK) * (IMAGE_BUTTON_WIDTH + MARGIN_WIDTH),
                MARGIN_WIDTH + ROW_FOR_IMAGE_WITH_TAKEN_CARD * (IMAGE_BUTTON_HEIGHT + MARGIN_WIDTH) ,IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT );

        List<game.Row> gameRows = GamePaneController.getGameController().getBoard().getRows();
        for (int y = 0; y < CARDS_IN_COLUMN; y++) {
            ImageView colorImage = initImageView(MARGIN_WIDTH + (CARDS_IN_ROW) * (IMAGE_BUTTON_WIDTH + MARGIN_WIDTH),
                    MARGIN_WIDTH + y * (IMAGE_BUTTON_HEIGHT + MARGIN_WIDTH),
                    IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);

            Row row = new Row(colorImage);


            for (int x = 0; x < CARDS_IN_ROW; x++) {
                CasualPosition casualPosition = gameRows.get(y).getPositions().get(x);
                CardButton btn = new CardButton(casualPosition , row,
                        MARGIN_WIDTH + x * (IMAGE_BUTTON_WIDTH + MARGIN_WIDTH),
                        MARGIN_WIDTH + y * (IMAGE_BUTTON_HEIGHT + MARGIN_WIDTH),
                        IMAGE_BUTTON_WIDTH,
                        IMAGE_BUTTON_HEIGHT
                );
                positionsToButtons.put(casualPosition, btn);
                btn.setOnAction(e -> chooseCardButton(btn));
                row.addCard(btn);
                getChildren().add(btn);
            }
            guiRows.add(row);
        }
        initDeckAndRejectedCardStack();
    }

    private void initDeckAndRejectedCardStack() {
        Board board = GamePaneController.getGameController().getBoard();

        deck = new DeckStackButton(board.getDeckPosition(), MARGIN_WIDTH + (CARDS_IN_ROW + SPACE_BETWEEN_CARDS_AND_STACK) * (IMAGE_BUTTON_WIDTH + MARGIN_WIDTH),
                MARGIN_WIDTH + ROW_FOR_DECK_STACK * (IMAGE_BUTTON_HEIGHT + MARGIN_WIDTH), IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT);
        deck.setOnAction(e -> getCardFromStack(deck));
        positionsToButtons.put(board.getDeckPosition(), deck);
        getChildren().add(deck);

        rejectedCards = new RejectedCardsStackButton(board.getRejectedPosition(), MARGIN_WIDTH + (CARDS_IN_ROW + SPACE_BETWEEN_CARDS_AND_STACK) * (IMAGE_BUTTON_WIDTH + MARGIN_WIDTH),
                MARGIN_WIDTH + ROW_FOR_REJECTED_CARD_STACK * (IMAGE_BUTTON_HEIGHT + MARGIN_WIDTH), IMAGE_BUTTON_WIDTH, IMAGE_BUTTON_HEIGHT); //load empty image
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
        I18n i18n = I18n.getInstance();

        if(!GamePaneController.getGameController().canBeDragged()){
            GuiTools.showAlertDialog(i18n.getString(CodesI18n.INCORRECT_MOVE),  i18n.getString(CodesI18n.CANNOT_TAKE_CARD_FROM_DECK), null);
        }else{
            chooseCardButton(btn);
            btn.setChecked(true);
            String imageURL = ImagePathsFactory.getPathToCardImage(btn.getPosition());
            takenCardFromStack.setImage(new Image(getClass().getResourceAsStream(imageURL)));
        }

    }

    private void chooseCardButton(GameButton btn) {
        if (cardsChosenByUser.contains(btn)) {
            if(btn instanceof  DeckStackButton){
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
