package game;

import com.google.inject.Inject;
import game.Positions.CasualPosition;
import game.Positions.Position;
import game.Positions.StackPosition;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kanes on 05.12.2017.
 */

public class Board {

    private List<Row> rows = new LinkedList<>();
    private CardShuffler shuffler;
    private StackPosition deck = new StackPosition();

    @Inject
    public Board(CardShuffler shuffler){
        this.shuffler = shuffler;
        fillUpRows();
    }

    public void removeCards(CasualPosition one, CasualPosition two){
        rows.forEach(r -> r.removeCard(one));
        rows.forEach(r -> r.removeCard(two));
    }

    public Position getPositionAt(int row, int col){
        return rows.get(row).getPositions().get(col);
    }

    public StackPosition getDeckPosition(){
        return deck;
    }

    private void fillUpRows() {
        for(int i = 0; i < 4; i++)
            rows.add(new Row(this.shuffler.getNextCards(6)));
        deck.putCards(this.shuffler.getRestCards());
    }

    @Override
    public String toString(){
        return rows.get(0).toString() + '\n'
                + rows.get(1).toString() + '\n'
                + rows.get(2).toString() + '\n'
                + rows.get(3).toString();
    }

    public boolean hasTheSameColorInRow(){
        for(Row row : rows){
            if(row.isColorAssigned())
                return false;
            Face current = Face.TWO;
            for(CasualPosition c : row.getPositions()){
                if(!c.getCard().getFace().equals(current))
                    return false;
                current = current.next();
            }
        }
        return true;
    }

}
