package game.Positions;

import game.Card;

import java.util.Stack;

public class DeckPosition extends StackPosition {

    public DeckPosition(Stack<Card> cards) {
        super(cards);
    }

    public DeckPosition() {
        super();
    }
}
