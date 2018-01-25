package hints;

import game.GameController;
import game.RandomShuffler;


public class main {
    public static void main(String[] args) {
        RandomShuffler randomShuffler = new RandomShuffler();
        GameController gameController = new GameController(randomShuffler, true);
        System.out.println(gameController.getBoard().toString());
//        Hints hints = new Hints(gameController.getBoard());
    }
}
