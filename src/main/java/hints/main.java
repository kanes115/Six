package hints;

import score.Score;

import java.util.List;

public class main {
    public static void main(String[] args) {
        Highscores highscores = new HighscoresHTTP();

        highscores.addScore(new Score("aaa", 111111111));
        highscores.addScore(new Score("bbb", 222222222));
        highscores.addScore(new Score("ccc", 333333333));
        List<Score> scores = highscores.getHighscores(0);
        for (Score score : scores) {
            System.out.println(score.getName());
            System.out.println(score.getScore());
        }
    }
}
