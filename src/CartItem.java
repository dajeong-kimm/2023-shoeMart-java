import java.util.ArrayList;
import java.util.List;

public class CartItem {
    private Product product;
    private int quantity;
    private int price;
    private String img;
    private static List<CartItem> cartItems = new ArrayList<>();

    public CartItem(Product product, int quantity, int price, String img) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.img = img;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public String getImg() {
        return img;
    }

    public static List<CartItem> getCartItems() {
        return cartItems;
    }

    public static void addToCart(Product product) {
        boolean found = false;
        for (CartItem item : cartItems) {
            if (item.getProduct().equals(product)) {
                item.setQuantity(item.getQuantity() + 1);
                found = true;
                break;
            }
        }
        if (!found) {
            CartItem newItem = new CartItem(product, 1, product.getPrice(), product.getImg());
            cartItems.add(newItem);
        }
    }
}
