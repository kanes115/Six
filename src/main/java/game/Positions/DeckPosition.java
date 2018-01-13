package game.Positions;

import game.Board;
import game.Card;

import java.util.Stack;

public class DeckPosition extends StackPosition {

    public DeckPosition(Stack<Card> cards, Board board) {
        super(cards, board);
    }

    public DeckPosition(Board board) {
        super(board);
    }
}
