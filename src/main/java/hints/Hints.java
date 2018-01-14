package hints;

import game.Board;
import game.GameController;
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

    public List<Position[]> getUnnecessaryDups() {
        List<Row> rows = board.getRows();
        List<Position[]> dups = new LinkedList<>();
        Row row = rows.get(0);
        for (CasualPosition position : row.getPositions()) {
            for (int i = 1; i < 4; i++) {
                Row checkRow = rows.get(i);
                for(CasualPosition position1 : checkRow.getPositions()) {
                    if (position.getCard().equals(position1.getCard()) && position.getCard().getFace().isUnnecessary()) {
                        dups.add(new Position[] {position, position1});
                    }
                }
            }
            if(!board.getRejectedPosition().isEmpty() && position.getCard().getFace().isUnnecessary() && board.getRejectedPosition().getCard().equals(position.getCard())) {
                dups.add(new Position[] {position, board.getRejectedPosition()});
            } else if (position.getCard().getFace().isUnnecessary() && board.getDeckPosition().getCard().equals(position.getCard())) {
                dups.add(new Position[] {position, board.getDeckPosition()});
            }
        }
        return dups;
    }

    public List<Position> showActionable() {
        List<Position> actionable = new LinkedList<>();
        board.getRows().forEach(row -> {
            row.getPositions().forEach(casualPosition -> {
                if (casualPosition.isEmpty()) {
                    board.getRows().forEach(row1 -> {
                        row1.getPositions().forEach(casualPosition1 -> {
                            if(!casualPosition1.isEmpty() && casualPosition.getTargetFace() == casualPosition1.getCard().getFace() && (!row.isColorAssigned() || row.getColor() == casualPosition1.getCard().getColor())) {
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

    public List<CasualPosition> deletableDuplicates() {
        List<Row> rows = board.getRows();
        List<CasualPosition> actionable = new LinkedList<>();
        rows.forEach(row -> {
            row.getPositions().forEach(casualPosition -> {
                if(row.isColorAssigned() && !casualPosition.isEmpty() && row.getColor() == casualPosition.getCard().getColor() && casualPosition.cardFaceMatchPosition()) {
                    rows.forEach(row1 -> {
                        row1.getPositions().forEach(casualPosition1 -> {
                            if(!casualPosition.equals(casualPosition1) && !casualPosition1.isEmpty() && casualPosition.getCard().equals(casualPosition1.getCard())) {
                                actionable.add(casualPosition1);
                            }
                        });
                    });
                }
            });
        });
        return actionable;
    }
}
