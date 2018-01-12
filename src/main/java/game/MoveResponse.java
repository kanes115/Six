package game;

public class MoveResponse {

    private String msg;
    private boolean wasOk;

    public MoveResponse(){
        msg = null;
        wasOk = true;
    }

    public MoveResponse(String msg){
        this.msg = msg;
        this.wasOk = false;
    }

    public String getErrorMessage(){
        return msg;
    }

    public boolean wasOk(){
        return wasOk;
    }
}
