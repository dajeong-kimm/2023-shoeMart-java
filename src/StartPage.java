import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPage extends JFrame {
    private static final long serialVersionUID = 1L;

    public StartPage() {
        setTitle("Start Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // 로고 이미지 추가
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/SHOE-MART_logo.PNG"));
        JLabel logoLabel = new JLabel(logoIcon);
        panel.add(logoLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton adminButton = new JButton("Administrator");
        JButton userButton = new JButton("Customer");

        centerPanel.add(adminButton);
        centerPanel.add(userButton);
        
     // 버튼 폰트 설정
        Font buttonFont = new Font(Font.DIALOG, Font.PLAIN, 30);
        adminButton.setFont(buttonFont);
        userButton.setFont(buttonFont);

        panel.add(centerPanel, BorderLayout.CENTER);

        adminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 페이지(StartPage) 종료
                AdministratorPage adminPage = new AdministratorPage(); // 관리자 페이지로 이동
                adminPage.setVisible(true);
            }
        });

        userButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 페이지(StartPage) 종료
                LoginRegisterPage loginRegisterPage = new LoginRegisterPage(); // 구매자 페이지로 이동
                loginRegisterPage.setVisible(true);
            }
        });

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        // StartPage 실행
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new StartPage();
            }
        });
    }
}
