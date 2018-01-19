package hints;

import game.Board;
import game.Moves.Move;
import game.Moves.MoveFactory;
import game.Positions.CasualPosition;
import game.Positions.Position;
import game.Row;

import java.util.LinkedList;
import java.util.List;

//TODO compress duplicated logic checks in all the ifs
public class Hints {


    private Board board;

    public Hints(Board board) {
        this.board = board;
    }

    private boolean checkIfDups(Position position, Position position1) {
        return position.getCard().equals(position1.getCard()) && position.getCard().getFace().isUnnecessary();
    }

    public List<Move> getUnnecessaryDupsMoves() {
        List<Row> rows = board.getRows();
        List<Move> dups = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            Row baseRow = rows.get(i);
            for (CasualPosition position : baseRow.getPositions()) {
                if (!position.isEmpty()) {
                    for (int j = i + 1; j < 4; j++) {
                        Row checkRow = rows.get(j);
                        for (CasualPosition position1 : checkRow.getPositions()) {
                            if (!position1.isEmpty()) {
                                if (checkIfDups(position, position1)) {
                                    dups.add(MoveFactory.getMove(position, position1, true));
                                }
                            }
                        }
                    }
                    if (!board.getRejectedPosition().isEmpty()) {
                        if (checkIfDups(position, board.getRejectedPosition())) {
                            dups.add(MoveFactory.getMove(position, board.getRejectedPosition(), true));
                        }
                    }
                    if (!board.getDeckPosition().isEmpty()) {
                        if (checkIfDups(position, board.getDeckPosition())) {
                            dups.add(MoveFactory.getMove(position, board.getDeckPosition(), true));
                        }
                    }
                }
            }
        }
        return dups;
    }

    public List<Position[]> getUnnecessaryDupsPositions() {
        List<Row> rows = board.getRows();
        List<Position[]> dups = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            Row baseRow = rows.get(i);
            for (CasualPosition position : baseRow.getPositions()) {
                if (!position.isEmpty()) {
                    for (int j = i + 1; j < 4; j++) {
                        Row compareRow = rows.get(j);
                        for (CasualPosition position1 : compareRow.getPositions()) {
                            if (!position1.isEmpty()) {
                                if (position.getCard().equals(position1.getCard()) &&
                                        position.getCard().getFace().isUnnecessary()) {
                                    dups.add(new Position[]{position, position1});
                                }
                            }
                        }
                    }
                    if (!board.getRejectedPosition().isEmpty() &&
                            position.getCard().getFace().isUnnecessary() &&
                            board.getRejectedPosition().getCard().equals(position.getCard())) {
                        dups.add(new Position[]{position, board.getRejectedPosition()});
                    } else if (!board.getDeckPosition().isEmpty() &&
                            position.getCard().getFace().isUnnecessary() &&
                            board.getDeckPosition().getCard().equals(position.getCard())) {
                        dups.add(new Position[]{position, board.getDeckPosition()});
                    }
                }
            }
        }
        return dups;
    }


    private boolean cardCanGo(Row row, CasualPosition position, Position position1) {
        return (!position1.isEmpty() && position.getTargetFace() == position1.getCard().getFace() && (!row.isColorAssigned() || row.getColor() == position.getCard().getColor()));
    }

    public List<Move> showActionableMoves() {
        MoveFactory moveFactory = new MoveFactory();
        List<Move> moves = new LinkedList<>();
        board.getRows().forEach(row -> {
            row.getPositions().forEach(casualPosition -> {
                if (casualPosition.isEmpty()) {
                    board.getRows().forEach(row1 -> {
                        row1.getPositions().forEach(casualPosition1 -> {
                            if (cardCanGo(row, casualPosition, casualPosition1)) {
                                moves.add(MoveFactory.getMove(casualPosition1, casualPosition, false));
                            }
                        });
                    });
                    if (!board.getRejectedPosition().isEmpty()) {
                        if (cardCanGo(row, casualPosition, board.getRejectedPosition())) {
                            moves.add(MoveFactory.getMove(board.getRejectedPosition(), casualPosition, false));
                        }
                    } else {
                        if (cardCanGo(row, casualPosition, board.getDeckPosition())) {
                            moves.add(MoveFactory.getMove(board.getDeckPosition(), casualPosition, false));
                        }
                    }
                }
            });
        });
        return moves;
    }

    public List<Position> showActionablePositions() {
        List<Position> actionable = new LinkedList<>();
        board.getRows().forEach(row -> {
            row.getPositions().forEach(casualPosition -> {
                if (casualPosition.isEmpty()) {
                    board.getRows().forEach(row1 -> {
                        row1.getPositions().forEach(casualPosition1 -> {
                            if (!casualPosition1.isEmpty() && casualPosition.getTargetFace() == casualPosition1.getCard().getFace() && (!row.isColorAssigned() || row.getColor() == casualPosition1.getCard().getColor())) {
                                actionable.add(casualPosition1);
                            }
                        });
                    });
                    if (!board.getRejectedPosition().isEmpty() && casualPosition.getTargetFace() == board.getRejectedPosition().getCard().getFace() && (!row.isColorAssigned() || row.getColor() == board.getRejectedPosition().getCard().getColor())) {
                        actionable.add(board.getRejectedPosition());
                    } else if (casualPosition.getTargetFace() == board.getDeckPosition().getCard().getFace() && (!row.isColorAssigned() || row.getColor() == board.getDeckPosition().getCard().getColor())) {
                        actionable.add(board.getDeckPosition());
                    }
                }
            });
        });
        return actionable;
    }

    private boolean checkIfCardInPlace(CasualPosition position, Row row) {
        return row.isColorAssigned() && !position.isEmpty() && row.getColor() == position.getCard().getColor() && position.cardFaceMatchPosition();
    }

    private boolean checkIfDuplicate(Position position, Position position1) {
        return !position.equals(position1) && !position1.isEmpty() && position.getCard().equals(position1.getCard());
    }

    public List<Move> deletableDuplicatesMoves() {
        List<Row> rows = board.getRows();
        List<Move> actionable = new LinkedList<>();
        rows.forEach(row -> {
            row.getPositions().forEach(casualPosition -> {
                if (checkIfCardInPlace(casualPosition, row)) {
                    rows.forEach(row1 -> {
                        row1.getPositions().forEach(casualPosition1 -> {
                            if (checkIfDuplicate(casualPosition, casualPosition1)) {
                                actionable.add(MoveFactory.getMove(casualPosition, true));
                            }
                        });
                    });
                }
            });
        });
        return actionable;
    }

    public List<CasualPosition> deletableDuplicatesPositions() {
        List<Row> rows = board.getRows();
        List<CasualPosition> actionable = new LinkedList<>();
        rows.forEach(row -> {
            row.getPositions().forEach(casualPosition -> {
                if (row.isColorAssigned() && !casualPosition.isEmpty() && row.getColor() == casualPosition.getCard().getColor() && casualPosition.cardFaceMatchPosition()) {
                    rows.forEach(row1 -> {
                        row1.getPositions().forEach(casualPosition1 -> {
                            if (!casualPosition.equals(casualPosition1) && !casualPosition1.isEmpty() && casualPosition.getCard().equals(casualPosition1.getCard())) {
                                actionable.add(casualPosition1);
                            }
                        });
                    });
                }
            });
        });
        return actionable;
    }

    public boolean movesLeft() {
        List<Move> unnecessary = getUnnecessaryDupsMoves();
        List<Move> actionable = showActionableMoves();
        List<Move> dups = deletableDuplicatesMoves();

        return unnecessary.size() + actionable.size() + dups.size() > 0;
    }
}
