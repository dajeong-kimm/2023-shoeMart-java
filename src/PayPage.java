import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.*;

public class PayPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private User user;
    private HashMap<Integer, Product> prod = new HashMap<>();
    private List<CartItem> cartItems = new ArrayList();
    private int totalPrice;

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/shoe_martt";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "haeun";

    public PayPage(User user, List<CartItem> cartItems) {
        this.user = user;
        this.cartItems = cartItems;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            Statement stmt = conn.createStatement();
            Statement stmt2 = conn.createStatement();
            ResultSet rs;

            for (CartItem item : cartItems) {
                
            	//카트에 있는 Id
            	int pId = item.getPId();

            	//Product 목록에 있는 Id
                String sql = "SELECT * from products WHERE pId=" + pId;
                rs = stmt.executeQuery(sql);
                //다음으로
                rs.next();

                String pName = rs.getString(2);
                int price = rs.getInt(3);
                int cnt = rs.getInt(4);
                Blob img = rs.getBlob(5);
                
                Product product = new Product(pName, price, cnt, img);
                prod.put(pId, product);
                rs.close();
            }

            stmt2.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //결제 페이지
        setTitle("Payment");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel cartItemsPanel = new JPanel();
        cartItemsPanel.setLayout(new GridLayout(cartItems.size(), 3, 10, 10));

        for (CartItem item : cartItems) {
        	// prod HashMap에서 Id 호출
            Product product = prod.get(item.getPId());
            JLabel nameLabel = new JLabel(product.getpName());
            JLabel priceLabel = new JLabel(Integer.toString(product.getPrice()));
            JLabel cntLabel = new JLabel("수량: " + Integer.toString(item.getQuantity()));
            
            
            //Panel에 이름 가격 수량 보여주기
            cartItemsPanel.add(nameLabel);
            cartItemsPanel.add(priceLabel);
            cartItemsPanel.add(cntLabel);
        }

        JPanel totalPanel = new JPanel();
        totalPrice = calculateTotalPrice();
        JLabel totalPriceLabel = new JLabel("총 가격: " + Integer.toString(totalPrice));

        totalPanel.add(totalPriceLabel);
        
        // 결제버튼
        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton payButton = new JButton("결제하기");
        JButton backButton = new JButton("상품 페이지로 돌아가기");
        
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 페이지 종료
                MainPage mainPage = new MainPage(user, cartItems); // MainPage 클래스로 이동
                mainPage.setVisible(true);
            }
        });
        payButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String paypwdInput = JOptionPane.showInputDialog(PayPage.this, "결제 비밀번호를 입력하세요.");
                      if (paypwdInput.equals(user.getPaypwd())) {
                             user.reducePoint(user, totalPrice);
//                             pointValueLabel.setText(Integer.toString(user.getPoint()));
                             performPayment();
                         } 
                      else {
                             JOptionPane.showMessageDialog(PayPage.this, "결제 비밀번호가 일치하지 않습니다.");
                         }
            	
            	
            };
        });
        
        
        
        paymentPanel.add(payButton);
        paymentPanel.add(backButton);

        panel.add(cartItemsPanel, BorderLayout.CENTER);
        panel.add(totalPanel, BorderLayout.NORTH);
        panel.add(paymentPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    
    private void performPayment() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            Statement stmt = conn.createStatement();
            Statement stmt2 = conn.createStatement();

            for (CartItem item : cartItems) {
                int pId = item.getPId();
                int quantity = item.getQuantity();

                String sql = "UPDATE products SET cnt = cnt - " + quantity + " WHERE pId = " + pId;
                stmt.executeUpdate(sql);

                sql = "DELETE FROM cart WHERE user_id='" + user.getId() + "' and product_id=" + pId;
                stmt2.executeUpdate(sql);
            }

            stmt2.close();
            stmt.close();
            conn.close();

            JOptionPane.showMessageDialog(PayPage.this, "결제가 완료되었습니다.");

            // Clear cartItems list
            cartItems.clear();

            dispose();
            MainPage mainPage = new MainPage(user, cartItems); // MainPage 클래스로 이동
            mainPage.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int calculateTotalPrice() {
        int totalPrice = 0;
        for (CartItem item : cartItems) {
            Product product = prod.get(item.getPId());
            totalPrice += item.getQuantity() * product.getPrice();
        }
        return totalPrice;
    }
}
