import java.sql.*;

public class DBConnection {
    static String URL = "jdbc:mysql://localhost:3306/inventory_db";
    static String USER = "root";
    static String PASS = "12345";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
