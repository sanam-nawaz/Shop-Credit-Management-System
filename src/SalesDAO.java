import java.sql.*;

public class SalesDAO {

    Connection con = DBConnection.getConnection();

    // CREATE SALE (basic version)
    public int createSale(int customerId, double totalAmount) {
        int saleId = -1;

        try {
            String sql = "INSERT INTO sales(customer_id, total_amount) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, customerId);
            ps.setDouble(2, totalAmount);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                saleId = rs.getInt(1);
            }

            System.out.println("✔ Sale Created. ID: " + saleId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return saleId;
    }

    // ADD ITEMS TO SALE
    public void addSaleItem(int saleId, int productId, int quantity, double price) {
        try {
            String sql = "INSERT INTO sale_details(sale_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, saleId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setDouble(4, price);

            ps.executeUpdate();

            // reduce stock
            String updateStock = "UPDATE products SET stock = stock - ? WHERE product_id = ?";
            PreparedStatement ps2 = con.prepareStatement(updateStock);

            ps2.setInt(1, quantity);
            ps2.setInt(2, productId);

            ps2.executeUpdate();

            System.out.println("✔ Item added to sale");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}