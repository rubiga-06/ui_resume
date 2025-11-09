import java.sql.*;

public class DatabaseManager {
    // ✅ Always use the same DB file in your project root directory
    private static final String DB_PATH = System.getProperty("user.dir") + "/resume_builder.db";
    private static final String DB_URL = "jdbc:sqlite:" + DB_PATH;

    static {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            // ✅ Ensure foreign key constraints are active
            stmt.execute("PRAGMA foreign_keys = ON;");

            // ✅ Create Users table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS users (
                    username TEXT PRIMARY KEY,
                    password TEXT NOT NULL
                )
            """);

            // ✅ Create Resumes table
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS resumes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT UNIQUE NOT NULL,
                    fullName TEXT,
                    email TEXT,
                    phone TEXT,
                    address TEXT,
                    linkedIn TEXT,
                    summary TEXT,
                    FOREIGN KEY(username) REFERENCES users(username) ON DELETE CASCADE
                )
            """);

            System.out.println("✅ Database initialized successfully!");
            System.out.println("📁 Database file: " + DB_PATH);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Method to return a connection (auto-commit true)
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        conn.setAutoCommit(true); // ensure changes save immediately
        return conn;
    }
}
