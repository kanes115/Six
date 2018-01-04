package game.Moves;


import game.Card;
import game.Positions.CasualPosition;
import game.State;

public class DeleteMove implements Move {

    private final CasualPosition pos1;
    private final CasualPosition pos2;

    private Card savedPos1;
    private Card savedPos2;

    private State state;
    private boolean isMade;

    public DeleteMove(CasualPosition pos1, CasualPosition pos2, State state){
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.state = state;
        this.isMade = false;
    }

    @Override
    public boolean execute() {
        if(state != State.PREPARING)
            return false;
        if(pos1.isEmpty() || pos2.isEmpty())
            return false;
        if(!pos1.getCard().getFace().isRemovable() ||
                !pos2.getCard().getFace().isRemovable())
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
    public State inWhatStateAvailable() {
        return State.PREPARING;
    }

    @Override
    public boolean isMade() {
        return this.isMade;
    }
}
