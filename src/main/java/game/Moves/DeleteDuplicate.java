package game.Moves;

import game.Board;
import game.Positions.StackPosition;

public class DeleteDuplicate implements Move {


    // this move takes a position from deck or rejected
    // and looks for the duplicate in matrix.
    // if found and the card is not UNNECESSARY - it removes
    // a card from deck or rejected (for unnecessary cards use DeleteUnnecessaryPair)
    public DeleteDuplicate(StackPosition pos, Board board){

    }

    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public void revert() {

    }

    @Override
    public boolean isMade() {
        return false;
    }

}
