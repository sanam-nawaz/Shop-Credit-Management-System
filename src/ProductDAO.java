import java.sql.*;

public class ProductDAO {

    Connection con = DBConnection.getConnection();

    public void addProduct(String name, double price, int stock) {
        try {
            String query = "INSERT INTO products(name, price, stock) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, stock);

            ps.executeUpdate();
            System.out.println("Product Added");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewProducts() {
        try {
            String query = "SELECT * FROM products";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                    rs.getInt("product_id") + " | " +
                    rs.getString("name") + " | " +
                    rs.getDouble("price") + " | " +
                    rs.getInt("stock")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}