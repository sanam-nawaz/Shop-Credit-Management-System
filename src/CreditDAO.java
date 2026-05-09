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
            System.out.println("✔ Credit Added (BORROW)");

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
            System.out.println("✔ Payment Recorded");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CHECK BALANCE: Calculates total debt by subtracting payments from borrowings
    public void checkBalance(int customerId) {
        try {
            String sql = "SELECT type, SUM(amount) as total FROM credits WHERE customer_id = ? GROUP BY type";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, customerId);

            ResultSet rs = ps.executeQuery();
            double borrow = 0;
            double pay = 0;

            while (rs.next()) {
                String type = rs.getString("type");
                if (type.equals("BORROW")) {
                    borrow = rs.getDouble("total");
                } else if (type.equals("PAY")) {
                    pay = rs.getDouble("total");
                }
            }

            double balance = borrow - pay;
            System.out.println("Customer Balance: " + balance);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}