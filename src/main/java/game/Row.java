package game;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kanes on 05.12.2017.
 */

public class Row {

    private final boolean isColorAssigned;
    private Color color = null;

    private List<CasualPosition> positions;

    public Row(List<Card> cards){
        this.positions = cards.stream().map(CasualPosition::new).collect(Collectors.toList());
        this.isColorAssigned = false;
    }

    public void assignColor(Color color){ this.color = color; }

    public boolean isColorAssigned(){
        return isColorAssigned;
    }

    public List<CasualPosition> getPositions() {
        return positions;
    }

    // Removes a card from position if such position exists
    public void removeCard(Position pos){
        this.positions.stream().filter(p -> p.equals(pos) && !p.isEmpty()).forEach(p -> {
            pos.removeCard();
        });
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString(){
        return positions.stream().map(CasualPosition::toString).reduce("", (a, b) -> a + b);
    }
}
