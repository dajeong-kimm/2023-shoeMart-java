import java.sql.Blob;

public class Product {

    private String pName;
    private int price;
    private int cnt;
    private Blob img;
    
    public Product(String pName, int price, int cnt, Blob img) {
        this.pName = pName;
        this.price = price;
        this.cnt = cnt;
        this.img = img;
    }

    public String getpName() {
        return pName;
    }

    public int getPrice() {
        return price;
    }

    public int getCnt() {
        return cnt;
    }

    public Blob getImg() {
        return img;
    }
}
