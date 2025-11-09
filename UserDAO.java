import java.sql.*;

public class UserDAO {
    public static boolean addUser(String username, String password) {
        String sql = "INSERT INTO users(username, password) VALUES(?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static void createTableIfNotExists() {
    String sql = "CREATE TABLE IF NOT EXISTS users (" +
                 "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                 "username TEXT UNIQUE NOT NULL, " +
                 "password TEXT NOT NULL)";
    try (Connection conn = DatabaseManager.getConnection();
         Statement stmt = conn.createStatement()) {
        stmt.execute(sql);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public static boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
