package gui;

import game.Color;
import game.Face;

/**
 * Created by michaello on 15.12.17.
 */

public enum Card {

    CARD_2_OF_CLUBS(new game.Card(Color.CLUBS, Face.TWO), "2_of_clubs.png"),
    CARD_3_OF_CLUBS(new game.Card(Color.CLUBS, Face.THREE), "3_of_clubs.png"),
    CARD_4_OF_CLUBS(new game.Card(Color.CLUBS, Face.FOUR), "4_of_clubs.png"),
    CARD_5_OF_CLUBS(new game.Card(Color.CLUBS, Face.FIVE), "5_of_clubs.png"),
    CARD_6_OF_CLUBS(new game.Card(Color.CLUBS, Face.SIX), "6_of_clubs.png"),
    CARD_7_OF_CLUBS(new game.Card(Color.CLUBS, Face.SEVEN), "7_of_clubs.png"),
    CARD_8_OF_CLUBS(new game.Card(Color.CLUBS, Face.EIGHT), "8_of_clubs.png"),
    CARD_9_OF_CLUBS(new game.Card(Color.CLUBS, Face.NINE), "9_of_clubs.png"),
    CARD_10_OF_CLUBS(new game.Card(Color.CLUBS, Face.TEN), "10_of_clubs.png"),
    CARD_JACK_OF_CLUBS(new game.Card(Color.CLUBS, Face.JACK), "jack_of_clubs.png"),
    CARD_QUEEN_OF_CLUBS(new game.Card(Color.CLUBS, Face.QUEEN), "queen_of_clubs.png"),
    CARD_KING_OF_CLUBS(new game.Card(Color.CLUBS, Face.KING), "king_of_clubs.png"),
    CARD_ACE_OF_CLUBS(new game.Card(Color.CLUBS, Face.ACE), "ace_of_clubs.png"),
    CARD_2_OF_DIAMONDS(new game.Card(Color.DIAMONDS, Face.TWO), "2_of_diamonds.png"),
    CARD_3_OF_DIAMONDS(new game.Card(Color.DIAMONDS, Face.THREE), "3_of_diamonds.png"),
    CARD_4_OF_DIAMONDS(new game.Card(Color.DIAMONDS, Face.FOUR), "4_of_diamonds.png"),
    CARD_5_OF_DIAMONDS(new game.Card(Color.DIAMONDS, Face.FIVE), "5_of_diamonds.png"),
    CARD_6_OF_DIAMONDS(new game.Card(Color.DIAMONDS, Face.SIX), "6_of_diamonds.png"),
    CARD_7_OF_DIAMONDS(new game.Card(Color.DIAMONDS, Face.SEVEN), "7_of_diamonds.png"),
    CARD_8_OF_DIAMONDS(new game.Card(Color.DIAMONDS, Face.EIGHT), "8_of_diamonds.png"),
    CARD_9_OF_DIAMONDS(new game.Card(Color.DIAMONDS, Face.NINE), "9_of_diamonds.png"),
    CARD_10_OF_DIAMONDS(new game.Card(Color.DIAMONDS, Face.TEN), "10_of_diamonds.png"),
    CARD_JACK_OF_DIAMONDS(new game.Card(Color.DIAMONDS, Face.JACK), "jack_of_diamonds.png"),
    CARD_QUEEN_OF_DIAMONDS(new game.Card(Color.DIAMONDS, Face.QUEEN), "queen_of_diamonds.png"),
    CARD_KING_OF_DIAMONDS(new game.Card(Color.DIAMONDS, Face.KING), "king_of_diamonds.png"),
    CARD_ACE_OF_DIAMONDS(new game.Card(Color.DIAMONDS, Face.ACE), "ace_of_diamonds.png"),
    CARD_2_OF_HEARTS(new game.Card(Color.HEARTS, Face.TWO), "2_of_hearts.png"),
    CARD_3_OF_HEARTS(new game.Card(Color.HEARTS, Face.THREE), "3_of_hearts.png"),
    CARD_4_OF_HEARTS(new game.Card(Color.HEARTS, Face.FOUR), "4_of_hearts.png"),
    CARD_5_OF_HEARTS(new game.Card(Color.HEARTS, Face.FIVE), "5_of_hearts.png"),
    CARD_6_OF_HEARTS(new game.Card(Color.HEARTS, Face.SIX), "6_of_hearts.png"),
    CARD_7_OF_HEARTS(new game.Card(Color.HEARTS, Face.SEVEN), "7_of_hearts.png"),
    CARD_8_OF_HEARTS(new game.Card(Color.HEARTS, Face.EIGHT), "8_of_hearts.png"),
    CARD_9_OF_HEARTS(new game.Card(Color.HEARTS, Face.NINE), "9_of_hearts.png"),
    CARD_10_OF_HEARTS(new game.Card(Color.HEARTS, Face.TEN), "10_of_hearts.png"),
    CARD_JACK_OF_HEARTS(new game.Card(Color.HEARTS, Face.JACK), "jack_of_hearts.png"),
    CARD_QUEEN_OF_HEARTS(new game.Card(Color.HEARTS, Face.QUEEN), "queen_of_hearts.png"),
    CARD_KING_OF_HEARTS(new game.Card(Color.HEARTS, Face.KING), "king_of_hearts.png"),
    CARD_ACE_OF_HEARTS(new game.Card(Color.HEARTS, Face.ACE), "ace_of_hearts.png"),
    CARD_2_OF_SPADES(new game.Card(Color.SPADES, Face.TWO), "2_of_spades.png"),
    CARD_3_OF_SPADES(new game.Card(Color.SPADES, Face.THREE), "3_of_spades.png"),
    CARD_4_OF_SPADES(new game.Card(Color.SPADES, Face.FOUR), "4_of_spades.png"),
    CARD_5_OF_SPADES(new game.Card(Color.SPADES, Face.FIVE), "5_of_spades.png"),
    CARD_6_OF_SPADES(new game.Card(Color.SPADES, Face.SIX), "6_of_spades.png"),
    CARD_7_OF_SPADES(new game.Card(Color.SPADES, Face.SEVEN), "7_of_spades.png"),
    CARD_8_OF_SPADES(new game.Card(Color.SPADES, Face.EIGHT), "8_of_spades.png"),
    CARD_9_OF_SPADES(new game.Card(Color.SPADES, Face.NINE), "9_of_spades.png"),
    CARD_10_OF_SPADES(new game.Card(Color.SPADES, Face.TEN), "10_of_spades.png"),
    CARD_JACK_OF_SPADES(new game.Card(Color.SPADES, Face.JACK), "jack_of_spades.png"),
    CARD_QUEEN_OF_SPADES(new game.Card(Color.SPADES, Face.QUEEN), "queen_of_spades.png"),
    CARD_KING_OF_SPADES(new game.Card(Color.SPADES, Face.KING), "king_of_spades.png"),
    CARD_ACE_OF_SPADES(new game.Card(Color.SPADES, Face.ACE), "ace_of_spades.png"),
    //TODO
    // Waiting for change empty card representation in model
    CARD_EMPTY(null, "empty.png");

    private static final String PATH_TO_CARDS = "/gui/cards/";
    private String pathToFilename;
    private game.Card card;


    Card(game.Card card, String filename) {
        this.card = card;
        this.pathToFilename = PATH_TO_CARDS + filename;
    }

    public game.Card getCard() {
        return card;
    }

    public String getPathToFilename() {
        return pathToFilename;
    }

}

