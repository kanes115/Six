package game;


public class GameController {

    private  MoveChecker checker;
    private ExecutedMovesStack movesStack;
    private Board board;
    private State state;


    public GameController(ExecutedMovesStack moveStack, MoveChecker checker, Board board, State state){
        this.checker = checker;
        this.movesStack = moveStack;
        this.board = board;
        this.state = state;
    }

    public boolean tryMove(Move move){
        if(checker.isCorrect(move)){
            move.execute(board);
            return true;
        }
        else {
            return false;
        }
    }

}
