package gui.buttons;

import game.Card;
import gui.GamePane;
import gui.GuiTools;
import gui.ImagePathsFactory;
import gui.fxcontrollers.GamePaneController;
import gui.i18n.CodesI18n;
import gui.i18n.I18n;
import javafx.scene.image.ImageView;

/**
 * Created by michaello on 25.01.18.
 */
public class TakenCardFromStackButton extends GameButton {

    DeckStackButton deck;

    public TakenCardFromStackButton(double layoutX, double layoutY, double width, double height, String imageUrl, GamePane gamePane, DeckStackButton deck) {
        super(deck.getPosition(), layoutX, layoutY, width, height, imageUrl, gamePane);
        this.deck = deck;
    }

    public TakenCardFromStackButton(double layoutX, double layoutY, double width, double height, GamePane gamePane, DeckStackButton deck) {
        this(layoutX, layoutY, width, height, ImagePathsFactory.getPathToCardImage((Card) null), gamePane, deck); //empty card
        addDragAndDropHandler();

    }

    private void addDragAndDropHandler() {
//        this.setOnMousePressed(mouseEvent -> {
//            previousX = mouseEvent.getSceneX();
//            previousY = mouseEvent.getSceneY();
//            GameButton card = gamePane.getGameButtonByCordinates(previousX, previousY);
//            cardChosenByUser.add(card);
//            cardImageView = (ImageView) this.getGraphic();
//
//        });
//
//        this.setOnMouseDragged(mouseEvent -> {
//            cardImageView.setTranslateX(mouseEvent.getSceneX() - previousX);
//            cardImageView.setTranslateY(mouseEvent.getSceneY() - previousY);
//        });
//
//        this.setOnMouseReleased(mouseEvent -> {
//            GameButton card = gamePane.getGameButtonByCordinates(mouseEvent.getSceneX(), mouseEvent.getSceneY());
//            if(card == cardChosenByUser.get(0)){
//                cardChosenByUser.clearWholeListExceptDeckButton();
//                reloadImage();
//                return;
//            }
//            cardChosenByUser.add(card);
//            Move move;
//            TakenCardFromStackButton first = (TakenCardFromStackButton) cardChosenByUser.get(0);
//            GameButton second = cardChosenByUser.get(1);
//
//            if(second instanceof CardButton){
//                //move = new DeleteUnnecessaryPair(first.getPosition(),second.getPosition());
//                move = MoveFactory.getMove(first.getPosition(), second.getPosition(), true);
//                GamePaneController.makeDeletePairOrInsideMatrixRelocationMove(move);
//            }else{
//                move = MoveFactory.getMove(first.getPosition(), second.getPosition(), false);
//                GamePaneController.makeMove(move);
//            }
//            reloadImage();
//
//        });
    }

    @Override
    public void reloadImage() {

        I18n i18n = I18n.getInstance();

        if (deck.isChecked() && !GamePaneController.getGameController().canBeDragged()) {
            GuiTools.showAlertDialog(i18n.getString(CodesI18n.INCORRECT_MOVE), i18n.getString(CodesI18n.CANNOT_TAKE_CARD_FROM_DECK), null);
            deck.setChecked(false);
        } else {
            //  chooseCardButton(btn);
            this.setChecked(true);
            String imageURL = ImagePathsFactory.getPathToCardImage(deck.getPosition());
            ImageView imageView = GuiTools.createImageView(getWidth(), getHeight(), imageURL);
            setGraphic(imageView);

        }
    }

    public void setImage(Card card) {
        ImageView imageView = GuiTools.createImageView(getWidth(), getHeight(), ImagePathsFactory.getPathToCardImage(card));
        setGraphic(imageView);
    }
}
