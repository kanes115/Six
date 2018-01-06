package game.Moves;

import game.Board;
import game.Color;
import game.Face;
import game.Positions.CasualPosition;
import game.Positions.DeckPosition;
import game.Positions.Position;
import game.Positions.RejectedPosition;
import game.Row;

public class DeckToMatrix implements Move {

    private  Position deck;
    private  Position cas;
    private boolean isToRejectedStack;
    private boolean isMade;
    private Board board;
    // It moves a card from deck to rejected stack.
    public DeckToMatrix(DeckPosition deck, RejectedPosition rej, Board board){
        this.deck = deck;
        this.cas = rej;
        this.isToRejectedStack = true;
        this.isMade = false;
        this.board = board;
    }

    // It relocates a card from deck top to matrix.
    // It does it only if the card is moved to it's FINAL PLACE
    // or if it's the first free place
    public DeckToMatrix(DeckPosition deck, CasualPosition cas){
        this.deck = deck;
        this.cas = cas;
        this.isToRejectedStack = false;
        this.isMade = false;
    }

    @Override
    public boolean execute() {
        if(!isToRejectedStack){
            CasualPosition cas1 = (CasualPosition) cas;
            Color color = deck.getCard().getColor();
            Face face = deck.getCard().getFace();
            if(!cas1.isEmpty()){
                isMade = false;
                return false;
            }
            if(board.areAllRowsAssigned()){
                if(cas1.getTargetFace() == face && cas1.getRow().getColor() == color){
                    return true;
                }
                if(board.getFirstRowWithEmptyPosition().getColor() == cas1.getRow().getColor()){
                    if(board.getFirstRowWithEmptyPosition().getFirstEmptyPosition() == cas1 ){
                        return true;
                    }
                    return false;
                }
                return false;
            }
            if(!board.areAllRowsAssigned()){
                if(!cas1.getRow().isColorAssigned()){
                    for(Row row : board.getRows()){
                        //moze byc kolor przypisany do innego Row
                        if(row != cas1.getRow() && row.getColor() == color ) return false;
                    }
                    //set empty = false
                    if(cas1.getTargetFace() == face){
                        cas1.getRow().assignColor(color);
                        cas1.putCard(deck.getCard());
                        return true;
                    }
                    if(!board.getFirstRowWithEmptyPosition().isColorAssigned()){
                        if(board.getFirstRowWithEmptyPosition().getFirstEmptyPosition() == cas1){
                            return true;
                        }
                        return false;
                    }
                }
                else{
                    if(cas1.getTargetFace() == face && cas1.getRow().getColor() == color){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void revert() {

    }

    @Override
    public boolean isMade() {
        return this.isMade;
    }

}
