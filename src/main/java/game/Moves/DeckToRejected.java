package game.Moves;

import game.*;
import game.Positions.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;


public class DeckToRejected implements Move {

    private StackPosition from;
    private StackPosition to;
    private boolean isMade = false;
    private Board board;
    private String errorMsg = null;


    public DeckToRejected(StackPosition stack1, StackPosition stack2){
        assignDeckAndRejected(stack1, stack2);
        this.board = stack1.getBoard();
    }

    @Override
    public boolean execute() {

        if(from.getType() == StackPositionType.DECK && to.getType() == StackPositionType.REJECTED){
            if(board.hasFreePositions())
                return error("There are free positions on the board");
            return move(from, to);
        }
        return error("Wrong positions");
    }


    private boolean move(Position from, Position to) {
        to.putCard(from.removeCard());
        isMade = true;
        return true;
    }

    private boolean error(String msg) {
        isMade = false;
        errorMsg = msg;
        return false;
    }

    private boolean isDeckToRejected(StackPosition deck, StackPosition rejected) {
        return deck.getType() == StackPositionType.DECK
                && rejected.getType() == StackPositionType.REJECTED;
    }

    private void assignDeckAndRejected(StackPosition maybeDeck, StackPosition maybeRejected){
        if(isDeckToRejected(maybeDeck, maybeRejected)){
            this.from = maybeDeck;
            this.to = maybeRejected;
        }else{
            this.from = maybeRejected;
            this.to = maybeDeck;
        }
    }

    @Override
    public void revert() {
        throw new NotImplementedException();
    }

    @Override
    public boolean isMade() {
        return this.isMade;
    }

    @Override
    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMsg);
    }



}
