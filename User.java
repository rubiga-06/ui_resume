import java.io.Serializable;

class User implements Serializable {
    private String username;
    private String password;
    private Resume resume;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.resume = null;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Resume getResume() { return resume; }
    public void setResume(Resume resume) { this.resume = resume; }
}