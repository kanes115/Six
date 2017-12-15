package game;

/**
 * Created by Kanes on 05.12.2017.
 */
public class CasualPosition implements Position {

    private final Card card;
    private final boolean isEmpty;

    public CasualPosition(){
        this.card = null;
        this.isEmpty = true;
    }

    public CasualPosition(Card card){
        this.card = card;
        this.isEmpty = false;
    }

    @Override
    public void putCard(Card card) {

    }

    @Override
    public Card getCard() {
        return card;
    }

    @Override
    public void removeCard() {}

    @Override
    public boolean isEmpty() {
        return isEmpty;
    }

    @Override
    public PositionType getPositionType() {
        return null;
    }

    @Override
    public String toString(){
        if(this.isEmpty())
            return "empty";
        return card.toString();
    }


}
