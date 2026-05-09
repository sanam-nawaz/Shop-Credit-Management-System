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
            System.out.println("1. Add Customer");
            System.out.println("2. View Customers");
            System.out.println("3. Add Product");
            System.out.println("4. View Products");
            System.out.println("5. New Credit Sale");
            System.out.println("6. Record Payment");
            System.out.println("7. Check Balance");
            System.out.println("8. Exit");
            System.out.print("Choice: ");
            
            int choice = sc.nextInt();
            switch (choice) {
                case 1: // Matches CustomerDAO.addCustomer(name, phone)
                    sc.nextLine(); 
                    System.out.print("Name: "); String name = sc.nextLine();
                    System.out.print("Phone: "); String phone = sc.nextLine();
                    customerDAO.addCustomer(name, phone);
                    break;

                case 2: // Matches CustomerDAO.viewCustomers()
                    customerDAO.viewCustomers();
                    break;

                case 3: // Matches ProductDAO.addProduct(name, price, stock)
                    sc.nextLine(); 
                    System.out.print("Product Name: "); String pName = sc.nextLine();
                    System.out.print("Price: "); double price = sc.nextDouble();
                    System.out.print("Stock: "); int stock = sc.nextInt();
                    productDAO.addProduct(pName, price, stock);
                    break;

                case 4: // Matches ProductDAO.viewProducts()
                    productDAO.viewProducts();
                    break;

                case 5: // Logic for creating a sale and adding to credit
                    System.out.print("Customer ID: "); int cId = sc.nextInt();
                    System.out.print("Product ID: "); int pId = sc.nextInt();
                    System.out.print("Qty: "); int qty = sc.nextInt();
                    System.out.print("Total: "); double total = sc.nextDouble();
                    
                    int sId = salesDAO.createSale(cId, total);
                    if (sId != -1) {
                        // total/qty gives individual item price for sale_details
                        salesDAO.addSaleItem(sId, pId, qty, total/qty);
                        creditDAO.addCredit(cId, sId, total);
                    }
                    break;

                case 6: // Matches CreditDAO.addPayment(payId, amt)
                    System.out.print("Customer ID: "); int payId = sc.nextInt();
                    System.out.print("Amount: "); double amt = sc.nextDouble();
                    creditDAO.addPayment(payId, amt);
                    break;

                case 7: // Matches CreditDAO.checkBalance(balId)
                    System.out.print("Customer ID: "); int balId = sc.nextInt();
                    creditDAO.checkBalance(balId);
                    break;

                case 8:
                    System.out.println("Exiting System...");
                    return;
                
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}