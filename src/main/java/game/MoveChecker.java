package game;

/**
 * Created by aga on 10.12.17.
 */
public class MoveChecker {

    private  Move move;
    private  Board board;

    public MoveChecker(Move move){
        this.move = move;
        this.board = board;
    }

    public boolean isCorrect(Move move){
        //TO IMPLEMENT
        return true;
    }

    public boolean hasGameEnded(Board board){
        return true;
    }

}
