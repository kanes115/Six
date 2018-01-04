package game;


import game.Moves.Move;

public class GameController {

    private  MoveChecker checker;
    private Board board;
    private State state;


    public GameController(MoveChecker checker, Board board, State state){
        this.checker = checker;
        this.board = board;
        this.state = state;
    }

    public boolean tryMove(Move move){
        if(checker.isCorrect(move)){
            move.execute();
            return true;
        }
        else {
            return false;
        }
    }

}
