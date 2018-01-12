package game.Positions;

import game.Card;

/**
 * Created by Kanes on 05.12.2017.
 */
public interface Position {

    void putCard(Card card);

    Card getCard();

    Card removeCard();

    boolean isEmpty();
}