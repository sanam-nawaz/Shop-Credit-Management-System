import java.sql.*;

public class CreditDAO {

    Connection con = DBConnection.getConnection();

    // BORROW MONEY: Adds a record when a sale is made on credit
    public void addCredit(int customerId, int saleId, double amount) {
        try {
            String sql = "INSERT INTO credits(customer_id, sale_id, amount, type) VALUES (?, ?, ?, 'BORROW')";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, customerId);
            ps.setInt(2, saleId);
            ps.setDouble(3, amount);

            ps.executeUpdate();
            System.out.println("Credit Added (BORROW)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // PAYMENT: Records when a customer pays back their debt
    public void addPayment(int customerId, double amount) {
        try {
            String sql = "INSERT INTO credits(customer_id, sale_id, amount, type) VALUES (?, NULL, ?, 'PAY')";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, customerId);
            ps.setDouble(2, amount);

            ps.executeUpdate();
            System.out.println("Payment Recorded");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CHECK BALANCE: Calculates total debt by subtracting payments from borrowings
    public void checkBalance(int customerId) {
    // This SQL handles the math and ignores case (UPPER)
    String sql = "SELECT " +
                 "SUM(CASE WHEN UPPER(type) = 'BORROW' THEN amount ELSE 0 END) - " +
                 "SUM(CASE WHEN UPPER(type) = 'PAY' THEN amount ELSE 0 END) AS balance " +
                 "FROM credits WHERE customer_id = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setInt(1, customerId);
        
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                double balance = rs.getDouble("balance");
                System.out.println("\n--------------------------------");
                System.out.println("Customer ID: " + customerId);
                System.out.println("Current Debt Balance: " + balance);
                System.out.println("--------------------------------");
            }
        }
    } catch (SQLException e) {
        System.err.println("Error checking balance: " + e.getMessage());
    }
}
}