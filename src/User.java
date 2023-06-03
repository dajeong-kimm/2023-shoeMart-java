import java.sql.*;
import java.io.File;
import java.io.FileInputStream;


public class User {
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/shoe_martt";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "haeun";
    
    // 이미지 파일이 위치한 절대경로
    private static final String IMG_PATH = "src/";


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
        
        Connection conn;
    	Statement stmt;
    	String DB_NAME = "shoe_mart"; // 사용하시는 데이터베이스명으로 변경해주세요
    	PreparedStatement psmt;
    	
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    		conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    		
    		stmt = conn.createStatement();
    		
    		String checkQuery = "SELECT count(*) FROM information_schema.tables WHERE table_schema = '"+DB_NAME+"' AND table_name='products'";
			Statement stmt2 = conn.createStatement();
			ResultSet rs = stmt2.executeQuery(checkQuery);
			rs.next();

			if (rs.getInt(1) == 0) {

				String createQuery = "CREATE TABLE products (" + "pId int primary key auto_increment, "
						+ "pName varchar(30) not null unique, " + "price int not null, " + "cnt int default 0, "
						+ "img longblob)";

				stmt.executeUpdate(createQuery);

				Product[] products = new Product[8];
				String[] file = new String[8];

				products[0] = new Product("썬 여성 샌들 젤리슈즈", 17900, 10, null);
				products[1] = new Product("코트 버로우 로우2", 59000, 10, null);
				products[2] = new Product("아딜렛22", 46000, 10, null);
				products[3] = new Product("척테일러 올스타 클래식", 55000, 10, null);
				products[4] = new Product("클래식 클로그", 49000, 10, null);
				products[5] = new Product("메이린 여성 홀스빗 로퍼", 39900, 10, null);
				products[6] = new Product("피카츄 포켓 장화", 9900, 10, null);
				products[7] = new Product("헬로카봇 타운 장화", 8900, 10, null);
				
				file[0] = "sunwomen.png";
				file[1] = "low2.png";
				file[2] = "adelet.png";
				file[3] = "converse.png";
				file[4] = "classic.png";
				file[5] = "marin.png";
				file[6] = "pica.png";
				file[7] = "hellocabot.png";
				
				for (int i=0; i<8; i++) {
					String psmtStr = "INSERT INTO products values (null, ?, ?, ?, ?)";
					
					File f = new File(IMG_PATH+file[i]);
					FileInputStream fis = new FileInputStream(f);
										
					psmt = conn.prepareStatement(psmtStr);
					
					psmt.setString(1, products[i].getpName());
					psmt.setInt(2, products[i].getPrice());
					psmt.setInt(3, products[i].getCnt());
					psmt.setBinaryStream(4, fis,(int)f.length());
									
					psmt.executeUpdate();
					fis.close();
					psmt.close();
				}
			}
			rs.close(); stmt2.close(); stmt.close(); conn.close();
		} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			
			stmt = conn.createStatement();
			
			String createQuery = "CREATE TABLE IF NOT EXISTS cart ("+
					"cart_id int primary key auto_increment, "+
					"user_id varchar(50), "+
					"product_id int, "+
					"amount int default 0, "+
					"foreign key(user_id) references users(id), "+
					"foreign key(product_id) references products(pId))";
			
			System.out.println(createQuery);
			
			stmt.executeUpdate(createQuery);
			
			stmt.close(); conn.close();
		} catch(Exception e) {
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
    
    // 포인트 삭감 메소드 
    public void reducePoint(User user, int amount) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("UPDATE users SET point = point + ? WHERE id = ?")) {

            // 이전 포인트 값 가져오기
            int currentPoint = user.getPoint();

            // 포인트 감소시키기
            int newPoint = currentPoint - amount;

            stmt.setInt(1, amount);
            stmt.setString(2, user.getId());

            stmt.executeUpdate(); // DB에 포인트 값을 업데이트

            // User 객체의 포인트 값도 업데이트
            setPoint(newPoint);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isIdAvailable(String id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE id = ?")) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            // ResultSet에 결과가 있다면 중복된 아이디이므로 false를 반환
            return !rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 예외가 발생하면 중복 여부를 확정할 수 없으므로 기본적으로 true를 반환
        return true;
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