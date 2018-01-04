package game.Positions;

import game.Card;
import game.Face;
import game.Row;

/**
 * Created by Kanes on 05.12.2017.
 */
public class CasualPosition implements Position {

    private Card card;
    private Row row;
    private boolean isEmpty;
    private Face targetFace;

    public CasualPosition(Face targetFace, Row row){
        this.card = null;
        this.isEmpty = true;
        this.targetFace = targetFace;
        this.row = row;
    }

    public CasualPosition(Card card, Face targetFace, Row row){
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
    public String toString(){
        if(this.isEmpty())
            return "empty";
        return card.toString();
    }

    public boolean cardFaceMatchPosition() {
        return !this.isEmpty() && this.targetFace.equals(this.getCard().getFace());
    }


    public Row getRow() {
        return row;
    }
}
