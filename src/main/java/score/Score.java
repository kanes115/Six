package score;

public class Score {
    private final String name;
    private final long score;

    public Score(String name, long score) {
        this.name = name;
        this.score = score;
    }


    public String getName() {
        return name;
    }

    public long getScore() {
        return score;
    }
}
