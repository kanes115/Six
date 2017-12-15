package hints;

import score.Score;

import java.util.List;

public interface Highscores {
    List<Score> getHighscores(int page);
    void addScore(Score score);
}
