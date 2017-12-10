package game;

/**
 * Created by Kanes on 05.12.2017.
 */
public class Request {

    private final boolean toExecute;
    private final Move move;

    public Request(Move move, boolean toExecute){
        this.move = move;
        this.toExecute = toExecute;
    }

    Move getMove(){
        return move;
    }

    boolean toExecute(){
        return toExecute;
    }
}
