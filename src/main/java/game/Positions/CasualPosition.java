package game.Positions;

import game.Card;
import game.Face;
import game.Row;

/**
 * Created by Kanes on 05.12.2017.
 */
public class CasualPosition extends Position {

    private Card card;
    private Row row;
    private boolean isEmpty;
    private final Face targetFace;

    public CasualPosition(Face targetFace, Row row){
        this(null, targetFace, row);
    }

    public CasualPosition(Card card, Face targetFace, Row row){
        super(row.getBoard());
        this.card = card;
        this.row = row;
        this.isEmpty = false;
        this.targetFace = targetFace;
    }

    @Override
    public void putCard(Card card) {
        if(isEmpty){
            this.card = card;
            isEmpty = false;
        }
    }

    @Override
    public Card getCard() {
        return card;
    }

    @Override
    public Card removeCard() {
        if(!isEmpty) {
            Card tmp = card;
            card = null;
            isEmpty = true;
            return tmp;
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return isEmpty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CasualPosition)) return false;

        CasualPosition that = (CasualPosition) o;

        if (isEmpty != that.isEmpty) return false;
        if (card != null ? !card.equals(that.card) : that.card != null) return false;
        if (!row.equals(that.row)) return false;
        return targetFace == that.targetFace;
    }

    @Override
    public int hashCode() {
        int result = card != null ? card.hashCode() : 0;
        result = 31 * result + row.hashCode();
        result = 31 * result + (isEmpty ? 1 : 0);
        result = 31 * result + targetFace.hashCode();
        return result;
    }

    @Override
    public String toString(){
        if(this.isEmpty())
            return "empty";
        return card.toString();
    }

    public boolean cardFaceMatchPosition() {
        return !this.isEmpty() && this.targetFace.equals(this.getCard().getFace());
    }

    public boolean cardColorMatchRow(){
        return !this.isEmpty() && this.getRow().isColorAssigned()
                && this.getRow().getColor() == this.getCard().getColor();
    }


    public Face getTargetFace(){
        return targetFace;
    }


    public Row getRow() {
        return row;
    }
}
