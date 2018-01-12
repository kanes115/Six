package game.Moves;

import game.*;
import game.Positions.*;

// TODO: Change name!!! But do it at the end of the project not to affect GUI team work
// Note that the name is not relevant right now, it should be something like
// InvolvingStackMove

public class DeckToMatrix implements Move {

    private  Position deck;
    private  Position cas;
    private boolean isToRejectedStack;
    private boolean isFromRejectedStack;
    private boolean isFromDeckStack;
    private boolean isMade;
    private Board board;
    private String errorMsg = "";


    // It moves a card from deck to rejected stack.
    public DeckToMatrix(DeckPosition deck, RejectedPosition rej, Board board){
        this.deck = deck;
        this.cas = rej;
        this.isToRejectedStack = true;
        this.isFromRejectedStack = false;
        this.isFromDeckStack = false;
        this.isMade = false;
        this.board = board;
    }

    // It relocates a card from deck top to matrix.
    // It does it only if the card is moved to it's FINAL PLACE
    // or if it's the first free place
    public DeckToMatrix(DeckPosition deck, CasualPosition cas, Board board){
        this.deck = deck;
        this.cas = cas;
        this.isFromDeckStack = true;
        this.isToRejectedStack = false;
        this.isFromRejectedStack = false;
        this.isMade = false;
        this.board = board;
    }

    // Moves a card from Rejected Stack to casual position if possible
    public DeckToMatrix(RejectedPosition rej, CasualPosition cas, Board board){
        this.deck = deck;
        this.cas = cas;
        this.isFromRejectedStack = true;
        this.isFromDeckStack = false;
        this.isToRejectedStack = false;
        this.isMade = false;
        this.board = board;
    }

    // TODO REFACTOR THIS METHOD
    @Override
    public boolean execute() {

        if(isFromDeckStack) {
            if (!board.getRejectedPosition().isEmpty()) {
                return false;
            }
        }

        if(isFromRejectedStack || isFromDeckStack ){
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
        }if(isToRejectedStack){
            //gdy sa na planszy wolne miejsca a my na stosik odrzuconych to false
            if(board.hasFreePositions()){
                isMade = false;
                return false;
            }
            else{
                //casual zla nazwa - ma byc rej
                Card card = deck.getCard();
                deck.removeCard();
                cas.putCard(card);
                isMade = true;
                return true;
            }

        }
        return false;
    }


    @Override
    public void revert() {
        //TODO
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
