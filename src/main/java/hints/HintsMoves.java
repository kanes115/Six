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

    public List<Move> getActionables() {
        MoveFactory moveFactory = new MoveFactory();
        List<Move> actionables = new LinkedList<>();
        board.getRows().forEach(row -> {
            row.getPositions().forEach(casualPosition -> {
                if (casualPosition.isEmpty()) {
                    board.getRows().forEach(row1 -> {
                        row1.getPositions().forEach(casualPosition1 -> {
                            if (HintsUtils.cardCanGo(row, casualPosition, casualPosition1)) {
                                actionables.add(MoveFactory.getMove(casualPosition1, casualPosition, false));
                            }
                        });
                    });
                    if (!board.getRejectedPosition().isEmpty()) {
                        if (HintsUtils.cardCanGo(row, casualPosition, board.getRejectedPosition())) {
                            actionables.add(MoveFactory.getMove(board.getRejectedPosition(), casualPosition, false));
                        }
                    } else {
                        if (HintsUtils.cardCanGo(row, casualPosition, board.getDeckPosition())) {
                            actionables.add(MoveFactory.getMove(board.getDeckPosition(), casualPosition, false));
                        }
                    }
                }
            });
        });
        return actionables;
    }

    public List<Move> getDeletableDuplicates() {
        List<Row> rows = board.getRows();
        List<Move> duplicates = new LinkedList<>();
        rows.forEach(row -> {
            row.getPositions().forEach(casualPosition -> {
                if (HintsUtils.checkIfCardInPlace(casualPosition, row)) {
                    rows.forEach(row1 -> {
                        row1.getPositions().forEach(casualPosition1 -> {
                            if (HintsUtils.checkIfDuplicate(casualPosition, casualPosition1)) {
                                duplicates.add(MoveFactory.getMove(casualPosition, true));
                            }
                        });
                    });
                }
            });
        });
        return duplicates;
    }

    public boolean checkIfAnyMovesLeft() {
        List<Move> unnecessaryPairs = getUnnecessaryPairs();
        List<Move> actionables = getActionables();
        List<Move> duplicates = getDeletableDuplicates();

        return unnecessaryPairs.size() + actionables.size() + duplicates.size() > 0;
    }
}
