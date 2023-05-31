import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class MainPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private User user;
    private List<CartItem> cartItems;
    
    Product product1 = new Product("썬 여성 샌들 젤리슈즈", 17900, 10, "/썬여성샌들젤리슈즈.PNG");
    Product product2 = new Product("코트 버로우 로우2", 59000, 10, "/코트버로우로우2.PNG");
    Product product3 = new Product("아딜렛 22", 46000, 10, "/아딜렛22.PNG");
    Product product4 = new Product("척테일러 올스타 클래식", 55000, 10, "/척테일러올스타클래식.PNG");
    Product product5 = new Product("클래식 클로그", 49000, 10, "/클래식클로그.PNG");
    Product product6 = new Product("메이린 여성 홀스핏 로퍼", 39900, 10, "/메이린여성홀스빗로퍼.PNG");
    Product product7 = new Product("피카츄 포켓 장화", 9900, 10, "/피카츄포켓장화.PNG");
    Product product8 = new Product("헬로카봇 타운 장화", 8900, 10, "/헬로카봇타운장화.PNG");

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
        

        // 상품 1
        JLabel product1Label = new JLabel(product1.getpName());
        JLabel product1Price = new JLabel(Integer.toString(product1.getPrice()));
        ImageIcon product1Icon = new ImageIcon(product1.getImg());
        JLabel product1ImageLabel = new JLabel(product1Icon);

        centerPanel.add(product1ImageLabel);
        centerPanel.add(product1Label);
        centerPanel.add(product1Price);

            
        // 상품 2
        JLabel product2Label = new JLabel(product2.getpName());
        JLabel product2Price = new JLabel(Integer.toString(product2.getPrice()));
        ImageIcon product2Icon = new ImageIcon(product2.getImg());
        JLabel product2ImageLabel = new JLabel(product2Icon);

        centerPanel.add(product2ImageLabel);
        centerPanel.add(product2Label);
        centerPanel.add(product2Price);
        
     // 상품 3
        JLabel product3Label = new JLabel(product3.getpName());
        JLabel product3Price = new JLabel(Integer.toString(product3.getPrice()));
        ImageIcon product3Icon = new ImageIcon(product3.getImg());
        JLabel product3ImageLabel = new JLabel(product3Icon);

        centerPanel.add(product3ImageLabel);
        centerPanel.add(product3Label);
        centerPanel.add(product3Price);
        
     // 상품 4
        JLabel product4Label = new JLabel(product4.getpName());
        JLabel product4Price = new JLabel(Integer.toString(product4.getPrice()));
        ImageIcon product4Icon = new ImageIcon(product4.getImg());
        JLabel product4ImageLabel = new JLabel(product4Icon);

        centerPanel.add(product4ImageLabel);
        centerPanel.add(product4Label);
        centerPanel.add(product4Price);
        
     // 상품 5
        JLabel product5Label = new JLabel(product5.getpName());
        JLabel product5Price = new JLabel(Integer.toString(product5.getPrice()));
        ImageIcon product5Icon = new ImageIcon(product5.getImg());
        JLabel product5ImageLabel = new JLabel(product5Icon);

        centerPanel.add(product5ImageLabel);
        centerPanel.add(product5Label);
        centerPanel.add(product5Price);
        
     // 상품 6
        JLabel product6Label = new JLabel(product6.getpName());
        JLabel product6Price = new JLabel(Integer.toString(product6.getPrice()));
        ImageIcon product6Icon = new ImageIcon(product6.getImg());
        JLabel product6ImageLabel = new JLabel(product6Icon);

        centerPanel.add(product6ImageLabel);
        centerPanel.add(product6Label);
        centerPanel.add(product6Price);
        
     // 상품 7
        JLabel product7Label = new JLabel(product7.getpName());
        JLabel product7Price = new JLabel(Integer.toString(product7.getPrice()));
        ImageIcon product7Icon = new ImageIcon(product7.getImg());
        JLabel product7ImageLabel = new JLabel(product7Icon);

        centerPanel.add(product7ImageLabel);
        centerPanel.add(product7Label);
        centerPanel.add(product7Price);
        
     // 상품 8
        JLabel product8Label = new JLabel(product8.getpName());
        JLabel product8Price = new JLabel(Integer.toString(product8.getPrice()));
        ImageIcon product8Icon = new ImageIcon(product8.getImg());
        JLabel product8ImageLabel = new JLabel(product8Icon);

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


