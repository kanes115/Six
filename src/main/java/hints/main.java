package hints;

import score.Score;

public class main {
    public static void main(String[] args) {
        Highscores highscores = new HighscoresHTTP();

        highscores.addScore(new Score("aaa", 111111111));
    }
}
