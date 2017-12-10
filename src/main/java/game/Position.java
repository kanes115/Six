package game;

/**
 * Created by Kanes on 05.12.2017.
 */
public interface Position {

    boolean putCard(Card card);

    Card getCard();

    void removeCard();

    boolean isEmpty();

    PositionType getPositionType();

}
