package game.Moves;

import game.Board;
import game.Card;
import game.Positions.CasualPosition;

import java.util.Optional;

public class InsideMatrixRelocation implements Move {

    private CasualPosition from;
    private CasualPosition to;
    private Board board;
    private boolean isMade;

    private boolean wasColorAssigned;
    private String errorMsg = null;

    public InsideMatrixRelocation(CasualPosition position1, CasualPosition position2){
        assignFromAndTo(position1, position2);
        this.board = this.from.getBoard();
        this.isMade = false;
    }

    @Override
    public boolean execute() {
        if(from.isEmpty() || !to.isEmpty())
            return error("One field is empty");
        if(!to.getTargetFace().equals(from.getCard().getFace()))
            return error("This position has a different target face");
        if(!to.getRow().isColorAssigned()){
            Card savedFrom = from.getCard();
            if(board.getAssignedColors().contains(from.getCard().getColor()))
                return error("This color is already assigned");
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
        return error("This row has different color assigned");
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
    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMsg);
    }

    private void assignFromAndTo(CasualPosition position1, CasualPosition position2){
        if(position2.isEmpty()) {
            this.from = position1;
            this.to = position2;
        }else{
            this.from = position2;
            this.to = position1;
        }
    }

    private boolean error(String msg){
        this.errorMsg = msg;
        this.isMade = false;
        return false;
    }

    private void justMove(CasualPosition fromPosition, CasualPosition toPosition){
        toPosition.putCard(fromPosition.removeCard());
    }
}
