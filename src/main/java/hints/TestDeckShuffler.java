package hints;

import game.Card;
import game.CardShuffler;
import game.Color;
import game.Face;

/**
 * Created by jkret on 12/01/2018.
 */
public class TestDeckShuffler extends CardShuffler {
    public TestDeckShuffler() {
        super();
        for (Color color : Color.values()) {
            deck.add(new Card(color, Face.SIX));
            deck.add(new Card(color, Face.SEVEN));
            deck.add(new Card(color, Face.EIGHT));
            deck.add(new Card(color, Face.NINE));
            deck.add(new Card(color, Face.TEN));
            deck.add(new Card(color, Face.JACK));
            deck.add(new Card(color, Face.QUEEN));
            deck.add(new Card(color, Face.KING));
        }
        for (Color  color : Color.values()) {
            for (Face face : Face.values()) {
                if (
                        face != Face.SIX &&
                        face != Face.SEVEN &&
                        face != Face.EIGHT &&
                        face != Face.NINE &&
                        face != Face.TEN &&
                        face != Face.JACK &&
                        face != Face.QUEEN &&
                        face != Face.KING
                        ) {
                    deck.add(new Card(color, face));
                }
            }
        }
    }

    public void saveDeck() {}
}
