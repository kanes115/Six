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

    public Board getBoard(){
        return this.board;
    }

    public boolean tryMove(Move move){
        return move.isMade();
    }

    public void startEasy(){
//        timer.start();
        isHard = false;
        state = State.INPROGRESS;
    }

    public void startHard(){
//        timer.start();
        isHard = true;
        state = State.INPROGRESS;
    }

//    public int getTime(){
//        return timer.getTime();
//    }

    public State getGameState(){
        return this.state;
    }

    public boolean hasGameEnded(){
        if(!isHard){
//            timer.stop();
            return board.areAllCardsInPlace();
        }
//        timer.stop();
        return board.getDeckPosition().isEmpty() && board.areAllCardsInPlace();
    }

    public void updateGameState(){
        if(!isHard){
            if(board.areAllCardsInPlace()) state = State.WON;
            else state = State.LOST;
        }
        else{
            if(board.getDeckPosition().isEmpty() && board.areAllCardsInPlace()) state = State.WON;
            else state = State.LOST;
        }
    }

}
