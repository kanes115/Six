package hints;

import game.Positions.CasualPosition;
import game.Positions.Position;
import game.Row;

public class HintsUtils {
    public static boolean checkIfPairIsUnnecessary(Position position, Position position1) {
        return position.getCard().equals(position1.getCard()) &&
                position.getCard().getFace().isUnnecessary() &&
                !position.equals(position1);
    }

    public static boolean checkIfPositionsAreTaken(Position position1, Position position2) {
        return !position1.isEmpty() && !position2.isEmpty();
    }

    public static boolean cardCanGo(Row row, CasualPosition position, Position position1) {
        return !position1.isEmpty() &&
                position.getTargetFace() == position1.getCard().getFace() &&
                (!row.isColorAssigned() || row.getColor() == position1.getCard().getColor());
    }

    public static boolean checkIfCardInPlace(CasualPosition position, Row row) {
        return row.isColorAssigned() &&
                !position.isEmpty() &&
                row.getColor() == position.getCard().getColor() &&
                position.cardFaceMatchPosition();
    }

    public static boolean checkIfDuplicate(Position position, Position position1) {
        return !position.isEmpty() &&
                !position1.isEmpty() &&
                !position.equals(position1) &&
                position.getCard().equals(position1.getCard());
    }
}
