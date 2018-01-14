package game;

import com.google.inject.Inject;
import game.Positions.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kanes on 05.12.2017.
 */

public class Board {

    private List<Row> rows = new LinkedList<>();
    private CardShuffler shuffler;
    private DeckPosition deck = new DeckPosition(this);
    private RejectedPosition rejected = new RejectedPosition(this);

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

    public void removeCards(CasualPosition one, CasualPosition two){
        rows.forEach(r -> r.removeCard(one));
        rows.forEach(r -> r.removeCard(two));
    }

    public CasualPosition getPositionAt(int row, int col){
        if(row < 0 || row > 3 || col < 0 || col > 5)
            throw new IllegalArgumentException("Indexes out of bounds");
        return rows.get(row).getPositions().get(col);
    }

    public DeckPosition getDeckPosition(){
        return deck;
    }

    public RejectedPosition getRejectedPosition() { return rejected;}

    private void fillUpRows() {
        for(int i = 0; i < 4; i++)
            rows.add(new Row(this.shuffler.getNextCards(8), this));
        deck.putCards(this.shuffler.getRestCards());
    }

    @Override
    public String toString(){
        return rows.get(0).toString() + '\n'
                + rows.get(1).toString() + '\n'
                + rows.get(2).toString() + '\n'
                + rows.get(3).toString();
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
