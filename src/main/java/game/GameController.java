package game;


import com.google.inject.Guice;
import com.google.inject.Injector;
import game.Moves.Move;
import hints.ITimer;
import hints.NormalTimer;


public class GameController {

    private Board board;
    private State state;
    private ITimer timer = new NormalTimer();
    private boolean isHard;

    public GameController(boolean isHard){
        Injector injector = Guice.createInjector(new BoardModule());
        Board someBoard = injector.getInstance(Board.class);
        initialize(someBoard, isHard);
    }

    public GameController(CardShuffler shuffler, boolean isHard){
        initialize(new Board(shuffler), isHard);
    }

    private void initialize(Board board, boolean isHard){
        this.board = board;
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
