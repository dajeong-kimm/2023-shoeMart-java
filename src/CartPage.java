import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.*;

public class CartPage extends JFrame {
	private static final long serialVersionUID = 1L;
	private User user;
	private HashMap<Integer, Product> prod = new HashMap<>();
	private List<CartItem> cartItems = new ArrayList(); // Change the type to List<CartItem>
	private JLabel totalPriceLabel;

	private static final String DB_URL = "jdbc:mysql://localhost:3306/shoe_mart";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "!!dltnals!!6280";

	public CartPage(User user) { // Update the constructor
		this.user = user;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			Statement stmt = conn.createStatement();
			Statement stmt2 = conn.createStatement();
			ResultSet rs;

			String sql = "SELECT * from cart WHERE user_id='" + this.user.getId() + "'";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int pId = rs.getInt(3);
				int quantity = rs.getInt(4);
				CartItem item = new CartItem(user, pId, quantity);

				cartItems.add(item);

				sql = "SELECT * from products WHERE pId=" + pId;
				ResultSet prodRs = stmt2.executeQuery(sql);
				prodRs.next();

				String pName = prodRs.getString(2);
				int price = prodRs.getInt(3);
				int cnt = prodRs.getInt(4);
				Blob img = prodRs.getBlob(5);
				Product product = new Product(pName, price, cnt, img);
				prod.put(pId, product);
				prodRs.close();
			}

			rs.close();
			stmt2.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 장바구니 상품 목록
		setTitle("Cart");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 600);
		setResizable(false);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		
		if (cartItems.isEmpty()) {
			JPanel printPanel = new JPanel();
			JLabel printLabel = new JLabel("장바구니 목록이 비었습니다.");
			
			printPanel.add(printLabel);
			panel.add(printPanel, BorderLayout.CENTER);
		}

		else {

			JPanel cartItemsPanel = new JPanel();
			JScrollPane scrollPane = new JScrollPane(cartItemsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			cartItemsPanel.setLayout(new GridLayout(cartItems.size(), 4, 30, 30));

			int num = cartItems.size();

			Insets inset = new Insets((int) 240 / (num * num), 40, (int) 240 / (num * num), 40);

			for (CartItem item : cartItems) { // Update the loop variable type
				Product product = prod.get(item.getPId());
				JLabel nameLabel = new JLabel(product.getpName()); // Access the product from the CartItem
				JLabel priceLabel = new JLabel(Integer.toString(product.getPrice())); // Use the getPrice() method from
																						// CartItem
				JLabel cntLabel = new JLabel("수량: " + Integer.toString(item.getQuantity()));

				nameLabel.setBorder(new EmptyBorder(5, 15, 5, 15));
				nameLabel.setHorizontalAlignment(JLabel.CENTER);
				priceLabel.setBorder(new EmptyBorder(5, 30, 5, 30));
				priceLabel.setHorizontalAlignment(JLabel.CENTER);
				cntLabel.setBorder(new EmptyBorder(5, 30, 5, 30));
				cntLabel.setHorizontalAlignment(JLabel.CENTER);

				JPanel buttonPanel = new JPanel();
				buttonPanel.setLayout(new GridLayout(1, 1));
				buttonPanel.setBorder(new EmptyBorder(inset));
				JButton cancelButton = new JButton("취소하기");
				buttonPanel.add(cancelButton);

				cartItemsPanel.add(nameLabel);
				cartItemsPanel.add(priceLabel);
				cartItemsPanel.add(cntLabel);
				cartItemsPanel.add(buttonPanel);

				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						removeFromCart(item); // Pass the product to removeFromCart()
						setVisible(false);
						CartPage cartpage = new CartPage(user);
					}
				});
			}

			JPanel totalPanel = new JPanel();
			totalPriceLabel = new JLabel("총 가격: " + Integer.toString(calculateTotalPrice()));

			totalPanel.add(totalPriceLabel);

			panel.add(scrollPane, BorderLayout.CENTER);
			panel.add(totalPanel, BorderLayout.NORTH);
			
		}
		
		JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton backButton = new JButton("상품 페이지");
        JButton mypageButton = new JButton("마이페이지");
        JButton payButton = new JButton("결제하기");

        bottomPanel.add(backButton);
        bottomPanel.add(mypageButton);
        bottomPanel.add(payButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 페이지(MainPage) 종료
                MainPage mainPage = new MainPage(user, cartItems); // MyPage 클래스로 이동
            }
        });
        
        mypageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 페이지(MainPage) 종료
                MyPage myPage = new MyPage(user); // MyPage 클래스로 이동
            }
        });
        
        payButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 페이지(MainPage) 종료
                PayPage payPage = new PayPage(user, cartItems); // MyPage 클래스로 이동
            }
        });
        
		panel.add(bottomPanel, BorderLayout.SOUTH);
        
		add(panel);
		setVisible(true);

	}

	// 장바구니에서 상품 제외
	private void removeFromCart(CartItem item) {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM cart WHERE user_id='" + this.user.getId() + "' and product_id=" + item.getPId());

			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		cartItems.remove(item);
		prod.remove(item.getPId());

		totalPriceLabel.setText("총 가격: " + calculateTotalPrice());
		JOptionPane.showMessageDialog(CartPage.this, "상품이 장바구니에서 제외되었습니다.");
	}

	// 총 가격 계산
	private int calculateTotalPrice() {
		int totalPrice = 0;
		for (CartItem item : cartItems) {// Update the loop variable type
			Product product = prod.get(item.getPId());
			totalPrice += item.getQuantity() * product.getPrice(); // Use the getPrice() method from CartItem
		}
		return totalPrice;
	}
}
