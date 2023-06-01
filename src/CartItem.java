import java.sql.*;


public class CartItem {
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/shoe_mart";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "!!dltnals!!6280";
    
    private User user;
    private int pId;
    private int quantity;

    public CartItem(User user, int pId, int quantity) {
    	this.user = user;
        this.pId = pId;
        this.quantity = quantity;
    }
    
    public User getUser() {
    	return this.user;
    }
    
    public int getPId() {
    	return this.pId;
    }
    
    public int getQuantity() {
    	return this.quantity;
    }
    
    public void addCart() {
    	 Connection con;
         
         try {
         	Class.forName("com.mysql.jdbc.Driver");
     	
         	con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
         	
         	Statement stmt = con.createStatement();
         	ResultSet rs;
         	PreparedStatement psmt;
         	
         	String sql = "SELECT * from cart WHERE user_id='"+this.user.getId()+"' and product_id="+pId;
         	        	
         	rs = stmt.executeQuery(sql);
         	
         	if (rs.next()) {
         		int cnt = rs.getInt(3);
         		
         		cnt += quantity;
         		
         		sql = "UPDATE cart SET amount="+cnt+" WHERE user_id='"+this.user.getId()+"' and product_id="+pId;
         		stmt.executeUpdate(sql);
         	}
         	else {
         		psmt = con.prepareStatement("INSERT INTO cart VALUES(null, ?, ?, ?)");
         	
         		psmt.setString(1, this.user.getId());
         		psmt.setInt(2, this.pId);
         		psmt.setInt(3, this.quantity);
     	
         		psmt.executeUpdate();
         		psmt.close();
         	}
         	rs.close(); stmt.close(); con.close();
         } catch(Exception e) {
         	e.printStackTrace();
         }

    }
}
