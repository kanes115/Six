package game.Moves;

import game.Positions.CasualPosition;
import game.Positions.Position;
import game.Positions.StackPosition;

public class MoveFactory {

    public static Move getMove(Position position, boolean isDelete){
        if(isDelete)
            return new DeleteDuplicate(position);
        return new AssignColorOnCard((CasualPosition) position);
    }

    public static Move getMove(Position position1, Position position2, boolean isDelete){
        if(isDelete)
            return new DeleteUnnecessaryPair(position1, position2);
        if(position1 instanceof CasualPosition && position2 instanceof CasualPosition)
            return new InsideMatrixRelocation((CasualPosition) position1,
                    (CasualPosition) position2);

        if(position1 instanceof StackPosition && position2 instanceof StackPosition)
            return new DeckToRejected((StackPosition) position1, (StackPosition) position2);
        if(position1 instanceof StackPosition)
            return new StackToCasual((StackPosition) position1, (CasualPosition) position2);
        return new StackToCasual((StackPosition) position2, (CasualPosition) position1);
    }


}
