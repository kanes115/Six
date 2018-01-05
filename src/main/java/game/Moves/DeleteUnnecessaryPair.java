package game.Moves;


import game.Card;
import game.Positions.Position;

// Assumptions: it is possible to delete any pair of unnecessary cards (ace - five)
// laying on any position (but on top)

public class DeleteUnnecessaryPair implements Move {

    private final Position pos1;
    private final Position pos2;

    private Card savedPos1;
    private Card savedPos2;

    private boolean isMade;

    public DeleteUnnecessaryPair(Position pos1, Position pos2){
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.isMade = false;
    }

    @Override
    public boolean execute() {
        if(pos1.isEmpty() || pos2.isEmpty())
            return false;
        if(!pos1.getCard().getFace().isUnnecessary() ||
                !pos2.getCard().getFace().isUnnecessary())
            return false;
        if(!pos1.getCard().equals(pos2.getCard()))
            return false;

        savedPos1 = pos1.getCard();
        savedPos2 = pos2.getCard();
        pos1.removeCard();
        pos2.removeCard();

        isMade = true;
        return true;
    }

    @Override
    public void revert() {
        if(!isMade)
            return;
        pos1.putCard(savedPos1);
        pos2.putCard(savedPos2);
        isMade = false;
    }

    @Override
    public boolean isMade() {
        return this.isMade;
    }
}
