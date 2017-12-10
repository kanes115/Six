package game;

/**
 * Created by Kanes on 05.12.2017.
 */

public class RequestResponse {

    private Board newBoard;
    private Request req;
    private boolean isCorrect;
    private boolean wasExecuted;

    public RequestResponse(Board newBoard, Request req, boolean isCorrect, boolean wasExecuted){
        this.newBoard = newBoard;
        this.req = req;
        this.isCorrect = isCorrect;
        this.wasExecuted = wasExecuted;
    }

    public Board getUpdatedBoard() {
        return newBoard;
    }

    public Request getRequest() {
        return req;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public boolean isWasExecuted() {
        return wasExecuted;
    }
}
