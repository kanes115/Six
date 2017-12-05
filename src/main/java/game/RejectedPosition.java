package game;

/**
 * Created by Kanes on 05.12.2017.
 */
public class RejectedPosition implements Position {

    @Override
    public boolean putCard(Card card) {
        return false;
    }

    @Override
    public Card getCard() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public PositionType getPositionType() {
        return null;
    }
}
