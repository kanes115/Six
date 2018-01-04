package game;


import game.Moves.Move;

public class GameController {

    private  MoveChecker checker;
    private Board board;
    private State state;
    private Timer timer;

    public GameController(MoveChecker checker, Board board, State state,Timer timer){
        this.checker = checker;
        this.board = board;
        this.state = state;
        this.timer = timer;
    }

    public boolean tryMove(Move move){ return move.execute(board);}

    public void startClock(){ timer.start();}

    public void startPreparing(){
        state = State.PREPARING;
    }

    public void startRound(){
        state = State.INPROGRESS;
    }

    public State getGameState(){
        return this.state;
    }

    public State hasGameEnded(int option){
        if(option == 1){
            if(board.hasTheSameColorInRow()) return State.WON;
            else return State.LOST;
        }
        else{
            if(board.getDeckPosition().isEmpty() && board.hasTheSameColorInRow()) return State.WON;
            else return State.LOST;
        }
    }



}
