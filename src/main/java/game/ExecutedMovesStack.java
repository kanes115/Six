package game;

/**
 * Created by Kanes on 05.12.2017.
 */
public interface ExecutedMovesStack {

    public void addMove();
    public Move getLastMove();
    public boolean isEmpty();
}
