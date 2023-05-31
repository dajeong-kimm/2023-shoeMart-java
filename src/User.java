import java.sql.*;

public class User {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/User";
    private static final String DB_USERNAME = "dajeong";
    private static final String DB_PASSWORD = "6545";

    private String name;
    private String id;
    private String pwd;
    private String paypwd;
    private int point = 0;

    public User() {
    }

    public User(String name, String id, String pwd, String paypwd, int point) {
        this.name = name;
        this.id = id;
        this.pwd = pwd;
        this.paypwd = paypwd;
        this.point = point;
    }

    // 사용자 정보 조회 메서드
    public static User getUser(String id) {
        User user = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?")) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String pwd = rs.getString("pwd");
                String paypwd = rs.getString("paypwd");
                int point = rs.getInt("point");

                user = new User(name, id, pwd, paypwd, point);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // 사용자 정보 저장 메서드
    public void saveUser() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (name, id, pwd, paypwd, point) VALUES (?, ?, ?, ?, ?)")) {

            stmt.setString(1, name);
            stmt.setString(2, id);
            stmt.setString(3, pwd);
            stmt.setString(4, paypwd);
            stmt.setInt(5, point);
            

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getter와 Setter 메서드는 필요에 따라 추가하십시오.

    // 예시로 몇 가지 Getter 메서드를 추가한 것입니다.
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPwd() {
        return pwd;
    }

    public String getPaypwd() {
        return paypwd;
    }

    public int getPoint() {
        return point;
    }

    // 예시로 몇 가지 Setter 메서드를 추가한 것입니다.
    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setPaypwd(String paypwd) {
        this.paypwd = paypwd;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
