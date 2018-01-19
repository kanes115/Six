package hints;

import game.Board;
import game.Card;
import game.GameController;
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

    public List<Move> getUnnecessaryDups() {
        List<Row> rows = board.getRows();
        List<Move> dups = new LinkedList<>();
        Row row = rows.get(0);
        for (CasualPosition position : row.getPositions()) {
            for (int i = 1; i < 4; i++) {
                Row checkRow = rows.get(i);
                for(CasualPosition position1 : checkRow.getPositions()) {
                    if (checkIfDups(position, position1)) {
                        dups.add(MoveFactory.getMove(position, position1, true));
                    }
                }
            }
            if(!board.getRejectedPosition().isEmpty()) {
                if (checkIfDups(position, board.getRejectedPosition())){
                    dups.add(MoveFactory.getMove(position, board.getRejectedPosition(), true));
                }
            } else {
                if (checkIfDups(position, board.getDeckPosition())) {
                    dups.add(MoveFactory.getMove(position, board.getDeckPosition(), true));
                }
            }
        }
        return dups;
    }


    private boolean cardCanGo(Row row, CasualPosition position, Position position1) {
        return (!position1.isEmpty() && position.getTargetFace() == position1.getCard().getFace() && (!row.isColorAssigned() || row.getColor() == position.getCard().getColor()));
    }

    public List<Move> showActionable() {
        MoveFactory moveFactory = new MoveFactory();
        List<Move> moves = new LinkedList<>();
        board.getRows().forEach(row -> {
            row.getPositions().forEach(casualPosition -> {
                if (casualPosition.isEmpty()) {
                    board.getRows().forEach(row1 -> {
                        row1.getPositions().forEach(casualPosition1 -> {
                            if(cardCanGo(row, casualPosition, casualPosition1)) {
                                moves.add(MoveFactory.getMove(casualPosition1 ,casualPosition, false));
                            }
                        });
                    });
                    if(!board.getRejectedPosition().isEmpty()) {
                        if(cardCanGo(row, casualPosition, board.getRejectedPosition())) {
                            moves.add(MoveFactory.getMove(board.getRejectedPosition() ,casualPosition, false));
                        }
                    } else {
                        if(cardCanGo(row, casualPosition, board.getDeckPosition())) {
                            moves.add(MoveFactory.getMove(board.getDeckPosition(), casualPosition, false));
                        }
                    }
                }
            });
        });
        return moves;
    }

    private boolean checkIfCardInPlace(CasualPosition position, Row row) {
        return row.isColorAssigned() && !position.isEmpty() && row.getColor() == position.getCard().getColor() && position.cardFaceMatchPosition();
    }

    private boolean checkIfDuplicate(Position position, Position position1) {
        return !position.equals(position1) && !position1.isEmpty() && position.getCard().equals(position1.getCard());
    }

    public List<Move> deletableDuplicates() {
        List<Row> rows = board.getRows();
        List<Move> actionable = new LinkedList<>();
        rows.forEach(row -> {
            row.getPositions().forEach(casualPosition -> {
                if(checkIfCardInPlace(casualPosition, row)) {
                    rows.forEach(row1 -> {
                        row1.getPositions().forEach(casualPosition1 -> {
                            if(checkIfDuplicate(casualPosition, casualPosition1)) {
                                actionable.add(MoveFactory.getMove(casualPosition, true));
                            }
                        });
                    });
                }
            });
        });
        return actionable;
    }

    public boolean movesLeft() {
        List<Move> unnecessary = getUnnecessaryDups();
        List<Move> actionable = showActionable();
        List<Move> dups = deletableDuplicates();

        return unnecessary.size() + actionable.size() + dups.size() > 0;
    }
}
