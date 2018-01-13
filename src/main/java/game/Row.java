package game;

import game.Positions.CasualPosition;
import game.Positions.Position;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Kanes on 05.12.2017.
 */

public class Row {

    private final Board board;
    private boolean isColorAssigned;
    private Color color = null;

    private List<CasualPosition> positions = new LinkedList<>();

    public Row(List<Card> cards, Board board){
        if(cards.size() != 8)
            throw new IllegalArgumentException("You have to pass 8 cards to the row");
        this.board = board;
        Face f = Face.SIX;
        for(Card card: cards){
            this.positions.add(new CasualPosition(card, f, this));
            f = f.next();
        }
        checkIfCompleted();
    }

    private void checkIfCompleted() {
        this.isColorAssigned = completedDueToColor() && completedDueToFaces();
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

    //Checks whether in a row are cards of one color
    public boolean completedDueToColor() {
        List<Position> notEmptyPos = this.getPositions().stream()
                .filter(p -> !p.isEmpty())
                .collect(Collectors.toList());
        if(notEmptyPos.isEmpty())
            return false;
        Color cardsColor = notEmptyPos
                .get(0)
                .getCard()
                .getColor();
        return this.getPositions()
                        .stream()
                        .allMatch(p -> !p.isEmpty()
                                && p.getCard().getColor().equals(cardsColor));
    }

    //Checks whether in a row are cards with faces in 6 - king
    public boolean completedDueToFaces(){
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

    public Board getBoard() {
        return board;
    }
}
