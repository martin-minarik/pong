package pong;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ScoreDAO {
    public ScoreDAO() {

        try (Connection connection = getConnection()) {
            initTable(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<ScoreListViewItem> load() {
        List<ScoreListViewItem> result = new LinkedList<>();
        try (Connection connection = getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT score, name FROM score");
            while (rs.next()) {
                int value = rs.getInt(1);
                String name = rs.getString(2);
                result.add(new ScoreListViewItem(value, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void save(List<ScoreListViewItem> highScores) {
        try (Connection connection = getConnection()) {
            getConnection().createStatement().execute("DELETE FROM score");
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO score (score, name) VALUES (?, ?)");
            for (ScoreListViewItem score : highScores) {
                pstm.setInt(1, score.getScore());
                pstm.setString(2, score.getName());
                pstm.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void initTable(Connection connection) {
        Statement stmt;
        try {
            stmt = connection.createStatement();
            stmt.execute("CREATE TABLE score ("
                    + "   Id INT NOT NULL GENERATED ALWAYS AS IDENTITY,"
                    + "   score INT NOT NULL,"
                    + "   name VARCHAR(255),"
                    + "   PRIMARY KEY (Id))");
        } catch (SQLException e) {
            if (e.getSQLState().equals("X0Y32")) {
                return;
            }
            e.printStackTrace();
        }

    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:derby:scoreDB;create=true");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
