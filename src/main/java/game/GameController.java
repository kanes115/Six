package game;


import com.google.inject.Guice;
import com.google.inject.Injector;
import game.Moves.Move;
import hints.ITimer;

import javax.inject.Inject;

public class GameController {

    private Board board;
    private State state;
    private ITimer timer;
    private boolean isHard;

    @Inject
    public GameController(ITimer timer, boolean isHard){
        this.timer = timer;
        Injector injector = Guice.createInjector(new BoardModule());
        this.board = injector.getInstance(Board.class);
        this.state = State.INPROGRESS;
        this.isHard = isHard;
        if (isHard)
            startHard();
        else
            startEasy();
    }

    public Board getBoard(){
        return this.board;
    }

    public boolean tryMove(Move move){
        boolean res = move.execute();
        if (hasGameEnded())
            updateGameState();
        return res;
    }

    public long getTime(){
        return timer.getTimeLong();
    }

    public State getGameState(){
        return this.state;
    }

    private boolean hasGameEnded(){
        if(!isHard){
            timer.stop();
            return board.areAllCardsInPlace();
        }
        timer.stop();
        return false;
//        return board.getDeckPosition().isEmpty() && board.areAllCardsInPlace();
    }

    private void updateGameState(){
        if(!isHard){
            if(board.areAllCardsInPlace()) state = State.WON;
            else state = State.LOST;
        }
        else{
            if(board.getDeckPosition().isEmpty() && board.areAllCardsInPlace()) state = State.WON;
            else state = State.LOST;
        }
    }

    private void startEasy(){
        timer.start();
        isHard = false;
        state = State.INPROGRESS;
    }

    private void startHard(){
        timer.start();
        isHard = true;
        state = State.INPROGRESS;
    }

}
