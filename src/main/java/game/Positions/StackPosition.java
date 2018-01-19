package game.Positions;

import game.Board;
import game.Card;

import java.util.List;
import java.util.Stack;

public abstract class StackPosition extends Position {

    private Stack<Card> cards;

    public StackPosition(Stack<Card> cards, Board board){
        super(board);
        this.cards = cards;
    }

    public StackPosition(Board board){
        super(board);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StackPosition)) return false;

        StackPosition that = (StackPosition) o;

        return cards.equals(that.cards);
    }

    @Override
    public int hashCode() {
        return cards.hashCode();
    }
}
