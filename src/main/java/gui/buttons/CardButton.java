package gui.buttons;

import game.Moves.DeleteUnnecessaryPair;
import game.Moves.InsideMatrixRelocation;
import game.Moves.Move;
import game.Positions.CasualPosition;
import gui.GamePane;
import gui.GuiTools;
import gui.ImagePathsFactory;
import gui.Row;
import gui.fxcontrollers.GamePaneController;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class CardButton extends GameButton {

    private final int CLICK_COUNT_TO_DRAG_AND_DROP = 2;
    private Row row;
    private ButtonList cardChosenByUser;
    private int clickedCount = 0;

    public CardButton(CasualPosition position, Row row, double layoutX, double layoutY, double width, double height, String imageURL, GamePane gamePane) {
        super(position, layoutX, layoutY, width, height, imageURL, gamePane);
        this.row = row;
        cardChosenByUser = gamePane.getCardsChosenByUser();
        addDragAndDropHandler();

        addOneClickHandler();
    }


    public CardButton(CasualPosition position, Row row, double layoutX, double layoutY, double width, double height, GamePane gamePane) {
        this(position, row, layoutX, layoutY, width, height, ImagePathsFactory.getPathToCardImage(position.getCard()), gamePane);
    }

    public CasualPosition getPosition() {
        return (CasualPosition) super.getPosition();
    }

    public Row getRow() {
        return row;
    }

    public void move(double toX, double toY) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(100), this);
        transition.setToX(toX);
        transition.setToY(toY);
        transition.play();
    }

    private void addDragAndDropHandler() {

        this.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.getClickCount() == CLICK_COUNT_TO_DRAG_AND_DROP) {
                clickedCount = CLICK_COUNT_TO_DRAG_AND_DROP;
                previousX = mouseEvent.getSceneX();
                previousY = mouseEvent.getSceneY();
                CardButton card = gamePane.getCardButtonByCordinates(previousX, previousY);
//                cardChosenByUser.add(card);
                cardImageView = (ImageView) this.getGraphic();
            }
        });

        this.setOnMouseDragged(mouseEvent -> {
            if (clickedCount == CLICK_COUNT_TO_DRAG_AND_DROP) {
                cardImageView.setTranslateX(mouseEvent.getSceneX() - previousX);
                cardImageView.setTranslateY(mouseEvent.getSceneY() - previousY);
            }
        });

        this.setOnMouseReleased(mouseEvent -> {
            if (mouseEvent.getClickCount() == CLICK_COUNT_TO_DRAG_AND_DROP) {
                double x = mouseEvent.getSceneX();
                double y = mouseEvent.getSceneY();
                CardButton card = gamePane.getCardButtonByCordinates(x, y);
                if (card == null || card == cardChosenByUser.get(0)) {
                    cardChosenByUser.clearWholeListExceptDeckButton();
                    reloadImage();
                    return;
                }
                cardChosenByUser.add(card);
                Move move;
                CardButton first = (CardButton) cardChosenByUser.get(0);
                if (first.getPosition().isEmpty() || cardChosenByUser.get(1).getPosition().isEmpty()) {
                    move = new InsideMatrixRelocation(first.getPosition(), (CasualPosition) cardChosenByUser.get(1).getPosition());
                } else {
                    move = new DeleteUnnecessaryPair(first.getPosition(), cardChosenByUser.get(1).getPosition());
                }

                GamePaneController.makeDeletePairOrInsideMatrixRelocationMove(move);

                reloadImage();
                clickedCount = 0;
            }
        });
    }


    private void addOneClickHandler() {
        this.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 1) {
                if (!cardChosenByUser.contains(this)) {
                    cardChosenByUser.add(this);
                } else {
                    cardChosenByUser.remove(this);
                }
            }
        });
    }

    @Override
    public void reloadImage() {
        String imageUrl = ImagePathsFactory.getPathToCardImage(getPosition());
        ImageView imageView = GuiTools.createImageView(getWidth(), getHeight(), imageUrl);
        setGraphic(imageView);
    }

}