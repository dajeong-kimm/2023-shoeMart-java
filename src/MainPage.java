import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class MainPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private User user;
    private ProductList prods = new ProductList();

    public MainPage(User user, List<CartItem> cartItems) {
        this.user = user;

        setTitle("SHOE-MART");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,600);
        setResizable(false);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 로고 이미지 추가
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/SHOE-MART_logo.PNG"));
        JLabel logoLabel = new JLabel(logoIcon);
        panel.add(logoLabel, BorderLayout.NORTH);
      

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 2, 10, 10)); // 4행 2열의 그리드 레이아웃 사용
        
    	
    	Set keyset = prods.products.keySet();
    	Iterator<Integer> iter = keyset.iterator();
    	
        while(iter.hasNext()) {
        	Integer key = iter.next();
        	Product prod = prods.products.get(key);
        	JLabel productLabel = new JLabel(prod.getpName());
        	JLabel productPrice = new JLabel(Integer.toString(prod.getPrice()));
        	try {
        		InputStream is = prod.getImg().getBinaryStream(1, prod.getImg().length());
        		BufferedImage image = ImageIO.read(is);
        		ImageIcon productIcon = new ImageIcon(image);
        		Image img = productIcon.getImage();
        		Image changeImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        		ImageIcon changed = new ImageIcon(changeImg);
            	JLabel productImageLabel = new JLabel(changed);
            	JButton addCart = new JButton("추가");
            	addCart.setSize(50, 50);
            	addCart.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        addToCart(key.intValue());
                    }
                });
            	
            	centerPanel.add(productImageLabel);
            	centerPanel.add(productLabel);
            	centerPanel.add(productPrice);
            	centerPanel.add(addCart);
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        } 
        
      
        panel.add(centerPanel, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton myPageButton = new JButton("마이페이지");
        JButton cartButton = new JButton("장바구니");
        JButton checkoutButton = new JButton("결제하기");

        bottomPanel.add(myPageButton);
        bottomPanel.add(cartButton);
        bottomPanel.add(checkoutButton);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
        
     // 마이페이지 버튼 이벤트 리스너
     // 마이페이지 버튼 이벤트 리스너
        myPageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 페이지(MainPage) 종료
                MyPage myPage = new MyPage(user); // MyPage 클래스로 이동
            }
        });

        // 장바구니 버튼 이벤트 리스너
        cartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 페이지(MainPage) 종료
                CartPage cartPage = new CartPage(user); // CartPage 클래스로 이동
            }
        });
        

        // 결제하기 버튼 이벤트 리스너
        checkoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 페이지(MainPage) 종료
                PayPage payPage = new PayPage(user, CartItem.getCartItems()); // PayPage 클래스로 이동
            }
        });

        


    

}
 // 장바구니에 상품을 추가하는 메서드
    private void addToCart(int pId) {
        // 장바구니에 상품 추가하는 로직을 구현
        // 예시로 CartItem 클래스의 addToCart 메서드를 호출하는 것으로 가정합니다.
        CartItem item = new CartItem(this.user, pId, 1);
        item.addCart();
        JOptionPane.showMessageDialog(MainPage.this, "상품이 장바구니에 추가되었습니다.");
    }
}


