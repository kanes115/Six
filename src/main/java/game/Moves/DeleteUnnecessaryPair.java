package game.Moves;


import game.Card;
import game.Positions.Position;

import java.util.Optional;

// Assumptions: it is possible to delete any pair of unnecessary cards (ace - five)
// laying on any position (but on top)

public class DeleteUnnecessaryPair implements Move {

    private final Position from;
    private final Position to;

    private Card savedPos1;
    private Card savedPos2;

    private boolean isMade;
    private String errorMsg = null;

    public DeleteUnnecessaryPair(Position from, Position to){
        this.from = from;
        this.to = to;
        this.isMade = false;
    }

    @Override
    public boolean execute() {
        if(from.isEmpty() || to.isEmpty())
            return error("Both positions must be empty");
        if(!from.getCard().getFace().isUnnecessary() ||
                !to.getCard().getFace().isUnnecessary())
            return error("Both cards must be unnecessary");
        if(!from.getCard().equals(to.getCard()))
            return error("Cards must be the same");

        savedPos1 = from.getCard();
        savedPos2 = to.getCard();
        from.removeCard();
        to.removeCard();

        isMade = true;
        return true;
    }

    @Override
    public void revert() {
        if(!isMade)
            return;
        from.putCard(savedPos1);
        to.putCard(savedPos2);
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
    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMsg);
    }
}
