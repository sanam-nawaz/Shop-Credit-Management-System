import java.sql.*;

public class CustomerDAO{

    Connection con = DBConnection.getConnection();

    // ADD CUSTOMERs
    public void addCustomer(String name, String phone) {
        try {
            String query = "INSERT INTO customers(name, phone) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, phone);

            ps.executeUpdate();
            System.out.println(" Customer Added");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // VIEW CUSTOMERS
    public void viewCustomers() {
        try {
            String query = "SELECT * FROM customers";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                    rs.getInt("customer_id") + " | " +
                    rs.getString("name") + " | " +
                    rs.getString("phone")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}