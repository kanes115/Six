package game.Moves;

import game.*;
import game.Positions.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;


public class StackToCasual implements Move {

    private StackPosition from;
    private CasualPosition to;
    private boolean isMade = false;
    private Board board;
    private String errorMsg = null;


    public StackToCasual(StackPosition deckOrRejected, CasualPosition casual){
        this.from = deckOrRejected;
        this.to = casual;
        this.board = deckOrRejected.getBoard();
    }


    @Override
    public boolean execute() {
        if(from.getType() == StackPositionType.DECK){
            if(!board.getRejectedPosition().isEmpty())
                return error("Rejected stack is not empty");
            return moveStackCasual();
        }

        if(from.getType() == StackPositionType.REJECTED){
            return moveStackCasual();
        }
        return error("Wrong positions");
    }

    private boolean moveStackCasual(){
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
    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMsg);
    }

}

