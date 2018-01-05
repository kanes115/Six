package game;


import game.Moves.Move;

public class GameController {

    private Board board;
    private State state;
//    private Timer timer;
    private boolean isHard;

    public GameController(Board board, State state){
        this.board = board;
        this.state = state;
    }

    public boolean tryMove(Move move){
//        if(move.inWhatStateAvailable() == this.state){
//            move.execute();
//            return true;
//        }
        return false;
    }

    public void startEasy(){
//        timer.start();
        isHard = false;
    }

    public void startHard(){
//        timer.start();
        isHard = true;
    }

//    public int getTime(){
//        return timer.getTime();
//    }

//    public void startPreparing(){
//        state = State.PREPARING;
//    }

    public void startRound(){
        state = State.INPROGRESS;
    }

    public State getGameState(){
        return this.state;
    }

    public boolean hasGameEnded(){
        if(!isHard){
//            timer.stop();
//            return board.hasTheSameColorInRow();
        }
//        timer.stop();
//        return board.getDeckPosition().isEmpty() && board.hasTheSameColorInRow();
        return true;
    }

    public void updateGameState(){
        if(!isHard){
//            if(board.hasTheSameColorInRow()) state = State.WON;
//            else state = State.LOST;
        }
        else{
            return;
//            if(board.getDeckPosition().isEmpty() && board.hasTheSameColorInRow()) state = State.WON;
//            else state = State.LOST;
        }
    }

}
