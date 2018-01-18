package game.Moves;

import game.*;
import game.Positions.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


// TODO: Change name!!! But do it at the end of the project not to affect GUI team work
// Note that the name is not relevant right now, it should be something like
// InvolvingStackMove

public class FromStack implements Move {

    private Position from;
    private Position to;
    private boolean isMade = false;
    private Board board;
    private String errorMsg = "";


    // It moves a card from stack to rejected stack.
    public FromStack(DeckPosition deck, RejectedPosition rejected){
        this.from = deck;
        this.to = rejected;
        this.board = deck.getBoard();
    }

    // It relocates a card from stack top to matrix.
    // It does it only if the card is moved to it's FINAL PLACE
    // or if it's the first free place
    public FromStack(DeckPosition deck, CasualPosition casual){
        this.from = deck;
        this.to = casual;
        this.board = deck.getBoard();
    }

    // Moves a card from Rejected Stack to casual position if possible
    public FromStack(RejectedPosition rejected, CasualPosition casual){
        this.from = rejected;
        this.to = casual;
        this.board = rejected.getBoard();
    }


    @Override
    public boolean execute() {
        if(from.getClass() == DeckPosition.class && to.getClass() == CasualPosition.class){
            if(!board.getRejectedPosition().isEmpty())
                return error("Rejected stack is not empty");
            return moveStackCasual();
        }

        if(from.getClass() == DeckPosition.class && to.getClass() == RejectedPosition.class){
            if(board.hasFreePositions())
                return error("There are free positions on the board");
            return move(from, to);
        }
        if(from.getClass() == RejectedPosition.class && to.getClass() == CasualPosition.class){
            return moveStackCasual();
        }
        return error("Wrong positions");
    }

    private boolean moveStackCasual(){
        StackPosition from = (StackPosition) this.from;
        CasualPosition to = (CasualPosition) this.to;

        if(!to.isEmpty())
            return error("Destination position is occupied");

        if(faceMatch(from, to) && to.getRow().isColorAssigned()){
            if(colorMatch(from, to))
                return move(from, to);
            return maybeError("Color of this row is not the same as card's one");
        }

        if(to.getRow().isColorAssigned())
            return maybeError("This face does not match");

        if(faceMatch(from, to) && !to.getRow().isColorAssigned()){
            Color colorToAssign = from.getCard().getColor();
            if(board.getAssignedColors().contains(colorToAssign))
                return maybeError("This color is already assigned to a row");
            System.out.println("Assigning colo!!");
            to.getRow().assignColor(colorToAssign);
            return move(from, to);
        }

        return maybeError("unknown");
    }

    private boolean move(Position from, Position to) {
        to.putCard(from.removeCard());
        isMade = true;
        return true;
    }

    private boolean faceMatch(StackPosition p, CasualPosition p2){
        return p2.getTargetFace() == p.getCard().getFace();
    }


    private boolean colorMatch(StackPosition p, CasualPosition p2){
        return p2.getRow().getColor() == p.getCard().getColor();
    }

    // Checks if it wasn't the first empty position
    // otherwise returns an error with message `msg`
    private boolean maybeError(String msg){
        if(!board.hasFreePositions())
            return error(msg);
        if(board.getFirstRowWithEmptyPosition().getFirstEmptyPosition() == to)
            return move(from, to);
        return error(msg);
    }

    private boolean error(String msg) {
        isMade = false;
        errorMsg = msg;
        return false;
    }

    @Override
    public void revert() {
        throw new NotImplementedException();
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
