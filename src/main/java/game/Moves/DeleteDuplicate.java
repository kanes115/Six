package game.Moves;

import game.*;
import game.Positions.CasualPosition;
import game.Positions.Position;
import game.Positions.RejectedPosition;
import game.Positions.StackPosition;

import java.util.Stack;


public class DeleteDuplicate implements Move {

    private final Position pos1;
    private Card card1;
    private Board board;
    private boolean isMade;
    private String errorMsg = "";


    public DeleteDuplicate(Position pos){
        this.pos1 = pos;
        this.board = pos.getBoard();
        isMade = false;
    }


    @Override
    public boolean execute() {

        if(pos1.getClass() == CasualPosition.class){
            CasualPosition c = (CasualPosition) pos1;
            if(c.getRow().isColorAssigned() && c.cardFaceMatchPosition() && c.cardColorMatchRow())
                return error("This card is in its place already");

        }

        card1 = pos1.getCard();
        if(card1.getFace().isUnnecessary()){
            isMade = false;
            return false;
        }
        Color color = card1.getColor();

        if(!board.getAssignedColors().contains(color)){
            return false;
        }

        Row row = board.getRowInColor(color);

        for(CasualPosition position : row.getPositions()){
            if(position.cardFaceMatchPosition() && position.getCard().equals(card1)){
                pos1.removeCard();
                isMade = true;
                return true;
            }
        }

        isMade = false;
        return false;
    }

    public boolean error(String msg){
        this.isMade = false;
        this.errorMsg = msg;
        return false;
    }

    @Override
    public void revert() {
        if(!isMade)
            return;
        pos1.putCard(card1);
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
