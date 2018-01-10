package game.Moves;

import game.Card;
import game.Positions.CasualPosition;

public class InsideMatrixRelocation implements Move {

    private final CasualPosition from;
    private final CasualPosition to;
    private boolean isMade;

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
        if(!to.getTargetFace().equals(from.getCard().getFace()))
            return false;
        if(!to.getRow().isColorAssigned()){
            Card savedFrom = from.getCard();
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

    private void justMove(CasualPosition a, CasualPosition b){
        b.putCard(a.removeCard());
    }
}