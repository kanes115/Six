package game.Moves;

import game.*;
import game.Positions.CasualPosition;
import game.Positions.Position;
import game.Positions.RejectedPosition;
import game.Positions.StackPosition;

import java.util.Stack;


public class DeleteDuplicate implements Move {

    private final Position pos1;
    private Card card1;
    private Board board;
    private boolean isMade;
    private String errorMsg = "";


    // this move takes a position from deck or rejected
    // and looks for the duplicate in matrix.
    // if found and the card is not UNNECESSARY - it removes
    // a card from deck or rejected (for unnecessary cards use DeleteUnnecessaryPair)

    public DeleteDuplicate(Position pos){
        this.pos1 = pos;
        this.board = pos.getBoard();
        isMade = false;
    }


    @Override
    public boolean execute() {
        card1 = pos1.getCard();
        if(card1.getFace().isUnnecessary()){
            return error("This card is unnecessary.");
        }
        Color color = card1.getColor();

        if(!board.getAssignedColors().contains(color)){
            return error("There is no duplicate on the board.");
        }

        Row row = board.getRowInColor(color);

        for(CasualPosition position : row.getPositions()){
            if(position.cardFaceMatchPosition() && position.getCard().equals(card1)){
                pos1.removeCard();
                isMade = true;
                return true;
            }
        }
        return error("There is no duplicate on the board.");
    }

    @Override
    public void revert() {
        if(!isMade)
            return;
        pos1.putCard(card1);
        isMade = false;
    }

    private boolean error(String msg) {
        isMade = false;
        errorMsg = msg;
        return false;
    }

    @Override
    public boolean isMade() {
        return this.isMade;
    }

    @Override
    public String getErrorMessage() {
        return errorMsg;
    }

}
