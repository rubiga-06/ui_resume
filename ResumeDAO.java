import java.sql.*;

public class ResumeDAO {
    
    public static void createTableIfNotExists() {
    String sql = """
        CREATE TABLE IF NOT EXISTS resumes (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            username TEXT UNIQUE NOT NULL,
            fullName TEXT,
            email TEXT,
            phone TEXT,
            address TEXT,
            linkedIn TEXT,
            summary TEXT,
            FOREIGN KEY(username) REFERENCES users(username)
        )
    """;
    try (Connection conn = DatabaseManager.getConnection();
         Statement stmt = conn.createStatement()) {
        stmt.execute(sql);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public static void saveResume(String username, Resume resume) {
    String sql = """
        INSERT INTO resumes(username, fullName, email, phone, address, linkedIn, summary)
        VALUES(?, ?, ?, ?, ?, ?, ?)
        ON CONFLICT(username)
        DO UPDATE SET
            fullName=excluded.fullName,
            email=excluded.email,
            phone=excluded.phone,
            address=excluded.address,
            linkedIn=excluded.linkedIn,
            summary=excluded.summary
    """;
    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, username);
        pstmt.setString(2, resume.getFullName());
        pstmt.setString(3, resume.getEmail());
        pstmt.setString(4, resume.getPhone());
        pstmt.setString(5, resume.getAddress());
        pstmt.setString(6, resume.getLinkedIn());
        pstmt.setString(7, resume.getSummary());

        int affected = pstmt.executeUpdate();
        System.out.println("✅ Resume saved/updated for user: " + username + " (rows affected: " + affected + ")");

    } catch (SQLException e) {
        System.out.println("❌ Error saving resume for user: " + username);
        e.printStackTrace();
    }
}



    public static Resume loadResume(String username) {
        String sql = "SELECT * FROM resumes WHERE username=?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Resume r = new Resume();
                r.setFullName(rs.getString("fullName"));
                r.setEmail(rs.getString("email"));
                r.setPhone(rs.getString("phone"));
                r.setAddress(rs.getString("address"));
                r.setLinkedIn(rs.getString("linkedIn"));
                r.setSummary(rs.getString("summary"));
                return r;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

