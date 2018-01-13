package game.Positions;

import game.Board;
import game.Card;

import java.util.Stack;

public class RejectedPosition extends StackPosition{

    public RejectedPosition(Stack<Card> cards, Board board) {
        super(cards, board);
    }

    public RejectedPosition(Board board) {
        super(board);
    }


}
