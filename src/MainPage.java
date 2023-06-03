import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainPage extends JFrame {
	private static final long serialVersionUID = 1L;
	private User user;
	private ProductList prods = new ProductList();
	private JTextField amtField;

	public MainPage(User user, List<CartItem> cartItems) {
		this.user = user;

		setTitle("SHOE-MART");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1500, 800);
		setResizable(false);
		setLocationRelativeTo(null);
		
		ArrayList<JTextField> text = new ArrayList<>();

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		// 로고 이미지 추가
		ImageIcon logoIcon = new ImageIcon(getClass().getResource("/SHOE-MART_logo.PNG"));
		JLabel logoLabel = new JLabel(logoIcon);
		panel.add(logoLabel, BorderLayout.NORTH);

		int prodAmount = prods.products.size();

		int row = prodAmount % 2 == 0 ? (int) prodAmount / 2 : (int) prodAmount / 2 + 1;
		
		Insets inset = new Insets(70, 5, 70, 5);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(row, 2, 10, 10)); // 4행 2열의 그리드 레이아웃 사용
		JScrollPane scrollPane = new JScrollPane(centerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		Set keyset = prods.products.keySet();
		Iterator<Integer> iter = keyset.iterator();

		while (iter.hasNext()) {
			Integer key = iter.next();
			Product prod = prods.products.get(key);
			JPanel productPanel = new JPanel();
			productPanel.setLayout(new GridLayout(1, 5));
			JLabel productLabel = new JLabel(prod.getpName());
			productLabel.setHorizontalAlignment(JLabel.CENTER);
			JLabel productPrice = new JLabel(Integer.toString(prod.getPrice()));
			productPrice.setHorizontalAlignment(JLabel.CENTER);

			try {
				InputStream is = prod.getImg().getBinaryStream(1, prod.getImg().length());
				BufferedImage image = ImageIO.read(is);
				ImageIcon productIcon = new ImageIcon(image);
				Image img = productIcon.getImage();
				Image changeImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
				ImageIcon changed = new ImageIcon(changeImg);
				JLabel productImageLabel = new JLabel(changed);
				
				JPanel buttonPanel = new JPanel();
				buttonPanel.setLayout(new GridLayout(1, 1));
				buttonPanel.setBorder(new EmptyBorder(inset));
				JButton addCart = new JButton("추가");
				buttonPanel.add(addCart);
								
				JPanel amtDecisionPanel = new JPanel();
				amtDecisionPanel.setLayout(new GridLayout(1, 3));
				JButton sub = new JButton("<");
				amtField = new JTextField("0");
				
				text.add(amtField);
				
				text.get(key-1).setHorizontalAlignment(JTextField.CENTER);
				text.get(key-1).addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent ke) {
						String value = amtField.getText();
						
						int l = value.length();
						if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyChar() == 8) {
							text.get(key-1).setEditable(true);
						} else {
							text.get(key-1).setEditable(false);
							text.get(key-1).setBackground(Color.white);
						}
					}
				});
				JButton add = new JButton(">");
				
				sub.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int num = Integer.parseInt(text.get(key-1).getText());
						
						if (num > 0)
							text.get(key-1).setText(Integer.toString(num-1));
					}
				});
				
				add.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int num = Integer.parseInt(text.get(key-1).getText());
						
						text.get(key-1).setText(Integer.toString(num+1));
					}
				});
				
				addCart.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						addToCart(key.intValue(), Integer.parseInt(text.get(key-1).getText()));
					}
				});

				
				amtDecisionPanel.add(sub);
				amtDecisionPanel.add(text.get(key-1));
				amtDecisionPanel.add(add);
				amtDecisionPanel.setBorder(new EmptyBorder(inset));
				productPanel.add(productImageLabel);
				productPanel.add(productLabel);
				productPanel.add(productPrice);
				productPanel.add(amtDecisionPanel);
				productPanel.add(buttonPanel);
				
				centerPanel.add(productPanel);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		panel.add(scrollPane, BorderLayout.CENTER);

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
				PayPage payPage = new PayPage(user, cartItems); // PayPage 클래스로 이동
			}
		});

	}

	// 장바구니에 상품을 추가하는 메서드
	private void addToCart(int pId, int amt) {
		// 장바구니에 상품 추가하는 로직을 구현
		// 예시로 CartItem 클래스의 addToCart 메서드를 호출하는 것으로 가정합니다.
		CartItem item = new CartItem(this.user, pId, amt);
		item.addCart();
		JOptionPane.showMessageDialog(MainPage.this, "상품이 장바구니에 추가되었습니다.");
	}
}
