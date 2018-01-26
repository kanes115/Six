package hints;

import game.Board;
import game.Moves.Move;
import game.Moves.MoveFactory;
import game.Positions.CasualPosition;
import game.Positions.Position;
import game.Row;

import java.util.LinkedList;
import java.util.List;

public class HintsMoves {
    private Board board;

    public HintsMoves(Board board) {
        this.board = board;
    }

    private void decideIfUnnecessary(List<Move> unnecessaryPairs, Position position1, Position position2) {
        if (HintsUtils.checkIfPositionsAreTaken(position1, position2)) {
            if (HintsUtils.checkIfPairIsUnnecessary(position1, position2)) {
                unnecessaryPairs.add(MoveFactory.getMove(position1, position2, true));
            }
        }
    }

    private void checkWithRemainingPositions(List<Move> unnecessaryPairs, int rowIndex, Position basePosition) {
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

    private void checkRowForUnnecessaryPairs(List<Move> unnecessaryPairs, int rowIndex) {
        List<Row> rows = board.getRows();
        Row row = rows.get(rowIndex);
        for (CasualPosition position : row.getPositions()) {
            if (!position.isEmpty()) {
                checkWithRemainingPositions(unnecessaryPairs, rowIndex, position);
            }
        }
    }

    public List<Move> getUnnecessaryPairs() {
        List<Move> unnecessaryPairs = new LinkedList<>();
        for (int rowIndex = 0; rowIndex < 4; rowIndex++) {
            checkRowForUnnecessaryPairs(unnecessaryPairs, rowIndex);
        }
        return unnecessaryPairs;
    }

    private void checkRowForCardsToRelocate(List<Move> actionables,
                                            int baseRowIndex,
                                            int compareRowIndex,
                                            CasualPosition availablePosition) {
        List<Row> rows = board.getRows();
        Row baseRow = rows.get(baseRowIndex);
        Row compareRow = rows.get(compareRowIndex);
        for (Position position : compareRow.getPositions()) {
            if (HintsUtils.cardCanGo(baseRow, availablePosition, position)) {
                actionables.add(MoveFactory.getMove(position, availablePosition, false));
            }
        }
    }

    private void checkStacksForCardsToRelocate(List<Move> actionables,
                                               int baseRowIndex,
                                               CasualPosition availablePosition) {
        List<Row> rows = board.getRows();
        Row baseRow = rows.get(baseRowIndex);
        Position rejectedPosition = board.getRejectedPosition();
        Position deckPosition = board.getDeckPosition();
        if (HintsUtils.cardCanGo(baseRow, availablePosition, rejectedPosition)) {
            actionables.add(MoveFactory.getMove(rejectedPosition, availablePosition, false));
        }
        if (HintsUtils.cardCanGo(baseRow, availablePosition, deckPosition)) {
            actionables.add(MoveFactory.getMove(deckPosition, availablePosition, false));
        }
    }

    private void lookForCardsToRelocate(List<Move> actionables, int baseRowIndex, CasualPosition availablePosition) {
        for (int compareRowIndex = 0; compareRowIndex < 4; compareRowIndex++) {
            checkRowForCardsToRelocate(actionables, baseRowIndex, compareRowIndex, availablePosition);
        }
        checkStacksForCardsToRelocate(actionables, baseRowIndex, availablePosition);
    }

    private void checkRowForCardsToRelocate(List<Move> actionables, int baseRowIndex) {
        List<Row> rows = board.getRows();
        Row baseRow = rows.get(baseRowIndex);
        for (CasualPosition casualPosition : baseRow.getPositions()) {
            if (casualPosition.isEmpty()) {
                lookForCardsToRelocate(actionables, baseRowIndex, casualPosition);
            }
        }
    }

    public List<Move> getActionables() {
        List<Move> actionables = new LinkedList<>();
        for (int baseRowIndex = 0; baseRowIndex < 4; baseRowIndex++) {
            checkRowForCardsToRelocate(actionables, baseRowIndex);
        }
        return actionables;
    }

    private void checkIfHasDuplicatesInRow(List<Move> duplicates,
                                           CasualPosition checkedPosition,
                                           int compareRowIndex) {
        List<Row> rows = board.getRows();
        Row compareRow = rows.get(compareRowIndex);
        for (CasualPosition position : compareRow.getPositions()) {
            if (HintsUtils.checkIfDuplicate(checkedPosition, position)) {
                duplicates.add(MoveFactory.getMove(position, true));
            }
        }
    }

    private void checkIfHasDuplicatesOnBoard(List<Move> duplicates, CasualPosition checkedPosition) {
        for (int compareRowIndex = 0; compareRowIndex < 4; compareRowIndex++) {
            checkIfHasDuplicatesInRow(duplicates, checkedPosition, compareRowIndex);
        }
    }

    private void checkRowForCardsInPlace(List<Move> duplicates, int baseRowIndex) {
        List<Row> rows = board.getRows();
        Row baseRow = rows.get(baseRowIndex);
        for (CasualPosition casualPosition : baseRow.getPositions()) {
            if (HintsUtils.checkIfCardInPlace(casualPosition, baseRow)) {
                checkIfHasDuplicatesOnBoard(duplicates, casualPosition);
            }
        }
    }

    public List<Move> getDeletableDuplicates() {
        List<Move> duplicates = new LinkedList<>();
        for (int baseRowIndex = 0; baseRowIndex < 4; baseRowIndex++) {
            checkRowForCardsInPlace(duplicates, baseRowIndex);
        }
        return duplicates;
    }

//    public List<Move> getDeletableDuplicates() {
//        List<Row> rows = board.getRows();
//        List<Move> duplicates = new LinkedList<>();
//        rows.forEach(row -> {
//            row.getPositions().forEach(casualPosition -> {
//                if (HintsUtils.checkIfCardInPlace(casualPosition, row)) {
//                    rows.forEach(row1 -> {
//                        row1.getPositions().forEach(casualPosition1 -> {
//                            if (HintsUtils.checkIfDuplicate(casualPosition, casualPosition1)) {
//                                duplicates.add(MoveFactory.getMove(casualPosition, true));
//                            }
//                        });
//                    });
//                }
//            });
//        });
//        return duplicates;
//    }

    public boolean checkIfAnyMovesLeft() {
        List<Move> unnecessaryPairs = getUnnecessaryPairs();
        List<Move> actionables = getActionables();
        List<Move> duplicates = getDeletableDuplicates();

        return unnecessaryPairs.size() + actionables.size() + duplicates.size() > 0;
    }
}
