import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CartPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private User user;
    private List<CartItem> cartItems; // Change the type to List<CartItem>
    private JLabel totalPriceLabel;

    public CartPage(User user, List<CartItem> cartItems) { // Update the constructor
        this.user = user;
        this.cartItems = cartItems;

        // Rest of the code...

        // 장바구니 상품 목록
        JPanel cartItemsPanel = new JPanel();
        cartItemsPanel.setLayout(new GridLayout(cartItems.size(), 3, 10, 10));

        for (CartItem item : cartItems) { // Update the loop variable type
            JLabel nameLabel = new JLabel(item.getProduct().getpName()); // Access the product from the CartItem
            JLabel priceLabel = new JLabel(Integer.toString(item.getPrice())); // Use the getPrice() method from CartItem
            JButton cancelButton = new JButton("취소하기");

            cartItemsPanel.add(nameLabel);
            cartItemsPanel.add(priceLabel);
            cartItemsPanel.add(cancelButton);

            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    removeFromCart(item.getProduct()); // Pass the product to removeFromCart()
                }
            });
        }

        // Rest of the code...

    }

    // 장바구니에서 상품 제외
    private void removeFromCart(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().equals(product)) {
                cartItems.remove(item);
                break;
            }
        }
        totalPriceLabel.setText("총 가격: " + calculateTotalPrice());
        JOptionPane.showMessageDialog(CartPage.this, "상품이 장바구니에서 제외되었습니다.");
    }

    // 총 가격 계산
    private int calculateTotalPrice() {
        int totalPrice = 0;
        for (CartItem item : cartItems) { // Update the loop variable type
            totalPrice += item.getPrice(); // Use the getPrice() method from CartItem
        }
        return totalPrice;
    }
}
