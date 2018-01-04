package game;


public class DeleteMove implements Move {

    private final CasualPosition pos1;
    private final CasualPosition pos2;

    private Card savedPos1;
    private Card savedPos2;

    private State state;

    public DeleteMove(CasualPosition pos1, CasualPosition pos2, State state){
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.state = state;
    }

    @Override
    public boolean execute() {
        if(state != State.PREPARING)
            return false;
        if(pos1.isEmpty() || pos2.isEmpty())
            return false;
        if(!pos1.getCard().getFace().isRemovable() ||
                !pos2.getCard().getFace().isRemovable())
            return false;

        savedPos1 = pos1.getCard();
        savedPos2 = pos2.getCard();
        pos1.removeCard();
        pos2.removeCard();

        return true;
    }

    @Override
    public void revert() {
        pos1.putCard(savedPos1);
        pos2.putCard(savedPos2);
    }

    @Override
    public State inWhatStateAvailable() {
        return State.PREPARING;
    }
}
