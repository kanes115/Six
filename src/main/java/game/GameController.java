package game;

import io.reactivex.*;


public class GameController {

    private  MoveChecker checker;
    private ExecutedMovesStack movesStack;
    private Board board;

    public GameController(ExecutedMovesStack moveStack, MoveChecker checker, Board board){
        this.checker = checker;
        this.movesStack = moveStack;
        this.board = board;
    }

    public  Observable<RequestResponse> handleRequest(Observable<Request> request){
        return request.map(r -> nextBoard(r));
    }

    private  RequestResponse nextBoard(Request request) {
        boolean isCorrect;
        boolean wasExecuted;
        Move move = request.getMove();
        RequestResponse response;
        boolean toExecute = request.toExecute();
        if(checker.canMove(move) && toExecute){
            isCorrect = true;
            wasExecuted = true;
            updateBoard(board,move);
             response = new RequestResponse(board,request,isCorrect, wasExecuted );
        }
        else if(checker.canMove(move) && !toExecute){
            isCorrect = true;
            wasExecuted = false;
             response = new RequestResponse(board, request, isCorrect, wasExecuted);
        }
        else{
            isCorrect = false;
            wasExecuted = false;
            response = new RequestResponse(board, request, isCorrect, wasExecuted);
        }
        return response;

    }

    public Board updateBoard(Board board, Move move){
        return board;
    }

}
