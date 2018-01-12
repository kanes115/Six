package game;

import game.Positions.CasualPosition;
import game.Positions.Position;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kanes on 05.12.2017.
 */

public class Row {

    private boolean isColorAssigned;
    private Color color = null;

    private List<CasualPosition> positions = new LinkedList<>();

    public Row(List<Card> cards){
        if(cards.size() != 8)
            throw new IllegalArgumentException("You have to pass 8 cards to the row");
        Face f = Face.SIX;
        for(Card card: cards){
            this.positions.add(new CasualPosition(card, f, this));
            f = f.next();
        }
        this.isColorAssigned = false;
    }

    public void assignColor(Color color){
        this.color = color;
        isColorAssigned = true;
    }

    public void unAssignColor(){
        this.color = null;
        isColorAssigned = false;
    }

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
        return positions
                .stream()
                .map(CasualPosition::toString).reduce("", (a, b) -> a + b);
    }

    //It also checks whether all positions are filled
    // needs renaming - not done not to break the api
    public boolean hasTheSameColorInRow() {
        return this.getPositions()
                        .stream()
                        .allMatch(c -> c.isEmpty()
                                || !c.getCard().getColor().equals(this.getColor()));
    }

    //It also checks whether all positions are filled
    // needs renaming - not done not to break the api
    public boolean hasFacesInOrder(){
        return this.getPositions()
                .stream()
                .allMatch(CasualPosition::cardFaceMatchPosition);
    }

    public boolean hasEmptyPostion(){
        for(CasualPosition c : this.getPositions()){
            if(c.isEmpty()) return true;
        }
        return false;
    }

    public CasualPosition getFirstEmptyPosition(){
        for(CasualPosition c : this.getPositions()){
            if(c.isEmpty()) return c;
        }
        return null;
    }


}
