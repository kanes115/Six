package hints;

import game.Board;
import game.Positions.CasualPosition;
import game.Positions.Position;
import game.Row;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jkret on 25/01/2018.
 */
public class HintsPositions {
    private Board board;

    public HintsPositions(Board board) {
        this.board = board;
    }

    private void addUnnecessaryPair(List<Position[]> unnecessaryPairs, Position position1, Position position2) {
        unnecessaryPairs.add(new Position[]{position1, position2});
    }

    private void decideIfUnnecessary(List<Position[]> unnecessaryPairs, Position position1, Position position2) {
        if (HintsUtils.checkIfPositionsAreTaken(position1, position2)) {
            if (HintsUtils.checkIfPairIsUnnecessary(position1, position2)) {
                addUnnecessaryPair(unnecessaryPairs, position1, position2);
            }
        }
    }

    private void checkWithRemainingPositions(List<Position[]> unnecessaryPairs, int rowIndex, Position basePosition) {
        List<Row> rows = board.getRows();
        for (int compareRowIndex = rowIndex; compareRowIndex < 4; compareRowIndex++) {
            Row compareRow = rows.get(compareRowIndex);
            for (CasualPosition comparePosition : compareRow.getPositions()) {
                decideIfUnnecessary(unnecessaryPairs, basePosition, comparePosition);
            }
        }
        Position rejectedPosition = board.getRejectedPosition();
        Position deckPosition = board.getDeckPosition();
        decideIfUnnecessary(unnecessaryPairs, basePosition, rejectedPosition);
        decideIfUnnecessary(unnecessaryPairs, basePosition, deckPosition);
    }

    private void checkRowForUnnecessaryPairs(List<Position[]> unnecessaryPairs, int rowIndex) {
        List<Row> rows = board.getRows();
        Row row = rows.get(rowIndex);
        for (CasualPosition position : row.getPositions()) {
            if (!position.isEmpty()) {
                checkWithRemainingPositions(unnecessaryPairs, rowIndex, position);
            }
        }
    }

    public List<Position[]> getUnnecessaryPairs() {
        List<Position[]> unnecessaryPairs = new LinkedList<>();
        for (int rowIndex = 0; rowIndex < 4; rowIndex++) {
            checkRowForUnnecessaryPairs(unnecessaryPairs, rowIndex);
        }
        return unnecessaryPairs;
    }

    public List<Position> showActionable() {
        List<Position> actionables = new LinkedList<>();
        board.getRows().forEach(row -> {
            row.getPositions().forEach(casualPosition -> {
                if (casualPosition.isEmpty()) {
                    board.getRows().forEach(row1 -> {
                        row1.getPositions().forEach(casualPosition1 -> {
                            if (HintsUtils.cardCanGo(row, casualPosition, casualPosition1)) {
                                actionables.add(casualPosition1);
                            }
                        });
                    });

                    Position rejectedPosition = board.getRejectedPosition();
                    Position deckPosition = board.getDeckPosition();
                    if (HintsUtils.cardCanGo(row, casualPosition, rejectedPosition)) {
                        actionables.add(rejectedPosition);
                    } else if (HintsUtils.cardCanGo(row, casualPosition, deckPosition)) {
                        actionables.add(deckPosition);
                    }
                }
            });
        });
        return actionables;
    }

    public List<CasualPosition> getDeletableDuplicates() {
        List<Row> rows = board.getRows();
        List<CasualPosition> duplicates = new LinkedList<>();
        rows.forEach(row -> {
            row.getPositions().forEach(casualPosition -> {
                if (HintsUtils.checkIfCardInPlace(casualPosition, row)) {
                    rows.forEach(row1 -> {
                        row1.getPositions().forEach(casualPosition1 -> {
                            if (HintsUtils.checkIfDuplicate(casualPosition, casualPosition1)) {
                                duplicates.add(casualPosition1);
                            }
                        });
                    });
                }
            });
        });
        return duplicates;
    }
}
