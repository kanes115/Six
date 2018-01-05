package gui;

/**
 * Created by michaello on 15.12.17.
 */

public enum Cards {
    CARD_2_OF_CLUBS("2_of_clubs.png"),
    CARD_3_OF_CLUBS("3_of_clubs.png"),
    CARD_4_OF_CLUBS("4_of_clubs.png"),
    CARD_5_OF_CLUBS("5_of_clubs.png"),
    CARD_6_OF_CLUBS("6_of_clubs.png"),
    CARD_7_OF_CLUBS("7_of_clubs.png"),
    CARD_8_OF_CLUBS("8_of_clubs.png"),
    CARD_9_OF_CLUBS("9_of_clubs.png"),
    CARD_10_OF_CLUBS("10_of_clubs.png"),
    CARD_JACK_OF_CLUBS("jack_of_clubs.png"),
    CARD_QUEEN_OF_CLUBS("queen_of_clubs.png"),
    CARD_KING_OF_CLUBS("king_of_clubs.png"),
    CARD_ACE_OF_CLUBS("ace_of_clubs.png"),
    CARD_2_OF_DIAMONDS("2_of_diamonds.png"),
    CARD_3_OF_DIAMONDS("3_of_diamonds.png"),
    CARD_4_OF_DIAMONDS("4_of_diamonds.png"),
    CARD_5_OF_DIAMONDS("5_of_diamonds.png"),
    CARD_6_OF_DIAMONDS("6_of_diamonds.png"),
    CARD_7_OF_DIAMONDS("7_of_diamonds.png"),
    CARD_8_OF_DIAMONDS("8_of_diamonds.png"),
    CARD_9_OF_DIAMONDS("9_of_diamonds.png"),
    CARD_10_OF_DIAMONDS("10_of_diamonds.png"),
    CARD_JACK_OF_DIAMONDS("jack_of_diamonds.png"),
    CARD_QUEEN_OF_DIAMONDS("queen_of_diamonds.png"),
    CARD_KING_OF_DIAMONDS("king_of_diamonds.png"),
    CARD_ACE_OF_DIAMONDS("ace_of_diamonds.png"),
    CARD_2_OF_HEARTS("2_of_hearts.png"),
    CARD_3_OF_HEARTS("3_of_hearts.png"),
    CARD_4_OF_HEARTS("4_of_hearts.png"),
    CARD_5_OF_HEARTS("5_of_hearts.png"),
    CARD_6_OF_HEARTS("6_of_hearts.png"),
    CARD_7_OF_HEARTS("7_of_hearts.png"),
    CARD_8_OF_HEARTS("8_of_hearts.png"),
    CARD_9_OF_HEARTS("9_of_hearts.png"),
    CARD_10_OF_HEARTS("10_of_hearts.png"),
    CARD_JACK_OF_HEARTS("jack_of_hearts.png"),
    CARD_QUEEN_OF_HEARTS("queen_of_hearts.png"),
    CARD_KING_OF_HEARTS("king_of_hearts.png"),
    CARD_ACE_OF_HEARTS("ace_of_hearts.png"),
    CARD_2_OF_SPADES("2_of_spades.png"),
    CARD_3_OF_SPADES("3_of_spades.png"),
    CARD_4_OF_SPADES("4_of_spades.png"),
    CARD_5_OF_SPADES("5_of_spades.png"),
    CARD_6_OF_SPADES("6_of_spades.png"),
    CARD_7_OF_SPADES("7_of_spades.png"),
    CARD_8_OF_SPADES("8_of_spades.png"),
    CARD_9_OF_SPADES("9_of_spades.png"),
    CARD_10_OF_SPADES("10_of_spades.png"),
    CARD_JACK_OF_SPADES("jack_of_spades.png"),
    CARD_QUEEN_OF_SPADES("queen_of_spades.png"),
    CARD_KING_OF_SPADES("king_of_spades.png"),
    CARD_ACE_OF_SPADES("ace_of_spades.png"),
    CARD_EMPTY("empty.png");

    private static final String PATH_TO_CARDS = "/gui/cards/";
    private String pathToFilename;


    Cards(String filename) {
        this.pathToFilename = PATH_TO_CARDS + filename;
    }

    public String getPathToFilename() {
        return pathToFilename;
    }

}

