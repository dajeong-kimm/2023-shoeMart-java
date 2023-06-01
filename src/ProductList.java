import java.sql.*;
import java.util.HashMap;


public class ProductList{
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/shoe_mart";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "!!dltnals!!6280";

    private int pId;
    private String pName;
    private int price;
    private int cnt;
    private Blob img;
    
    HashMap<Integer, Product> products = new HashMap<Integer, Product>();
    
    ProductList() {
    	Connection con;
        ResultSet rs;
        Statement stmt;
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    		
    		con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    		
    		stmt = con.createStatement();
    		
    		String sql = "SELECT * FROM products";
    		rs = stmt.executeQuery(sql);
    		
    		while(rs.next()) {
    			pId = rs.getInt(1);
    			pName = rs.getString(2);
    			price = rs.getInt(3);
    			cnt = rs.getInt(4);
    			img = rs.getBlob(5);
    			
    			Product product = new Product(pName, price, cnt, img);
    			
    			products.put(pId,  product);
    		}
    		
    		rs.close();
    		stmt.close();
    		con.close();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}
