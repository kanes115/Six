package hints;

import game.Board;
import game.Positions.CasualPosition;
import game.Positions.Position;
import game.Row;

import java.util.LinkedList;
import java.util.List;

public class HintsPositions {
    private final int ROW_COUNT = 4;
    private Board board;

    public HintsPositions(Board board) {
        this.board = board;
    }

    private void decideIfUnnecessary(List<Position[]> unnecessaryPairs, Position position1, Position position2) {
        if (HintsUtils.checkIfPositionsAreTaken(position1, position2)) {
            if (HintsUtils.checkIfPairIsUnnecessary(position1, position2)) {
                unnecessaryPairs.add(new Position[]{position1, position2});
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
        for (int rowIndex = 0; rowIndex < ROW_COUNT; rowIndex++) {
            checkRowForUnnecessaryPairs(unnecessaryPairs, rowIndex);
        }
        return unnecessaryPairs;
    }

    private void checkRowForCardsToRelocate(List<Position> actionables,
                                            int baseRowIndex,
                                            int compareRowIndex,
                                            CasualPosition availablePosition) {
        List<Row> rows = board.getRows();
        Row baseRow = rows.get(baseRowIndex);
        Row compareRow = rows.get(compareRowIndex);
        for (Position position : compareRow.getPositions()) {
            if (HintsUtils.cardCanGo(baseRow, availablePosition, position)) {
                actionables.add(position);
            }
        }
    }

    private void checkStacksForCardsToRelocate(List<Position> actionables,
                                               int baseRowIndex,
                                               CasualPosition availablePosition) {
        List<Row> rows = board.getRows();
        Row baseRow = rows.get(baseRowIndex);
        Position rejectedPosition = board.getRejectedPosition();
        Position deckPosition = board.getDeckPosition();
        if (HintsUtils.cardCanGo(baseRow, availablePosition, rejectedPosition)) {
            actionables.add(rejectedPosition);
        }
        if (HintsUtils.cardCanGo(baseRow, availablePosition, deckPosition)) {
            actionables.add(deckPosition);
        }
    }

    private void lookForCardsToRelocate(List<Position> actionables, int baseRowIndex, CasualPosition availablePosition) {
        for (int compareRowIndex = 0; compareRowIndex < ROW_COUNT; compareRowIndex++) {
            checkRowForCardsToRelocate(actionables, baseRowIndex, compareRowIndex, availablePosition);
        }
        checkStacksForCardsToRelocate(actionables, baseRowIndex, availablePosition);
    }

    private void checkRowForCardsToRelocate(List<Position> actionables, int baseRowIndex) {
        List<Row> rows = board.getRows();
        Row baseRow = rows.get(baseRowIndex);
        for (CasualPosition casualPosition : baseRow.getPositions()) {
            if (casualPosition.isEmpty()) {
                lookForCardsToRelocate(actionables, baseRowIndex, casualPosition);
            }
        }
    }

    public List<Position> getActionables() {
        List<Position> actionables = new LinkedList<>();
        for (int baseRowIndex = 0; baseRowIndex < ROW_COUNT; baseRowIndex++) {
            checkRowForCardsToRelocate(actionables, baseRowIndex);
        }
        return actionables;
    }

    private void checkIfHasDuplicatesInRow(List<CasualPosition> duplicates,
                                           CasualPosition checkedPosition,
                                           int compareRowIndex) {
        List<Row> rows = board.getRows();
        Row compareRow = rows.get(compareRowIndex);
        for (CasualPosition position : compareRow.getPositions()) {
            if (HintsUtils.checkIfDuplicate(checkedPosition, position)) {
                duplicates.add(position);
            }
        }
    }

    private void checkIfHasDuplicatesOnBoard(List<CasualPosition> duplicates, CasualPosition checkedPosition) {
        for (int compareRowIndex = 0; compareRowIndex < ROW_COUNT; compareRowIndex++) {
            checkIfHasDuplicatesInRow(duplicates, checkedPosition, compareRowIndex);
        }
    }

//    private void checkIfHasDuplicatesOnStacks(List<CasualPosition> duplicates,
//                                              CasualPosition checkedPosition) {
//        Position rejectedPosition = board.getRejectedPosition();
//        Position deckPosition = board.getDeckPosition();
//
//        if (HintsUtils.checkIfDuplicate(checkedPosition, rejectedPosition)) {
//            duplicates.add(rejectedPosition);
//        }
//        if (HintsUtils.checkIfDuplicate(checkedPosition, deckPosition)) {
//            duplicates.add(deckPosition);
//        }
//    }

    private void checkRowForCardsInPlace(List<CasualPosition> duplicates, int baseRowIndex) {
        List<Row> rows = board.getRows();
        Row baseRow = rows.get(baseRowIndex);
        for (CasualPosition casualPosition : baseRow.getPositions()) {
            if (HintsUtils.checkIfCardInPlace(casualPosition, baseRow)) {
                checkIfHasDuplicatesOnBoard(duplicates, casualPosition);
            }
        }
    }

    public List<CasualPosition> getDeletableDuplicates() {
        List<CasualPosition> duplicates = new LinkedList<>();
        for (int baseRowIndex = 0; baseRowIndex < ROW_COUNT; baseRowIndex++) {
            checkRowForCardsInPlace(duplicates, baseRowIndex);
        }
        return duplicates;
    }

    public boolean checkIfAnyMovesLeft() {
        List<Position[]> unnecessaryPairs = getUnnecessaryPairs();
        List<Position> actionables = getActionables();
        List<CasualPosition> duplicates = getDeletableDuplicates();

        return unnecessaryPairs.size() + actionables.size() + duplicates.size() > 0 && board.hasFreePositions() &&
                (!board.getDeckPosition().isEmpty() || !board.getRejectedPosition().isEmpty());
    }
}
