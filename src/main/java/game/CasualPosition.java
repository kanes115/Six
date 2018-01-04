package game;

/**
 * Created by Kanes on 05.12.2017.
 */
public class CasualPosition implements Position {

    private Card card;
    private boolean isEmpty;
    private Face targetFace;

    public CasualPosition(Face targetFace){
        this.card = null;
        this.isEmpty = true;
        this.targetFace = targetFace;
    }

    public CasualPosition(Card card, Face targetFace){
        this.card = card;
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
    public void removeCard() {
        if(!isEmpty) {
            card = null;
            isEmpty = true;
        }
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


}
