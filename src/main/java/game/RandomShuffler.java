package game;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kanes on 05.12.2017.
 */
public class RandomShuffler implements CardShuffler {


    private List<Card> deck;
    private int cardsDispensed = 0;

    public RandomShuffler() {
        this.deck = new LinkedList<Card>();
        for (Color color : Color.values()) {
            for (Face face : Face.values()) {
                deck.add(new Card(color, face));
                deck.add(new Card(color, face));
            }
        }
        Collections.shuffle(deck);
    }


    @Override
    public List<Card> getRestCards() {
        List<Card> cards = deck.subList(cardsDispensed, deck.size());
        cardsDispensed = deck.size() - 1;
        return cards;
    }

    //TODO check correctness
    @Override
    public List<Card> getNextCards(int n) {
        List<Card> cards = deck.subList(cardsDispensed, cardsDispensed + n);
        cardsDispensed += n;
        return cards;
    }

    @Override
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
}
