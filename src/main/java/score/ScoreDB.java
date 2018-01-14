package score;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ScoreDB {

    private Connection connection;

    public ScoreDB () {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:test.sqlite");
        } catch ( Exception e ) {
            System.exit(0);
        }
    }

    public void resetDB () throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeQuery(
                           "CREATE TABLE SCORE (" +
                                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "score INT NOT NULL," +
                                "name NVARCHAR(50) NOT NULL);" +
                                "CREATE TABLE DECKS (" +
                                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "cards VARCHAR(3459) NOT NULL)");
        statement.close();
    }

    public void saveScore(Score score) {
        String query = "INSERT INTO SCORE(name, score) values(?,?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, score.getName());
            preparedStatement.setLong(2, score.getScore());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ScorePage getScoresPage(int page) {
        String query = "SELECT name, score FROM SCORE ORDER BY SCORE DESC LIMIT 10 OFFSET " + 10*(page+1);

        ResultSet set = null;
        try {
            Statement statement = connection.createStatement();
            set = statement.executeQuery(query);
            List<Score> scores = new LinkedList<Score>();
            while (set.next()) {
                scores.add(new Score(set.getString("name"), set.getLong("score")));
            }
            ScorePage sp = new ScorePage(page, 10, null, "/page/"+page, scores.size()==10?"/page/"+(page+1):"/page/"+page, scores);
            return sp;
        } catch (SQLException e) {
            e.printStackTrace();
        }

       return null;
    }

    public void saveDeck(String deck){
        String query = "INSERT INTO DECKS(cards) values(?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, deck);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getRandomDeck() {
        String query = "SELECT COUNT(*) as amount FROM DECKS ";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);
            int decks = 0;
            while (set.next()) {
                decks = set.getInt("amount");
            }
            if(decks == 0) {
                return "";
            } else {
                int randomId = (new Random()).nextInt(decks) + 1;
                query = "SELECT id, cards FROM DECKS where id = " + randomId;
                set = statement.executeQuery(query);
                String cards = "";
                while(set.next()) {
                    cards = set.getString("cards");
                }
                return cards;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public ScorePage getScores() {
        return getScoresPage(0);
    }


}
