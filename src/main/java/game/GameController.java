package game;


import game.Moves.Move;

public class GameController {

    private Board board;
    private State state;
    private Timer timer;
    private boolean isHard;

    public GameController(Board board, State state,Timer timer){
        this.board = board;
        this.state = state;
        this.timer = timer;
    }

    public boolean tryMove(Move move){ return move.execute(board);}

    public void startEasy(){
        timer.start();
        isHard = false;
    }

    public void startHard(){
        timer.start();
        isHard = true;
    }

    public int getTime(){
        return timer.getTime();
    }

    public void startPreparing(){
        state = State.PREPARING;
    }

    public void startRound(){
        state = State.INPROGRESS;
    }

    public State getGameState(){
        return this.state;
    }

    public boolean hasGameEnded(){
        if(!isHard){
            timer.stop();
            return board.hasTheSameColorInRow();
        }
        timer.stop();
        return board.getDeckPosition().isEmpty() && board.hasTheSameColorInRow();
    }

    public void updateGameState(){
        if(!isHard){
            if(board.hasTheSameColorInRow()) state = State.WON;
            else state = State.LOST;
        }
        else{
            if(board.getDeckPosition().isEmpty() && board.hasTheSameColorInRow()) state = State.WON;
            else state = State.LOST;
        }
    }

}
