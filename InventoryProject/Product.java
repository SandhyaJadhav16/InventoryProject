public class Product {
    public int id;
    public String name;
    public String description;
    public int quantity;
    public double price;

    public Product(int id, String name, String description, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public Product(String name, String description, int quantity, double price) {
        this(0, name, description, quantity, price);
    }

    public String toString() {
        return id + " | " + name + " | qty:" + quantity + " | ₹" + price;
    }
}
