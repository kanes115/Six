package game.Moves;

import game.State;

/**
 * Created by Kanes on 05.12.2017.
 */

public interface Move {

    boolean execute();

    void revert();

    boolean isMade();

    String getErrorMessage();
}
