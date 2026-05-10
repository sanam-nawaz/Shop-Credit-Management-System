import java.sql.*;

public class SalesDAO {

    // Ideally, get connection inside methods or use a Connection Pool
    private Connection getConnection() {
        return DBConnection.getConnection();
    }

    // 1. CREATE SALE
    public int createSale(int customerId, double totalAmount) {
        String sql = "INSERT INTO Sales (customer_id, sale_date, total_amount) VALUES (?, CURDATE(), ?)";
        
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, customerId);
            ps.setDouble(2, totalAmount);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int saleId = rs.getInt(1);
                    System.out.println("Sale Created. ID: " + saleId);
                    return saleId;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error in createSale: " + e.getMessage());
        }
        return -1;
    }

    // 2. ADD SALE ITEMS & UPDATE STOCK (With Transaction)
    public void addSaleItem(int saleId, int productId, int quantity, double price) {
        String insertItemSql = "INSERT INTO Sale_Details (sale_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        String updateStockSql = "UPDATE Products SET stock = stock - ? WHERE product_id = ?";

        Connection con = null;
        try {
            con = getConnection();
            con.setAutoCommit(false); // Start Transaction

            // Insert Sale Detail
            try (PreparedStatement ps1 = con.prepareStatement(insertItemSql)) {
                ps1.setInt(1, saleId);
                ps1.setInt(2, productId);
                ps1.setInt(3, quantity);
                ps1.setDouble(4, price);
                ps1.executeUpdate();
            }

            // Update Stock
            try (PreparedStatement ps2 = con.prepareStatement(updateStockSql)) {
                ps2.setInt(1, quantity);
                ps2.setInt(2, productId);
                ps2.executeUpdate();
            }

            con.commit(); // Save both changes
            System.out.println("Items added and stock updated.");

        } catch (SQLException e) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            System.err.println("Transaction failed. Rolled back: " + e.getMessage());
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    // 3. VIEW ALL SALES
    public void viewSales() {
        String sql = "SELECT s.sale_id, c.name, s.total_amount, s.sale_date " +
                     "FROM Sales s " +
                     "JOIN Customers c ON s.customer_id = c.customer_id";

        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n--- ALL SALES RECORDS ---");
            System.out.printf("%-10s | %-20s | %-12s | %-15s\n", "ID", "Customer Name", "Total", "Date");
            System.out.println("------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-10d | %-20s | %-12.2f | %-15s\n",
                        rs.getInt("sale_id"),
                        rs.getString("name"),
                        rs.getDouble("total_amount"),
                        rs.getDate("sale_date"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching sales: " + e.getMessage());
        }
    }
        // Add this inside SalesDAO.java
public void recordCashSale(int productId, int quantity) {
    String fetchPriceSql = "SELECT price, name FROM products WHERE product_id = ?"; // Added name for better feedback
    String saleSql = "INSERT INTO Sales (sale_date, total_amount, customer_id) VALUES (CURDATE(), ?, NULL)";
    String detailSql = "INSERT INTO sale_details (sale_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
    String stockSql = "UPDATE products SET stock = stock - ? WHERE product_id = ?";

    try (Connection con = DBConnection.getConnection()) {
        con.setAutoCommit(false);

        double unitPrice = 0;
        String productName = "";
        
        // Fetch Price and Name
        try (PreparedStatement psPrice = con.prepareStatement(fetchPriceSql)) {
            psPrice.setInt(1, productId);
            ResultSet rs = psPrice.executeQuery();
            if (rs.next()) {
                unitPrice = rs.getDouble("price");
                productName = rs.getString("name");
            } else {
                System.out.println("Product not found!");
                return;
            }
        }

        // CALCULATE AND DISPLAY TOTAL
        double totalPrice = unitPrice * quantity;
        System.out.println("\n------------------------------");
        System.out.println("Product: " + productName);
        System.out.println("Price:   " + unitPrice + " x " + quantity);
        System.out.println("TOTAL:   " + totalPrice);
        System.out.println("------------------------------");

        // Create Sale
        int saleId = -1;
        try (PreparedStatement ps1 = con.prepareStatement(saleSql, Statement.RETURN_GENERATED_KEYS)) {
            ps1.setDouble(1, totalPrice);
            ps1.executeUpdate();
            ResultSet rs = ps1.getGeneratedKeys();
            if (rs.next()) saleId = rs.getInt(1);
        }

        // Sale Details
        try (PreparedStatement ps2 = con.prepareStatement(detailSql)) {
            ps2.setInt(1, saleId);
            ps2.setInt(2, productId);
            ps2.setInt(3, quantity);
            ps2.setDouble(4, unitPrice);
            ps2.executeUpdate();
        }

        // Update Stock
        try (PreparedStatement ps3 = con.prepareStatement(stockSql)) {
            ps3.setInt(1, quantity);
            ps3.setInt(2, productId);
            ps3.executeUpdate();
        }

        con.commit();
        System.out.println("Sale Completed Successfully!");

    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }

}
    public void recordCreditSale(int customerId, int productId, int quantity) {
    // 1. SQL queries - Notice 'stock' is used instead of 'stock_quantity'
    String priceSql  = "SELECT price FROM products WHERE product_id = ?";
    String saleSql   = "INSERT INTO Sales (customer_id, sale_date, total_amount) VALUES (?, CURDATE(), ?)";
    String detailSql = "INSERT INTO sale_details (sale_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
    String creditSql = "INSERT INTO credits (customer_id, sale_id, amount, type) VALUES (?, ?, ?, 'BORROW')";
    String stockSql  = "UPDATE products SET stock = stock - ? WHERE product_id = ?"; 

    try (Connection con = DBConnection.getConnection()) {
        con.setAutoCommit(false); // Start Transaction

        double unitPrice = 0;

        // STEP A: Fetch the price automatically
        try (PreparedStatement psPrice = con.prepareStatement(priceSql)) {
            psPrice.setInt(1, productId);
            ResultSet rs = psPrice.executeQuery();
            if (rs.next()) {
                unitPrice = rs.getDouble("price");
            } else {
                System.out.println("Error: Product not found.");
                return;
            }
        }

        // STEP B: Calculate total automatically
        double totalAmount = unitPrice * quantity;

        // STEP C: Insert Sale and get ID
        int saleId = -1;
        try (PreparedStatement psSale = con.prepareStatement(saleSql, Statement.RETURN_GENERATED_KEYS)) {
            psSale.setInt(1, customerId);
            psSale.setDouble(2, totalAmount);
            psSale.executeUpdate();
            ResultSet rs = psSale.getGeneratedKeys();
            if (rs.next()) saleId = rs.getInt(1);
        }

        // STEP D: Insert Sale Details
        try (PreparedStatement psDetail = con.prepareStatement(detailSql)) {
            psDetail.setInt(1, saleId);
            psDetail.setInt(2, productId);
            psDetail.setInt(3, quantity);
            psDetail.setDouble(4, unitPrice);
            psDetail.executeUpdate();
        }

        // STEP E: Record the Debt in Credits table
        try (PreparedStatement psCredit = con.prepareStatement(creditSql)) {
            psCredit.setInt(1, customerId);
            psCredit.setInt(2, saleId);
            psCredit.setDouble(3, totalAmount);
            psCredit.executeUpdate();
        }

        // STEP F: Update Stock
        try (PreparedStatement psStock = con.prepareStatement(stockSql)) {
            psStock.setInt(1, quantity);
            psStock.setInt(2, productId);
            psStock.executeUpdate();
        }

        con.commit(); // Save everything
        System.out.println("Total Calculated: " + totalAmount);

    } catch (SQLException e) {
        System.err.println("Transaction failed: " + e.getMessage());
    }


}
    }
