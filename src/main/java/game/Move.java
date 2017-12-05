package game;

/**
 * Created by Kanes on 05.12.2017.
 */

public interface Move {

    Position getStartPosition();

    Position getEndPosition();

    void execute(Board board);

    void revert(Board board);
}
