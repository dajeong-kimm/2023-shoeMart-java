import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class AdministratorPage extends JFrame {
    private static final long serialVersionUID = 1L;

    public AdministratorPage() {
        setTitle("Administrator Page");
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
        
       
        JButton chatButton = new JButton("구매자에게 문의 답변");
        JButton addProductButton = new JButton("상품 관리");
        JButton backButton = new JButton("이전으로 돌아가기");


        centerPanel.add(chatButton);
        centerPanel.add(addProductButton);
        centerPanel.add(backButton);
        
     // 버튼 폰트 설정
        Font buttonFont = new Font(Font.DIALOG, Font.PLAIN, 15);
        chatButton.setFont(buttonFont);
        addProductButton.setFont(buttonFont);
        backButton.setFont(buttonFont);

        panel.add(centerPanel, BorderLayout.CENTER);

        // 채팅 버튼 추가
        
        chatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 클라이언트와의 채팅 연결 코드 작성
            	 SwingUtilities.invokeLater(new Runnable() {
                     @Override
                     public void run() {
                         ServerGUI serverGUI = new ServerGUI();
                         serverGUI.setVisible(true);
                     }
                 });
             
            }
        });
        

        // 상품 목록 추가 버튼 추가
        
        addProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 상품 목록 추가 코드 작성
            	dispose();
            	ProductManage prodManPage = new ProductManage();
            }
        });
        

        // 이전으로 돌아가기 버튼 추가
        
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 창 닫기
                StartPage startPage = new StartPage(); // StartPage 인스턴스 생성
                startPage.setVisible(true); // StartPage 보이기
            }
        });
        

     

        add(panel);
        setVisible(true);
    }
}
