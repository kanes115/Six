package gui;

import game.GameController;
import game.MoveResponse;
import game.Moves.*;
import game.Positions.CasualPosition;
import game.Positions.Position;
import gui.buttons.ButtonList;
import gui.buttons.CardButton;
import gui.buttons.GameButton;
import gui.dictionary.AppConstants;
import gui.i18n.CodesI18n;
import gui.i18n.I18n;
import javafx.scene.image.Image;

/**
 * Created by michaello on 14.01.18.
 */
public class MoveExecutor {

    private final ButtonList cardsChosenByUser;

    private final GamePane gamePane;

    private final GameController gameController;

    public MoveExecutor(ButtonList cardsChosenByUser, GamePane gamePane, GameController gameController) {
        this.cardsChosenByUser = cardsChosenByUser;
        this.gamePane = gamePane;
        this.gameController = gameController;
    }

    public void performAssignColorOnRowMove() {
        I18n i18n = I18n.getInstance();
        if (!checkButtonType(CardButton.class, cardsChosenByUser.get(0))) {
            GuiTools.showAlertDialog(i18n.getString(CodesI18n.INCORRECT_MOVE), i18n.getString(CodesI18n.CARD_FROM_BOARD_TAKEN_COND), null);
            cardsChosenByUser.clearWholeListExceptDeckButton();
            return;
        }
        CardButton first = (CardButton) cardsChosenByUser.get(0);
        Move move = new AssignColorOnCard(first.getPosition());
        MoveResponse response = gameController.tryMove(move);

        if (response.wasOk()) {
            first.reloadImage();
            assignRow(first);
        } else {
            GuiTools.showAlertDialog(i18n.getString(CodesI18n.INCORRECT_MOVE), response.getErrorMessage(), null);
        }

        cardsChosenByUser.clearWholeListExceptDeckButton();
    }

    public void performDeleteDuplicateMove() {
        GameButton button = cardsChosenByUser.get(0);
        Move move = new DeleteDuplicate(button.getPosition());

        MoveResponse response = gameController.tryMove(move);
        handleMoveWhichRequiredRefreshingWholeBoard(response);
    }

    public void performDeleteUnnecessaryPairMove() {
        GameButton first = cardsChosenByUser.get(0);
        GameButton second = cardsChosenByUser.get(1);
        I18n i18n = I18n.getInstance();

        Move move = new DeleteUnnecessaryPair(second.getPosition(), first.getPosition());
        MoveResponse response = gameController.tryMove(move);

        if (response.wasOk()) {
            second.reloadImage();
            first.reloadImage();
            gamePane.getTakenCardFromStack().setImage(null);
            cardsChosenByUser.clearWholeList();
        } else {
            GuiTools.showAlertDialog(i18n.getString(CodesI18n.INCORRECT_MOVE), response.getErrorMessage(), null);
            cardsChosenByUser.clearWholeListExceptDeckButton();
        }
    }

    public void performFromStackMove() {
        I18n i18n = I18n.getInstance();
        Move move;
        if ((move = initializeDeckToMatrixMove(cardsChosenByUser)) == null) {
            GuiTools.showAlertDialog(i18n.getString(CodesI18n.INCORRECT_MOVE), i18n.getString(CodesI18n.CANNOT_INITIALIZE_MOVE), null);
            return;
        }

        MoveResponse response = gameController.tryMove(move);
        if(response.wasOk() && cardsChosenByUser.get(1) instanceof CardButton){
            assignRow((CardButton) cardsChosenByUser.get(1));
        }
        handleMoveWhichRequiredRefreshingWholeBoard(response);
    }

    public void performInsideMatrixRelocationMove() {
        I18n i18n = I18n.getInstance();
        if (!checkButtonType(CardButton.class, cardsChosenByUser.get(0), cardsChosenByUser.get(1))) {
            GuiTools.showAlertDialog(i18n.getString(CodesI18n.INCORRECT_MOVE), i18n.getString(CodesI18n.SELECTED_CARDS_MUST_FROM_BOARD), null);
            cardsChosenByUser.clearWholeListExceptDeckButton();
            return;
        }


        CardButton first = (CardButton) cardsChosenByUser.get(0);
        CardButton second = (CardButton) cardsChosenByUser.get(1);

        Move move = new InsideMatrixRelocation(first.getPosition(), second.getPosition());
        MoveResponse response = gameController.tryMove(move);

        if (response.wasOk()) {
            second.reloadImage();
            first.reloadImage();
            assignRow(second);

        } else {
            GuiTools.showAlertDialog(i18n.getString(CodesI18n.INCORRECT_MOVE), response.getErrorMessage(), null);
        }
        cardsChosenByUser.clearWholeListExceptDeckButton();
    }

    private void assignRow(CardButton button) {
        game.Row gameRow = button.getPosition().getRow();
        gui.Row guiRow = button.getRow();
        if (!guiRow.isColorChoosen() && gameRow.isColorAssigned()) {
            guiRow.getColorImage().setImage(new Image(AppConstants.PATH_TO_CARDS_IMAGES + gameRow.getColor().name().toLowerCase() + ".png"));
            guiRow.setColorChoosen(true);
        }
    }

    private boolean checkButtonType(Class<?> buttonType, GameButton... buttons) {
        for (GameButton button : buttons) {
            if (!buttonType.isInstance(button)) {
                return false;
            }
        }
        return true;
    }

    private Move initializeDeckToMatrixMove(ButtonList cardsChosenByUser) {
        GameButton first = cardsChosenByUser.get(0);
        GameButton second = cardsChosenByUser.get(1);

        Position firstPosition = first.getPosition();
        Position secondPosition = second.getPosition();

        Move move = null;
        if (firstPosition instanceof DeckPosition && secondPosition instanceof RejectedPosition) {
            move = new FromStack((DeckPosition) firstPosition, (RejectedPosition) secondPosition);
        } else if (firstPosition instanceof DeckPosition && secondPosition instanceof CasualPosition) {
            move = new FromStack((DeckPosition) firstPosition, (CasualPosition) secondPosition);
        } else if (firstPosition instanceof RejectedPosition && secondPosition instanceof CasualPosition) {
            move = new FromStack((RejectedPosition) firstPosition, (CasualPosition) secondPosition);
        }
        return move;

    }

    private void handleMoveWhichRequiredRefreshingWholeBoard(MoveResponse moveResponse) {
        I18n i18n = I18n.getInstance();
        if (moveResponse.wasOk()) {
            reloadAllImages();
            gamePane.getTakenCardFromStack().setImage(null);
            cardsChosenByUser.clearWholeList();
        } else {
            GuiTools.showAlertDialog(i18n.getString(CodesI18n.INCORRECT_MOVE), moveResponse.getErrorMessage(), null);
            cardsChosenByUser.clearWholeListExceptDeckButton();
        }
    }

    private void reloadAllImages() {
        gamePane.getRejectedCards().reloadImage();
        gamePane.getDeck().reloadImage();
        gamePane.getGuiRows().forEach(
                row -> row.getCards().forEach(btn -> btn.reloadImage())
        );
    }

}
