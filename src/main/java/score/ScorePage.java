package score;

import java.util.Collections;
import java.util.List;

public class ScorePage {
    private final int page;
    private final int pageSize;
    private final String prev;
    private final String self;
    private final String next;
    private final List<Score> scores;

    public ScorePage(int page, int pageSize, String prev, String self, String next, List<Score> scores) {
        this.page = page;
        this.pageSize = pageSize;
        this.prev = prev;
        this.self = self;
        this.next = next;
        this.scores = Collections.unmodifiableList(scores);
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getPrev() {
        return prev;
    }

    public String getSelf() {
        return self;
    }

    public String getNext() {
        return next;
    }

    public List<Score> getScores() {
        return scores;
    }
}
