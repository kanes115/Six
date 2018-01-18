package game.Moves;

import java.util.Optional;

/**
 * Created by Kanes on 05.12.2017.
 */

public interface Move {

    boolean execute();

    void revert();

    boolean isMade();

    Optional<String> getErrorMessage();
}
