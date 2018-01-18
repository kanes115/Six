package game.Moves;

import game.Board;
import game.Positions.CasualPosition;
import game.Row;

public class AssignColorOnCard implements Move {


    private final CasualPosition position;
    private final Board board;
    private String errorMsg;
    private boolean isMade = false;

    public AssignColorOnCard(CasualPosition position){
        this.position = position;
        this.board = position.getBoard();
    }

    @Override
    public boolean execute() {
        Row targetRow = position.getRow();
        if(targetRow.isColorAssigned())
            return error("This card is already in assigned row");
        if(board.getAssignedColors().contains(position.getCard().getColor()))
            return error("This card has a row specified already");
        if(!position.cardFaceMatchPosition())
            return error("This card's face does not match this column");
        position.getRow().assignColor(position.getCard().getColor());
        return true;
    }

    @Override
    public void revert() {

    }

    @Override
    public boolean isMade() {
        return this.isMade;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMsg;
    }

    private boolean error(String msg){
        this.errorMsg = msg;
        this.isMade = false;
        return false;
    }
}
