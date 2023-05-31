import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MyPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private User user;
    private List<CartItem> cartItems;

    public MyPage(User user) {
        this.user = user;

        setTitle("My Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,600);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // 마진 추가

     // 로고 이미지 추가
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/SHOE-MART_logo.PNG"));
        JLabel logoLabel = new JLabel(logoIcon);
        panel.add(logoLabel, BorderLayout.NORTH);
      

        JPanel topPanel = new JPanel();
        topPanel.add(logoLabel);
        panel.add(topPanel, BorderLayout.NORTH);

        // 사용자 정보
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel nameLabel = new JLabel("이름:");
        JLabel nameValueLabel = new JLabel(user.getName());
        userInfoPanel.add(nameLabel);
        userInfoPanel.add(nameValueLabel);

        JLabel idLabel = new JLabel("아이디:");
        JLabel idValueLabel = new JLabel(user.getId());
        userInfoPanel.add(idLabel);
        userInfoPanel.add(idValueLabel);

        JLabel pointLabel = new JLabel("포인트:");
        JLabel pointValueLabel = new JLabel(Integer.toString(user.getPoint()));
        userInfoPanel.add(pointLabel);
        userInfoPanel.add(pointValueLabel);

        panel.add(userInfoPanel, BorderLayout.CENTER);

        // 버튼 추가
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton backButton = new JButton("상품 페이지로 돌아가기");
        JButton chargeButton = new JButton("포인트 충전하기");

        bottomPanel.add(backButton);
        bottomPanel.add(chargeButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 페이지(MainPage) 종료
                MainPage mainPage = new MainPage(user, cartItems); // MyPage 클래스로 이동
            }
        });
        
        chargeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 포인트 충전 페이지 열기
                String input = JOptionPane.showInputDialog(MyPage.this, "충전할 포인트 금액을 입력하세요.");
                try {
                    int amount = Integer.parseInt(input);
                    if (amount > 0) {
                        user.setPoint(user.getPoint() + amount);
                        pointValueLabel.setText(Integer.toString(user.getPoint()));
                        JOptionPane.showMessageDialog(MyPage.this, amount + " 포인트가 충전되었습니다.");
                    } else {
                        JOptionPane.showMessageDialog(MyPage.this, "잘못된 입력입니다. 양수의 정수를 입력하세요.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MyPage.this, "잘못된 입력입니다. 양수의 정수를 입력하세요.");
                }
            }
        });

        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }
}
