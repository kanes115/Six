package game.Moves;

import game.Card;
import game.Positions.CasualPosition;
import game.State;

public class InsideMatrixRelocation implements Move {

    private final CasualPosition from;
    private final CasualPosition to;
    private boolean isMade;

    private Card savedFrom;
    private Card savedTo;
    private boolean wasColorAssigned;

    public InsideMatrixRelocation(CasualPosition from, CasualPosition to){
        this.from = from;
        this.to = to;
        this.isMade = false;
    }

    @Override
    public boolean execute() {
        if(from.isEmpty() || !to.isEmpty())
            return false;
        savedFrom = from.getCard();
        savedTo = to.getCard();
        if(!to.getRow().isColorAssigned()){
            justMove(from, to);
            to.getRow().assignColor(savedFrom.getColor());
            isMade = true;
            wasColorAssigned = false;
            return true;
        }
        if(to.getRow().getColor().equals(from.getCard().getColor())){
            justMove(from, to);
            isMade = true;
            wasColorAssigned = true;
            return true;
        }
        return false;
    }

    @Override
    public void revert() {
        if(!isMade)
            return;
        if(wasColorAssigned){
            justMove(to, from);
            to.getRow().unAssignColor();
            return;
        }
        justMove(to, from);
    }

    @Override
    public boolean isMade() {
        return isMade;
    }

    @Override
    public State inWhatStateAvailable() {
        return State.PREPARING;
    }

    private void justMove(CasualPosition a, CasualPosition b){
        b.putCard(a.removeCard());
    }
}
