package score;

import com.google.gson.Gson;

import java.io.IOException;
import java.sql.SQLException;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        port(8080);
        ScoreDB scoreDB = new ScoreDB();
        scoreDB.resetDB();
        post("/score", (req, res) -> {
            Score score = new Gson().fromJson(req.body(), Score.class);
            System.out.println(score.getName());
            scoreDB.saveScore(score);
            return "thanks";
        });

        get("/scores", (req, res) -> {
            ScorePage sp = scoreDB.getScores();
            return new Gson().toJson(sp);
        });
    }
}
