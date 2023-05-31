import java.sql.*;

public class User {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/User";
    private static final String DB_USERNAME = "dajeong";
    private static final String DB_PASSWORD = "6545";

    private String name;
    private String id;
    private String pwd;
    private String paypwd;
    private int point;

    public User() {
    }

    public User(String name, String id, String pwd, String paypwd, int point) {
        this.name = name;
        this.id = id;
        this.pwd = pwd;
        this.paypwd = paypwd;
        this.point = point;
    }
    
    public static void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            // Create 'users' table if it doesn't exist
            String createTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                    "name VARCHAR(50) NOT NULL, " +
                    "id VARCHAR(50) PRIMARY KEY, " +
                    "pwd VARCHAR(50) NOT NULL, " +
                    "paypwd VARCHAR(50) NOT NULL, " +
                    "point INT NOT NULL)";
            statement.executeUpdate(createTableQuery);

            System.out.println("'users' table created successfully (if it didn't exist).");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    
    //포인트 충전후 저장 메서
    public void savePoint(User user, int amount) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("UPDATE users SET point = point + ? WHERE id = ?")) {

            // 이전 포인트 값 가져오기
            int currentPoint = user.getPoint();

            // 포인트 증가시키기
            int newPoint = currentPoint + amount;

            stmt.setInt(1, amount);
            stmt.setString(2, user.getId());

            stmt.executeUpdate(); // DB에 포인트 값을 업데이트

            // User 객체의 포인트 값도 업데이트
            setPoint(newPoint);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	   
	   
   

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
