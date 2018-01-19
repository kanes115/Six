package game.Positions;

import game.Board;
import game.Card;

import java.util.List;
import java.util.Stack;

public class StackPosition extends Position {

    private Stack<Card> cards;
    private final StackPositionType type;

    public StackPosition(Stack<Card> cards, Board board, StackPositionType type){
        this(board, type);
        this.cards = cards;
    }

    public StackPosition(Board board, StackPositionType type){
        super(board);
        this.type = type;
        cards = new Stack<>();
    }


    @Override
    public void putCard(Card card) {
        this.cards.push(card);
    }

    public void putCards(List<Card> cards){
        this.cards.addAll(cards);
    }

    @Override
    public Card getCard() {
        return this.cards.peek();
    }

    @Override
    public Card removeCard() {
        return this.cards.pop();
    }

    @Override
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public StackPositionType getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return cards.hashCode();
    }
}
