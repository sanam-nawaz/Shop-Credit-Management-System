import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CustomerDAO customerDAO = new CustomerDAO();
        ProductDAO productDAO = new ProductDAO();
        SalesDAO salesDAO = new SalesDAO();
        CreditDAO creditDAO = new CreditDAO();

        while (true) {
            System.out.println("\n===== SHOP & CREDIT SYSTEM =====");
            System.out.println("1.  Add Customer");
            System.out.println("2.  View Customers");
            System.out.println("3.  Add Product");
            System.out.println("4.  View Products");
            System.out.println("5.  New Cash Sale (Quick Sell)");
            System.out.println("6.  New Credit Sale");
            System.out.println("7.  Record Payment (Clear Credit)");
            System.out.println("8.  Check Customer Balance");
            System.out.println("9.  View All Sales History");
            System.out.println("10. Exit");
            System.out.print("Choice: ");
            
            int choice = sc.nextInt();
            switch (choice) {
                case 1: 
                    sc.nextLine(); // Clear buffer
                    System.out.print("Name: "); String name = sc.nextLine();
                    System.out.print("Phone: "); String phone = sc.nextLine();
                    customerDAO.addCustomer(name, phone);
                    break;

                case 2: 
                    customerDAO.viewCustomers();
                    break;

                case 3: 
                    sc.nextLine(); // Clear buffer
                    System.out.print("Product Name: "); String pName = sc.nextLine();
                    System.out.print("Price: "); double price = sc.nextDouble();
                    System.out.print("Stock: "); int stock = sc.nextInt();
                    productDAO.addProduct(pName, price, stock);
                    break;

                case 4:
                    productDAO.viewProducts(); 
                    break;

                case 5: // QUICK CASH SALE
                    System.out.println("\n--- NEW CASH SALE ---");
                    System.out.print("Enter Product ID: ");
                    int cashPId = sc.nextInt();
                    System.out.print("Enter Quantity: ");
                    int cashQty = sc.nextInt();

                    salesDAO.recordCashSale(cashPId, cashQty); 
                    break;

                case 6: // CREDIT SALE: Automatic Calculation
                    System.out.println("\n--- NEW CREDIT SALE ---");
                    System.out.print("Customer ID: "); 
                    int cId = sc.nextInt();
                    System.out.print("Product ID: "); 
                    int pId = sc.nextInt();
                    System.out.print("Qty: "); 
                    int qty = sc.nextInt();
                    
                    // This calls the method that fetches price and calculates total
                    salesDAO.recordCreditSale(cId, pId, qty);
                    break;

                case 7: 
                    System.out.print("Customer ID: "); int payId = sc.nextInt();
                    System.out.print("Payment Amount: "); double amt = sc.nextDouble();
                    creditDAO.addPayment(payId, amt);
                    break;

                case 8: 
                    System.out.print("Enter Customer ID to check: "); int balId = sc.nextInt();
                    creditDAO.checkBalance(balId);
                    break;

                case 9:
                    salesDAO.viewSales(); 
                    break;    

                case 10:
                    System.out.println("Exiting System... Goodbye!");
                    return;

                default:
                    System.out.println("Invalid Choice! Please try again.");
            }
        }
    }
}