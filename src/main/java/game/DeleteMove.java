package game;


public class DeleteMove implements Move {

    private Position start, end;

    public DeleteMove(Position start, Position end){
        this.start = start;
        this.end = end;
    }

    @Override
    public Position getStartPosition() {
        return start;
    }

    @Override
    public Position getEndPosition() {
        return end;
    }

    @Override
    public void execute(Board board) {
        //TODO
    }

    @Override
    public void revert(Board board) {
        //TODO
    }
}
