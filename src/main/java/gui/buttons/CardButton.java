package gui.buttons;

import game.Moves.DeleteUnnecessaryPair;
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


    private Row row;


    public CardButton(CasualPosition position, Row row, double layoutX, double layoutY, double width, double height, String imageURL, GamePane gamePane) {
        super(position, layoutX, layoutY, width, height, imageURL, gamePane);
        this.row = row;
        addDragAndDropHandler();
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
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
            CardButton card = gamePane.getCardButtonByCordinates(x,y);
            cardChosenByUser.add(card);
            cardImageView = (ImageView) this.getGraphic();

        });

        this.setOnMouseDragged(mouseEvent -> {
            cardImageView.setTranslateX(mouseEvent.getSceneX() - x);
            cardImageView.setTranslateY(mouseEvent.getSceneY() - y);
        });

        this.setOnMouseReleased(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
            CardButton card = gamePane.getCardButtonByCordinates(x,y);
            if(card == cardChosenByUser.get(0)){
                return;
            }
            cardChosenByUser.add(card);
            Move move = new DeleteUnnecessaryPair( cardChosenByUser.get(0).getPosition(),cardChosenByUser.get(1).getPosition());
            GamePaneController.makeMove(move);

        });
    }

    @Override
    public void reloadImage() {
        String imageUrl = ImagePathsFactory.getPathToCardImage(getPosition());
        ImageView imageView = GuiTools.createImageView(getWidth(), getHeight(), imageUrl);
        setGraphic(imageView);
    }

}