import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductManage extends JFrame{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/shoe_mart";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "!!dltnals!!6280";
    
    HashMap<Integer, JTextField> text;
    HashMap<Integer, JLabel> imglabel;
    private JPanel panel;
    private JScrollPane scroll;
    
	ProductManage() {
		
		setTitle("SHOE-MART Product Management");
		setSize(900, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel center = cartList();
		scroll = new JScrollPane(center, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scroll, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel();
		bottom.setLayout(new FlowLayout());
		JButton back = new JButton("관리자 페이지");
		JButton manage = new JButton("상품 추가");
		
		bottom.add(back);
		bottom.add(manage);
		panel.add(bottom, BorderLayout.SOUTH);
		
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				AdministratorPage admin = new AdministratorPage();
			}
		});
		
		manage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductRegisterPage prp = new ProductRegisterPage();
			}
		});
		
		add(panel);
		setVisible(true);
	}
	
	private ImageIcon loadImg(Blob blob) {
		try {
			InputStream is = blob.getBinaryStream(1, blob.length());
			BufferedImage img = ImageIO.read(is);
			
			ImageIcon imgIcon = new ImageIcon(img);
			Image image = imgIcon.getImage();
			Image changedImg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			ImageIcon changed = new ImageIcon(changedImg);
			
			return changed;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private JPanel cartList() {
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(0, 1));
		text = new HashMap<>();
		imglabel = new HashMap<>();
		
		Connection conn;
		Statement stmt;
		ResultSet rs;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			
			stmt = conn.createStatement();
			String cartQuery = "select * from products";
			
			rs = stmt.executeQuery(cartQuery);
			
			while(rs.next()) {
				JPanel list = new JPanel();
				list.setLayout(new GridLayout(1, 6));
				list.setBorder(new EmptyBorder(5, 5, 5, 5));
				int key = rs.getInt(1);
				
				String pName = rs.getString(2);
				int price = rs.getInt(3);
				int stock = rs.getInt(4);
				Blob img = rs.getBlob(5);
				
				JLabel name = new JLabel(pName);
				name.setHorizontalAlignment(JLabel.CENTER);
				list.add(name);
				
				JLabel image = new JLabel(loadImg(img));
				list.add(image);
				imglabel.put(key, image);
				
				JPanel btpanel = new JPanel();
				btpanel.setLayout(new GridLayout(1, 1));
				btpanel.setBorder(new EmptyBorder(50, 0, 50, 0));
				JButton change = new JButton("변경");
				change.setBorder(new EmptyBorder(20, 0, 20, 0));
				change.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent a) {
						JFileChooser j = new JFileChooser();
						int r = j.showOpenDialog(null);
						
						if (r == JFileChooser.APPROVE_OPTION) {
							try {
								Connection conn2 = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
								
								File file = new File(j.getSelectedFile().getAbsolutePath());
								InputStream is = new FileInputStream(file);
								FileInputStream fis = new FileInputStream(file);
								byte[] byteArray = new byte[(int) file.length()];
								is.read(byteArray);
								
								Blob blob = new javax.sql.rowset.serial.SerialBlob(byteArray);
								imglabel.get(key-1).setIcon(loadImg(blob));
								
								String updateQ = "update products set img = (?) where pId = " + key;
								PreparedStatement stmt2 = conn2.prepareStatement(updateQ);

								stmt2.setBinaryStream(1, fis, file.length());
								stmt2.executeUpdate();
	                            
								JOptionPane.showMessageDialog(ProductManage.this, "이미지가 변경되었습니다.");

								stmt2.close(); conn2.close(); fis.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				});
				btpanel.add(change);
				list.add(btpanel);
				
				JLabel pr = new JLabel(Integer.toString(price));
				pr.setHorizontalAlignment(JLabel.CENTER);
				list.add(pr);
				
				JPanel s = new JPanel();
				s.setLayout(new GridLayout(1, 1));
				s.setBorder(new EmptyBorder(20, 0, 20, 0));
				JTextField st = new JTextField(Integer.toString(stock));
				st.setHorizontalAlignment(JTextField.CENTER);
				st.setBorder(new LineBorder(Color.black));
				text.put(key, st);
				
				text.get(key).addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent ke) {						
						if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyChar() == 8)
							text.get(key).setEditable(true);
						else {
							text.get(key).setEditable(false);
							text.get(key).setBackground(Color.white);
						}
					}
				});
				
				s.add(st);
				list.add(s);
				
				JPanel bt = new JPanel();
				bt.setBorder(new EmptyBorder(10, 0, 10, 0));
				bt.setLayout(new GridLayout(0, 1, 10, 10));
				JButton stockChange = new JButton("재고 수정");
				JButton deleteProduct = new JButton("상품 삭제");
				
				stockChange.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							Connection conn2 = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
							Statement stmt2 = conn2.createStatement();
							String update = "update products set cnt = " + text.get(key).getText() + " where pId = " + key;
														
							stmt2.executeUpdate(update);

							JOptionPane.showMessageDialog(ProductManage.this, "수량이 변경되었습니다.");

							stmt2.close(); conn2.close();
							
						} catch (Exception a) {
							a.printStackTrace();
						}
					}
				});
				
				deleteProduct.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent a) {
						try {
							System.out.println(key);
							Connection conn2 = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
							Statement stmt2 = conn2.createStatement();
							String delete = "delete from products where pId = " + key;
							
							stmt2.executeUpdate(delete);
                            JOptionPane.showMessageDialog(ProductManage.this, "제품이 삭제되었습니다.");

							stmt2.close(); conn2.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						panel.remove(scroll);
						JPanel refresh = cartList();
						scroll = new JScrollPane(refresh, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
						panel.add(scroll, BorderLayout.CENTER);
						
						panel.revalidate();
						panel.repaint();
					}
				});
				
				bt.add(stockChange);
				bt.add(deleteProduct);
				list.add(bt);
				
				center.add(list);
				
				
			}
			rs.close(); stmt.close(); conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return center;
	}
}
