package game;

import java.util.List;

/**
 * Created by Kanes on 05.12.2017.
 */

public interface CardShuffler {

    List<Card> getRestCards();
    List<Card> getNextCards(int n);
    Card getNextCard();
    void saveDeck();
}
