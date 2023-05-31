import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PayPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private User user;
    private List<CartItem> cartItems;
    private int totalPrice;

    public PayPage(User user, List<CartItem> cartItems) {
        this.user = user;
        this.cartItems = cartItems;
        this.totalPrice = calculateTotalPrice();

        setTitle("Pay Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // 장바구니 상품 목록
        JPanel cartItemsPanel = new JPanel();
        cartItemsPanel.setLayout(new GridLayout(cartItems.size(), 2, 10, 10));

        for (CartItem cartItem : cartItems) {
            JLabel nameLabel = new JLabel(cartItem.getProduct().getpName());
            JLabel priceLabel = new JLabel(Integer.toString(cartItem.getPrice()));

            cartItemsPanel.add(nameLabel);
            cartItemsPanel.add(priceLabel);
        }

        panel.add(cartItemsPanel, BorderLayout.CENTER);

        // 총 가격
        JPanel totalPricePanel = new JPanel();
        totalPricePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel totalPriceLabel = new JLabel("총 가격: " + totalPrice);
        totalPricePanel.add(totalPriceLabel);

        panel.add(totalPricePanel, BorderLayout.SOUTH);

        // 하단 버튼
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton productPageButton = new JButton("상품 페이지로");
        JButton confirmPaymentButton = new JButton("결제확정");

        bottomPanel.add(productPageButton);
        bottomPanel.add(confirmPaymentButton);

        productPageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                MyPage myPage = new MyPage(user);
            }
        });

        confirmPaymentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int balance = user.getPoint() - totalPrice;
                user.setPoint(balance);

                JOptionPane.showMessageDialog(PayPage.this, "결제가 완료되었습니다.\n잔액: " + balance);

                cartItems.clear();
                dispose();
            }
        });

        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    // 총 가격 계산
    private int calculateTotalPrice() {
        int totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getPrice();
        }
        return totalPrice;
    }
}
