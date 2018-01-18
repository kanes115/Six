package game.Moves;

import game.*;
import game.Positions.CasualPosition;
import game.Positions.Position;


public class DeleteDuplicate implements Move {

    private final Position position;
    private Card card;
    private Board board;
    private boolean isMade;
    private String errorMsg = "";


    public DeleteDuplicate(Position position){
        this.position = position;
        this.board = position.getBoard();
        isMade = false;
    }


    @Override
    public boolean execute() {

        if(position instanceof CasualPosition){
            CasualPosition c = (CasualPosition) position;
            if(c.getRow().isColorAssigned() && c.cardFaceMatchPosition() && c.cardColorMatchRow())
                return error("This card is in its place already");

        }

        card = position.getCard();
        if(card.getFace().isUnnecessary()){
            isMade = false;
            return false;
        }
        Color color = card.getColor();

        if(!board.getAssignedColors().contains(color)){
            return false;
        }

        Row row = board.getRowInColor(color);

        for(CasualPosition position : row.getPositions()){
            if(position.cardFaceMatchPosition() && position.getCard().equals(card)){
                this.position.removeCard();
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
        position.putCard(card);
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
