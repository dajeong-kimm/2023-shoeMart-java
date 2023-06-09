import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

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
        setSize(900, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // 로고 이미지 추가
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/SHOE-MART_logo.PNG"));
        JLabel logoLabel = new JLabel(logoIcon);
        panel.add(logoLabel, BorderLayout.NORTH);

        JPanel topPanel = new JPanel();
        topPanel.add(logoLabel);
        panel.add(topPanel, BorderLayout.NORTH);

        // 사용자 정보 패널
        JPanel userInfoPanelWrapper = new JPanel();
        userInfoPanelWrapper.setLayout(new BorderLayout());
        userInfoPanelWrapper.setBorder(new EmptyBorder(0, 50, 0, 50));

        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new GridLayout(3, 2, 10, 10));
        userInfoPanel.setBorder(new CompoundBorder(
                new LineBorder(Color.BLACK, 2), // 테두리 선 스타일 및 색상 설정
                new EmptyBorder(10, 10, 10, 10) // 테두리 안쪽 여백 설정
        ));

        Font userInfoFont = new Font(Font.DIALOG, Font.PLAIN, 30);

        JLabel nameLabel = new JLabel("이름:");
        JLabel nameValueLabel = new JLabel(user.getName());
        nameLabel.setFont(userInfoFont);
        nameValueLabel.setFont(userInfoFont);
        userInfoPanel.add(nameLabel);
        userInfoPanel.add(nameValueLabel);

        JLabel idLabel = new JLabel("아이디:");
        JLabel idValueLabel = new JLabel(user.getId());
        idLabel.setFont(userInfoFont);
        idValueLabel.setFont(userInfoFont);
        userInfoPanel.add(idLabel);
        userInfoPanel.add(idValueLabel);

        JLabel pointLabel = new JLabel("포인트:");
        JLabel pointValueLabel = new JLabel(Integer.toString(user.getPoint()));
        pointLabel.setFont(userInfoFont);
        pointValueLabel.setFont(userInfoFont);
        userInfoPanel.add(pointLabel);
        userInfoPanel.add(pointValueLabel);

        userInfoPanelWrapper.add(userInfoPanel, BorderLayout.CENTER);
        panel.add(userInfoPanelWrapper, BorderLayout.CENTER);

        // 버튼 추가
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton backButton = new JButton("상품 페이지로 돌아가기");
        JButton chargeButton = new JButton("포인트 충전하기");
        JButton logoutButton = new JButton("로그아웃");
        JButton contactAdminButton = new JButton("관리자에게 문의하기");
        
     // 버튼 폰트 설정
        Font buttonFont = new Font(Font.DIALOG, Font.PLAIN, 20);
        backButton.setFont(buttonFont);
        chargeButton.setFont(buttonFont);
        logoutButton.setFont(buttonFont);
        contactAdminButton.setFont(buttonFont);

        bottomPanel.add(backButton);
        bottomPanel.add(chargeButton);
        bottomPanel.add(logoutButton);
        bottomPanel.add(contactAdminButton);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 페이지(MainPage) 종료
                MainPage mainPage = new MainPage(user, cartItems); // MainPage 클래스로 이동
                mainPage.setVisible(true);
            }
        });

        chargeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 포인트 충전 페이지 열기
                String input = JOptionPane.showInputDialog(MyPage.this, "충전할 포인트 금액을 입력하세요.");
                try {
                    int amount = Integer.parseInt(input);
                    if (amount > 0) {
                        String paypwdInput = JOptionPane.showInputDialog(MyPage.this, "결제 비밀번호를 입력하세요.");
                        if (paypwdInput.equals(user.getPaypwd())) {
                            user.savePoint(user, amount);
                            pointValueLabel.setText(Integer.toString(user.getPoint()));
                            JOptionPane.showMessageDialog(MyPage.this, amount + " 포인트가 충전되었습니다.");
                        } else {
                            JOptionPane.showMessageDialog(MyPage.this, "결제 비밀번호가 일치하지 않습니다.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(MyPage.this, "잘못된 입력입니다. 양수의 정수를 입력하세요.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MyPage.this, "잘못된 입력입니다. 양수의 정수를 입력하세요.");
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 페이지(MyPage) 종료
                LoginRegisterPage loginRegisterPage = new LoginRegisterPage(); // 로그인 페이지로 이동
                loginRegisterPage.setVisible(true);
            }
        });
        
     // 관리자와 채팅하기 버튼 이벤트 리스
        contactAdminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ClientGUI clientGUI = new ClientGUI();
                        clientGUI.setVisible(true);
                    }
                });

            }
        });

        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }
}
