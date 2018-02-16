package game;

import com.google.inject.Inject;
import game.Positions.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kanes on 05.12.2017.
 */

public class Board {

    private List<Row> rows = new LinkedList<>();
    private CardShuffler shuffler;
    private final StackPosition deck = new StackPosition(this, StackPositionType.DECK);
    private final StackPosition rejected = new StackPosition(this, StackPositionType.REJECTED);

    private static final int LOWER_ROW = 0;
    private static final int UPPER_ROW = 3;
    private static final int LOWER_COLUMN = 0;
    private static final int UPPER_COLUMN = 5;


    @Inject
    public Board(CardShuffler shuffler){
        this.shuffler = shuffler;
        fillUpRows();
    }

    public List <Row> getRows(){
        return this.rows;
    }

    public Row getRowInColor(Color color){
        for(Row r : rows)
            if (r.getColor() == color) return r;
        return null;
    }

    // Not used, to be removed
    // DEPRECATED
    public void removeCards(CasualPosition one, CasualPosition two){
        rows.forEach(r -> r.removeCard(one));
        rows.forEach(r -> r.removeCard(two));
    }



    public CasualPosition getPositionAt(int row, int col){
        if(row < LOWER_ROW || row > UPPER_ROW || col < LOWER_COLUMN || col > UPPER_COLUMN)
            throw new IllegalArgumentException("Indexes out of bounds");
        return rows.get(row).getPositions().get(col);
    }

    public StackPosition getDeckPosition(){
        return deck;
    }

    public StackPosition getRejectedPosition() { return rejected;}

    private void fillUpRows() {
        for(int i = 0; i < 4; i++)
            rows.add(new Row(this.shuffler.getNextCards(8), this));
        deck.putCards(this.shuffler.getRestCards());
    }

    @Override
    public String toString(){
        return String.join("\n", Arrays.asList(rows.get(0).toString(),
                rows.get(1).toString(),
                rows.get(2).toString(),
                rows.get(3).toString()
                ));
    }



    public boolean areAllCardsInPlace(){
      for(Row row : rows){
          if(!row.completedDueToColor() || !row.completedDueToFaces()){
              return false;
          }
      }
      return true;
    }

    public boolean areAllRowsAssigned(){
        for(Row row : rows){
            if(!row.isColorAssigned()) return false;
        }
        return true;
    }

    public boolean hasFreePositions(){
        for(Row row : rows){
            if(row.hasEmptyPostion()) return true;
        }
        return false;
    }

    public Row getFirstRowWithEmptyPosition(){
        for(Row row : this.rows){
            if(row.hasEmptyPostion()) return row;
        }
        return null;
    }

    public List<Color> getAssignedColors(){
        return rows.stream()
                .filter(Row::isColorAssigned)
                .map(Row::getColor)
                .distinct()
                .collect(Collectors.toList());
    }


}
