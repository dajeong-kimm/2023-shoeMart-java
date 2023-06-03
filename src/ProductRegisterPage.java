import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class ProductRegisterPage extends JFrame{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/shoe_mart";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "!!dltnals!!6280";
    
	private String path;
	private FileInputStream fis;
	private long imgSize;
	private JTextField nameField;
	private JTextField priceField;
	private JTextField cntField;
	private JLabel imgNameLabel;
	
	ProductRegisterPage() {
		setTitle("SHOE-MART Product Register");
		setSize(600, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		
		Insets inset = new Insets(20, 70, 20, 70);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 1));
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new GridLayout(1, 2));
		namePanel.setBorder(new EmptyBorder(inset));
		JLabel nameLabel = new JLabel("Product Name: ");
		nameField = new JTextField();
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		
		JPanel pricePanel = new JPanel();
		pricePanel.setLayout(new GridLayout(1, 2));
		pricePanel.setBorder(new EmptyBorder(inset));
		JLabel priceLabel = new JLabel("Product Price: ");
		priceField = new JTextField();
		pricePanel.add(priceLabel);
		pricePanel.add(priceField);
		
		JPanel cntPanel = new JPanel();
		cntPanel.setLayout(new GridLayout(1, 2));
		cntPanel.setBorder(new EmptyBorder(inset));
		JLabel cntLabel = new JLabel("Product Stock: ");
		cntField = new JTextField();
		cntPanel.add(cntLabel);
		cntPanel.add(cntField);
		
		JPanel imgPanel = new JPanel();
		imgPanel.setLayout(new GridLayout(1, 3));
		imgPanel.setBorder(new EmptyBorder(inset));
		JLabel imgLabel = new JLabel("Product Image: ");
		imgNameLabel = new JLabel();
		JButton imgButton = new JButton("Image select");
		imgButton.setActionCommand("Load");
		imgButton.addActionListener(new ButtonClickListener());
		imgPanel.add(imgLabel);
		imgPanel.add(imgNameLabel);
		imgPanel.add(imgButton);
		
		JPanel bottomPanel = new JPanel();
		JButton register = new JButton("Register");
		register.setSize(10, 5);
		register.setActionCommand("Register");
		register.addActionListener(new ButtonClickListener());
		bottomPanel.add(register);
		
		panel.add(namePanel);
		panel.add(pricePanel);
		panel.add(cntPanel);
		panel.add(imgPanel);
		panel.add(bottomPanel);
		
		add(panel);
		setVisible(true);

	}
	
	
	
	class ButtonClickListener implements ActionListener {
		
		private void loadImage() {
			JFileChooser j = new JFileChooser();
			int r = j.showOpenDialog(null);
			
			if (r == JFileChooser.APPROVE_OPTION) {
				path = j.getSelectedFile().getName();
				
				try {
					File file = new File(j.getSelectedFile().getAbsolutePath());
					fis = new FileInputStream(file);
					imgSize = file.length();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				imgNameLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
				imgNameLabel.setText(path);
			}
		}
		
		public boolean isInteger(String s) {
			for (int i = 0; i < s.length(); i++) {
				if (!Character.isDigit(s.charAt(i)))
					return false;
			}
			return true;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String s = e.getActionCommand();
			
			if (s.equals("Register")) {
				String name = nameField.getText();
				String p = priceField.getText();
				String c = cntField.getText();
				
				if (!isInteger(p) || !isInteger(c)) {

					SwingUtilities.invokeLater(() -> {
						JOptionPane.showMessageDialog(null, "정수를 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);

					});

				}

				else {
					int price = Integer.parseInt(priceField.getText());
					int cnt = Integer.parseInt(cntField.getText());

					Connection conn;
					PreparedStatement psmt;

					try {
						Class.forName("com.mysql.jdbc.Driver");
						conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

						String insertQuery = "insert into products values(null, ?, ?, ?, ?)";

						psmt = conn.prepareStatement(insertQuery);
						psmt.setString(1, name);
						psmt.setInt(2, price);
						psmt.setInt(3, cnt);
						psmt.setBinaryStream(4, fis, imgSize);

						int change = psmt.executeUpdate();

						if (change > 0) {
							SwingUtilities.invokeLater(() -> {
								JOptionPane.showMessageDialog(null, "상품을 추가했습니다.", "상품 추가",
										JOptionPane.INFORMATION_MESSAGE);

							});
							dispose();
							ProductRegisterPage newPage = new ProductRegisterPage();
						} else {
							SwingUtilities.invokeLater(() -> {
								JOptionPane.showMessageDialog(null, "상품이 추가되지 않았습니다.", "상품 추가 실패",
										JOptionPane.ERROR_MESSAGE);
							});

						}

						fis.close();
						psmt.close();
						conn.close();
					} catch (Exception x) {
						x.printStackTrace();
					}
				}
			}
			else if (s.equals("Load")) {
				loadImage();
			}
		}
	}

}
