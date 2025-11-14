import java.sql.*;
import java.util.*;

public class ProductDAO {

    public void addProduct(Product p) throws Exception {
        String q = "INSERT INTO products(name, description, quantity, price) VALUES (?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(q, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.name);
            ps.setString(2, p.description);
            ps.setInt(3, p.quantity);
            ps.setDouble(4, p.price);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) p.id = rs.getInt(1);
            }
        }
    }

    public List<Product> getAll() throws Exception {
        List<Product> list = new ArrayList<>();
        String q = "SELECT * FROM products ORDER BY id";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(q)) {
            while (rs.next()) {
                list.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("quantity"),
                    rs.getDouble("price")
                ));
            }
        }
        return list;
    }

    public Product findById(int id) throws Exception {
        String q = "SELECT * FROM products WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                    );
                }
            }
        }
        return null;
    }

    public boolean updateProduct(Product p) throws Exception {
        String q = "UPDATE products SET name = ?, description = ?, quantity = ?, price = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, p.name);
            ps.setString(2, p.description);
            ps.setInt(3, p.quantity);
            ps.setDouble(4, p.price);
            ps.setInt(5, p.id);
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    public boolean deleteById(int id) throws Exception {
        String q = "DELETE FROM products WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }
}
