package game.Moves;

import game.*;
import game.Positions.CasualPosition;
import game.Positions.Position;

import java.util.Optional;


public class DeleteDuplicate implements Move {

    private final Position position;
    private Card card;
    private Board board;
    private boolean isMade;
    private String errorMsg = null;


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
            return error("You cannot remove a card that is unnecessary without its pair");
        }
        Color color = card.getColor();

        if(!board.getAssignedColors().contains(color)){
            return error("This color is not even assigned.");
        }

        Row row = board.getRowInColor(color);

        log(row.toString());

        for(CasualPosition position : row.getPositions()){
            log("Checking position with card: " + position.getCard());
            if(position.cardFaceMatchPosition() && position.getCard().equals(card)){
                log("This will be reomved");
                this.position.removeCard();
                isMade = true;
                return true;
            }
        }
        return error("There is no duplicate on the board.");
    }

    public boolean error(String msg){
        this.isMade = false;
        this.errorMsg = msg;
        return false;
    }

    public void log(String str){
        System.out.println("[DeleteDuplicate] " + str);
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
    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMsg);
    }

}
