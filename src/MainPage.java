import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class MainPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private User user;
    private List<CartItem> cartItems;
    
    Product product1 = new Product("썬 여성 샌들 젤리슈즈", 17900, 10, "/sunwomen.PNG");
    Product product2 = new Product("코트 버로우 로우2", 59000, 10, "/low2.PNG");
    Product product3 = new Product("아딜렛 22", 46000, 10, "/adelet.PNG");
    Product product4 = new Product("척테일러 올스타 클래식", 55000, 10, "/converse.PNG");
    Product product5 = new Product("클래식 클로그", 49000, 10, "/classic.PNG");
    Product product6 = new Product("메이린 여성 홀스핏 로퍼", 39900, 10, "/marin.PNG");
    Product product7 = new Product("피카츄 포켓 장화", 9900, 10, "/pica.PNG");
    Product product8 = new Product("헬로카봇 타운 장화", 8900, 10, "/hellocabot.PNG");

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
        centerPanel.setLayout(new GridLayout(4, 2, 5, 5)); // 4행 2열의 그리드 레이아웃 사용
        

        // 상품 1
        JLabel product1Label = new JLabel(product1.getpName());
        JLabel product1Price = new JLabel(Integer.toString(product1.getPrice()));
        ImageIcon product1Icon = new ImageIcon(getClass().getResource(product1.getImg()));
        Image scaledProduct1Image = product1Icon.getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH);
        ImageIcon scaledProduct1Icon = new ImageIcon(scaledProduct1Image);
        JLabel product1ImageLabel = new JLabel(scaledProduct1Icon);

        centerPanel.add(product1ImageLabel);
        centerPanel.add(product1Label);
        centerPanel.add(product1Price);

            
        // 상품 2
        JLabel product2Label = new JLabel(product2.getpName());
        JLabel product2Price = new JLabel(Integer.toString(product2.getPrice()));
        ImageIcon product2Icon = new ImageIcon(getClass().getResource(product2.getImg()));
        Image scaledProduct2Image = product2Icon.getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH);
        ImageIcon scaledProduct2Icon = new ImageIcon(scaledProduct2Image);
        JLabel product2ImageLabel = new JLabel(scaledProduct2Icon);

        centerPanel.add(product2ImageLabel);
        centerPanel.add(product2Label);
        centerPanel.add(product2Price);
        
     // 상품 3
        JLabel product3Label = new JLabel(product3.getpName());
        JLabel product3Price = new JLabel(Integer.toString(product3.getPrice()));
        ImageIcon product3Icon = new ImageIcon(getClass().getResource(product3.getImg()));
        Image scaledProduct3Image = product3Icon.getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH);
        ImageIcon scaledProduct3Icon = new ImageIcon(scaledProduct3Image);
        JLabel product3ImageLabel = new JLabel(scaledProduct3Icon);

        centerPanel.add(product3ImageLabel);
        centerPanel.add(product3Label);
        centerPanel.add(product3Price);
        
     // 상품 4
        JLabel product4Label = new JLabel(product4.getpName());
        JLabel product4Price = new JLabel(Integer.toString(product4.getPrice()));
        ImageIcon product4Icon = new ImageIcon(getClass().getResource(product4.getImg()));
        Image scaledProduct4Image = product4Icon.getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH);
        ImageIcon scaledProduct4Icon = new ImageIcon(scaledProduct4Image);
        JLabel product4ImageLabel = new JLabel(scaledProduct4Icon);

        centerPanel.add(product4ImageLabel);
        centerPanel.add(product4Label);
        centerPanel.add(product4Price);
        
     // 상품 5
        JLabel product5Label = new JLabel(product5.getpName());
        JLabel product5Price = new JLabel(Integer.toString(product5.getPrice()));
        ImageIcon product5Icon = new ImageIcon(getClass().getResource(product5.getImg()));
        Image scaledProduct5Image = product5Icon.getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH);
        ImageIcon scaledProduct5Icon = new ImageIcon(scaledProduct5Image);
        JLabel product5ImageLabel = new JLabel(scaledProduct5Icon);

        centerPanel.add(product5ImageLabel);
        centerPanel.add(product5Label);
        centerPanel.add(product5Price);
        
     // 상품 6
        JLabel product6Label = new JLabel(product6.getpName());
        JLabel product6Price = new JLabel(Integer.toString(product6.getPrice()));
        ImageIcon product6Icon = new ImageIcon(getClass().getResource(product6.getImg()));
        Image scaledProduct6Image = product6Icon.getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH);
        ImageIcon scaledProduct6Icon = new ImageIcon(scaledProduct6Image);
        JLabel product6ImageLabel = new JLabel(scaledProduct6Icon);

        centerPanel.add(product6ImageLabel);
        centerPanel.add(product6Label);
        centerPanel.add(product6Price);
        
     // 상품 7
        JLabel product7Label = new JLabel(product7.getpName());
        JLabel product7Price = new JLabel(Integer.toString(product7.getPrice()));
        ImageIcon product7Icon = new ImageIcon(getClass().getResource(product7.getImg()));
        Image scaledProduct7Image = product7Icon.getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH);
        ImageIcon scaledProduct7Icon = new ImageIcon(scaledProduct7Image);
        JLabel product7ImageLabel = new JLabel(scaledProduct7Icon);

        centerPanel.add(product7ImageLabel);
        centerPanel.add(product7Label);
        centerPanel.add(product7Price);
        
     // 상품 8
        JLabel product8Label = new JLabel(product8.getpName());
        JLabel product8Price = new JLabel(Integer.toString(product8.getPrice()));
        ImageIcon product8Icon = new ImageIcon(getClass().getResource(product8.getImg()));
        Image scaledProduct8Image = product8Icon.getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH);
        ImageIcon scaledProduct8Icon = new ImageIcon(scaledProduct8Image);
        JLabel product8ImageLabel = new JLabel(scaledProduct8Icon);

        centerPanel.add(product8ImageLabel);
        centerPanel.add(product8Label);
        centerPanel.add(product8Price);
        
        
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
                CartPage cartPage = new CartPage(user, CartItem.getCartItems()); // CartPage 클래스로 이동
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
    private void addToCart(Product product) {
        // 장바구니에 상품 추가하는 로직을 구현
        // 예시로 CartItem 클래스의 addToCart 메서드를 호출하는 것으로 가정합니다.
        CartItem.addToCart(product);
        JOptionPane.showMessageDialog(MainPage.this, "상품이 장바구니에 추가되었습니다.");
    }
}


