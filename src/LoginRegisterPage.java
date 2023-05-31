import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginRegisterPage extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField idField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private List<CartItem> cartItems;

    public LoginRegisterPage() {
        setTitle("SHOE-MART Login/Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // 마진 추가

        // 로고 이미지 추가
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/SHOE-MART_logo.PNG"));
        JLabel logoLabel = new JLabel(logoIcon);
        panel.add(logoLabel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        JLabel idLabel = new JLabel("ID:");
        inputPanel.add(idLabel);

        idField = new JTextField();
        inputPanel.add(idField);

        JLabel pwdLabel = new JLabel("Password:");
        inputPanel.add(pwdLabel);

        passwordField = new JPasswordField();
        inputPanel.add(passwordField);

        centerPanel.add(inputPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        buttonPanel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        buttonPanel.add(registerButton);

        centerPanel.add(buttonPanel);

        panel.add(centerPanel, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
        
        
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            // 로그인 버튼 클릭 시 처리
            String id = idField.getText();
            String password = new String(passwordField.getPassword());

            // 사용자 인증 로직을 구현합니다.
            User user = User.getUser(id);
            if (user != null && user.getPwd().equals(password)) {
                JOptionPane.showMessageDialog(this, "로그인에 성공했습니다.", "로그인 성공", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // 로그인/회원가입 페이지 닫기

                // Main Page로 이동하는 로직을 추가합니다.
                MainPage mainPage = new MainPage(user,cartItems);
                mainPage.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "유효하지 않은 사용자 정보입니다.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == registerButton) {
            // 회원가입 버튼 클릭 시 처리
            dispose(); // 로그인/회원가입 페이지 닫기

            // 회원가입 페이지로 이동하는 로직을 추가합니다.
            RegisterPage registerPage = new RegisterPage();
            registerPage.setVisible(true);
        }
    }

 /*
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginRegisterPage();
        });
    }
    */
    
    
}

class RegisterPage extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField nameField;
    private JTextField idField;
    private JPasswordField passwordField;
    private JPasswordField paypwdField;
    private JButton registerButton;
    private JButton backButton;

    public RegisterPage() {
        setTitle("SHOE-MART Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // 마진 추가
        
     // Set up the logo image
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/SHOE-MART_logo.PNG"));
        JLabel logoLabel = new JLabel(logoIcon);
        panel.add(logoLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 2));

        JLabel nameLabel = new JLabel("Name:");
        centerPanel.add(nameLabel);

        nameField = new JTextField();
        centerPanel.add(nameField);

        JLabel idLabel = new JLabel("ID:");
        centerPanel.add(idLabel);

        idField = new JTextField();
        centerPanel.add(idField);

        JLabel pwdLabel = new JLabel("Password:");
        centerPanel.add(pwdLabel);

        passwordField = new JPasswordField();
        centerPanel.add(passwordField);

        JLabel paypwdLabel = new JLabel("Pay Password:");
        centerPanel.add(paypwdLabel);

        paypwdField = new JPasswordField();
        centerPanel.add(paypwdField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        buttonPanel.add(registerButton);
        
        backButton = new JButton("Back");
        backButton.addActionListener(this);
        buttonPanel.add(backButton);


        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        
        
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            // 회원가입 버튼 클릭 시 처리
            String name = nameField.getText();
            String id = idField.getText();
            String password = new String(passwordField.getPassword());
            String paypwd = new String(paypwdField.getPassword());
            int point = 0;

            // 회원가입 로직을 구현합니다.
             // Create the 'users' table if it doesn't exist
            User user = new User(name, id, password, paypwd, point);
            user.saveUser();

            JOptionPane.showMessageDialog(this, "회원가입에 성공했습니다.", "회원가입 성공", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // 회원가입 페이지 닫기

            // 로그인/회원가입 페이지로 이동하는 로직을 추가합니다.
            LoginRegisterPage loginRegisterPage = new LoginRegisterPage();
            loginRegisterPage.setVisible(true);
        }else if (e.getSource() == backButton) {
            // Back 버튼 클릭 시 처리
            dispose(); // 현재 페이지 닫기

            // 이전 페이지로 이동하는 로직을 추가합니다.
            LoginRegisterPage loginRegisterPage = new LoginRegisterPage();
            loginRegisterPage.setVisible(true);
        }
    }
}
