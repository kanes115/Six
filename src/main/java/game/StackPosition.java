package game;

import java.util.List;
import java.util.Stack;

public class StackPosition implements Position {

    private Stack<Card> cards;

    public StackPosition(Stack<Card> cards){
        this.cards = cards;
    }

    public StackPosition(){
        cards = new Stack<>();
    }


    @Override
    public void putCard(Card card) {
        this.cards.push(card);
    }

    public void putCards(List<Card> cards){
        this.cards.addAll(cards);
    }

    public void putCards(Stack<Card> cards){
        this.cards.addAll(cards);
    }

    @Override
    public Card getCard() {
        return this.cards.peek();
    }

    @Override
    public void removeCard() {
        this.cards.pop();
    }

    @Override
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
