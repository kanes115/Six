package game;

/**
 * Created by Kanes on 05.12.2017.
 */

public interface Move {

    boolean execute();

    void revert();

    State inWhatStateAvailable();
}
