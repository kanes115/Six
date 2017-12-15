package score;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ScoreDB {

    private Connection connection;
    public ScoreDB () throws IOException {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:test.sqlite");
        } catch ( Exception e ) {
            System.exit(0);
        }
    }

    public void resetDB () throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE SCORE (" +
                                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "score INT NOT NULL," +
                                "name NVARCHAR(50) NOT NULL)");
        statement.close();
    }

    public void saveScore(Score score) throws SQLException {
        String query = "INSERT INTO SCORE(name, score) values(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, score.getName());
        preparedStatement.setLong(2, score.getScore());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public ScorePage getScores() throws SQLException {
        String query = "SELECT name, score FROM SCORE ORDER BY SCORE DESC LIMIT 10";
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(query);
        List<Score> scores = new LinkedList<Score>();
        while (set.next()) {
            scores.add(new Score(set.getString("name"), set.getLong("score")));
        }

        ScorePage sp = new ScorePage(0, 10, null, "/page/0", scores.size()==10?"/page/1":null, scores);
        return sp;


    }


}
