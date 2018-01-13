package game.Positions;

import game.Board;
import game.Card;

/**
 * Created by Kanes on 05.12.2017.
 */
public abstract class Position {

    private final Board board;

    public Position(Board board){
        this.board = board;
    }

    public abstract void putCard(Card card);

    public abstract Card getCard();

    public abstract Card removeCard();

    public abstract boolean isEmpty();

    public Board getBoard(){
       return this.board;
    }
}