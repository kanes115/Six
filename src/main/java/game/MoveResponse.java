package game;

public class MoveResponse {

    private final String errorMessage;
    private final boolean wasOk;

    public MoveResponse(){
        this(null);
    }

    public MoveResponse(String errorMessage){
        this.errorMessage = errorMessage;
        this.wasOk = errorMessage == null;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public boolean wasOk(){
        return wasOk;
    }
}
