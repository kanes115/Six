package game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jkret on 12/01/2018.
 */
public abstract class CardShuffler {
    protected List<Card> deck;
    protected int cardsDispensed;

    public CardShuffler() {
        this.deck = new ArrayList<>();
        this.cardsDispensed = 0;
    }

    public List<Card> getRestCards() {
        List<Card> cards = deck.subList(cardsDispensed, deck.size());
        cardsDispensed = deck.size() - 1;
        return cards;
    }

    public List<Card> getNextCards(int n) {
        List<Card> cards = deck.subList(cardsDispensed, cardsDispensed + n);
        cardsDispensed += n;
        return cards;
    }

    public Card getNextCard() {
        if (cardsDispensed == deck.size())
            return null;
        Card card = deck.get(cardsDispensed);
        cardsDispensed++;
        return card;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public abstract void saveDeck();
}
