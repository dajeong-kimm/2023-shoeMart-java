public class Product {
    private String pName;
    private int price;
    private int cnt;
    private String img;

    public Product(String pName, int price, int cnt, String img) {
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

    public String getImg() {
        return img;
    }
}
