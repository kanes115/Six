package game.Moves;


import game.Card;
import game.Positions.Position;

// Assumptions: it is possible to delete any pair of unnecessary cards (ace - five)
// laying on any position (but on top)

public class DeleteUnnecessaryPair implements Move {

    private final Position from;
    private final Position to;

    private Card savedPos1;
    private Card savedPos2;

    private boolean isMade;
    private String errorMsg = "";

    public DeleteUnnecessaryPair(Position from, Position to){
        this.from = from;
        this.to = to;
        this.isMade = false;
    }

    @Override
    public boolean execute() {
        if(from.isEmpty() || to.isEmpty())
            return false;
        if(!from.getCard().getFace().isUnnecessary() ||
                !to.getCard().getFace().isUnnecessary())
            return false;
        if(!from.getCard().equals(to.getCard()))
            return false;

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

    @Override
    public boolean isMade() {
        return this.isMade;
    }

    @Override
    public String getErrorMessage() {
        return errorMsg;
    }
}
