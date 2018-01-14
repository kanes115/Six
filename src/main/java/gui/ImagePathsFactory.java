package gui;


import game.Card;
import game.Color;
import game.Face;
import game.Positions.Position;
import gui.dictionary.AppConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by michaello on 11.01.18.
 */
public class ImagePathsFactory {

    private static final Map<game.Card, String> paths = new HashMap<>();

    static {
        paths.put(new Card(Color.CLUBS, Face.TWO), "2_of_clubs.png");
        paths.put(new Card(Color.CLUBS, Face.THREE), "3_of_clubs.png");
        paths.put(new Card(Color.CLUBS, Face.FOUR), "4_of_clubs.png");
        paths.put(new Card(Color.CLUBS, Face.FIVE), "5_of_clubs.png");
        paths.put(new Card(Color.CLUBS, Face.SIX), "6_of_clubs.png");
        paths.put(new Card(Color.CLUBS, Face.SEVEN), "7_of_clubs.png");
        paths.put(new Card(Color.CLUBS, Face.EIGHT), "8_of_clubs.png");
        paths.put(new Card(Color.CLUBS, Face.NINE), "9_of_clubs.png");
        paths.put(new Card(Color.CLUBS, Face.TEN), "10_of_clubs.png");
        paths.put(new Card(Color.CLUBS, Face.JACK), "jack_of_clubs.png");
        paths.put(new Card(Color.CLUBS, Face.QUEEN), "queen_of_clubs.png");
        paths.put(new Card(Color.CLUBS, Face.KING), "king_of_clubs.png");
        paths.put(new Card(Color.CLUBS, Face.ACE), "ace_of_clubs.png");
        paths.put(new Card(Color.DIAMONDS, Face.TWO), "2_of_diamonds.png");
        paths.put(new Card(Color.DIAMONDS, Face.THREE), "3_of_diamonds.png");
        paths.put(new Card(Color.DIAMONDS, Face.FOUR), "4_of_diamonds.png");
        paths.put(new Card(Color.DIAMONDS, Face.FIVE), "5_of_diamonds.png");
        paths.put(new Card(Color.DIAMONDS, Face.SIX), "6_of_diamonds.png");
        paths.put(new Card(Color.DIAMONDS, Face.SEVEN), "7_of_diamonds.png");
        paths.put(new Card(Color.DIAMONDS, Face.EIGHT), "8_of_diamonds.png");
        paths.put(new Card(Color.DIAMONDS, Face.NINE), "9_of_diamonds.png");
        paths.put(new Card(Color.DIAMONDS, Face.TEN), "10_of_diamonds.png");
        paths.put(new Card(Color.DIAMONDS, Face.JACK), "jack_of_diamonds.png");
        paths.put(new Card(Color.DIAMONDS, Face.QUEEN), "queen_of_diamonds.png");
        paths.put(new Card(Color.DIAMONDS, Face.KING), "king_of_diamonds.png");
        paths.put(new Card(Color.DIAMONDS, Face.ACE), "ace_of_diamonds.png");
        paths.put(new Card(Color.HEARTS, Face.TWO), "2_of_hearts.png");
        paths.put(new Card(Color.HEARTS, Face.THREE), "3_of_hearts.png");
        paths.put(new Card(Color.HEARTS, Face.FOUR), "4_of_hearts.png");
        paths.put(new Card(Color.HEARTS, Face.FIVE), "5_of_hearts.png");
        paths.put(new Card(Color.HEARTS, Face.SIX), "6_of_hearts.png");
        paths.put(new Card(Color.HEARTS, Face.SEVEN), "7_of_hearts.png");
        paths.put(new Card(Color.HEARTS, Face.EIGHT), "8_of_hearts.png");
        paths.put(new Card(Color.HEARTS, Face.NINE), "9_of_hearts.png");
        paths.put(new Card(Color.HEARTS, Face.TEN), "10_of_hearts.png");
        paths.put(new Card(Color.HEARTS, Face.JACK), "jack_of_hearts.png");
        paths.put(new Card(Color.HEARTS, Face.QUEEN), "queen_of_hearts.png");
        paths.put(new Card(Color.HEARTS, Face.KING), "king_of_hearts.png");
        paths.put(new Card(Color.HEARTS, Face.ACE), "ace_of_hearts.png");
        paths.put(new Card(Color.SPADES, Face.TWO), "2_of_spades.png");
        paths.put(new Card(Color.SPADES, Face.THREE), "3_of_spades.png");
        paths.put(new Card(Color.SPADES, Face.FOUR), "4_of_spades.png");
        paths.put(new Card(Color.SPADES, Face.FIVE), "5_of_spades.png");
        paths.put(new Card(Color.SPADES, Face.SIX), "6_of_spades.png");
        paths.put(new Card(Color.SPADES, Face.SEVEN), "7_of_spades.png");
        paths.put(new Card(Color.SPADES, Face.EIGHT), "8_of_spades.png");
        paths.put(new Card(Color.SPADES, Face.NINE), "9_of_spades.png");
        paths.put(new Card(Color.SPADES, Face.TEN), "10_of_spades.png");
        paths.put(new Card(Color.SPADES, Face.JACK), "jack_of_spades.png");
        paths.put(new Card(Color.SPADES, Face.QUEEN), "queen_of_spades.png");
        paths.put(new Card(Color.SPADES, Face.KING), "king_of_spades.png");
        paths.put(new Card(Color.SPADES, Face.ACE), "ace_of_spades.png");
        paths.put(null, "back.png");
    }

    private ImagePathsFactory() {

    }

    public static String getPathToCardImage(Card card) {
        return AppConstants.PATH_TO_CARDS_IMAGES + paths.get(card);
    }

    public static String getPathToCardImage(Position position) {
        return AppConstants.PATH_TO_CARDS_IMAGES + paths.get(position == null || position.isEmpty() ? null : position.getCard());
    }
}
