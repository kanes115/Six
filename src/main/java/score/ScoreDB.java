package score;

import java.io.IOException;
import java.sql.*;

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

    public void saveScore (Score score) throws SQLException {
        String query = "INSERT INTO SCORE(name, score) values(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, score.getName());
        preparedStatement.setLong(2, score.getScore());
        preparedStatement.executeUpdate();
        preparedStatement.close();

    }


}
