import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ParseException {

        Scanner scanner = new Scanner(System.in);
        String fileAccount = "E:\\Study\\order-management-system\\Data\\account.txt";
        String fileAdmin = "E:\\Study\\order-management-system\\Data\\admin.txt";
        String fileCategory = "E:\\Study\\order-management-system\\Data\\category.txt";
        String fileOrder = "E:\\Study\\order-management-system\\Data\\order.txt";
        String fileProduct = "E:\\Study\\order-management-system\\Data\\products.txt";
        Account currentAccount = null;
        Customer currentCustomer = null;
        Cart cart = null;
        int choice = 0;
        String choiceInfo = null;
        boolean isLogged = false;
        boolean isLoggedAdmin = false;


        while (true) {
            System.out.println("Enter 1 to register, 2 to login, or 0 to exit:");
            int option = scanner.nextInt();
            if (option == 0) {
                System.out.println("Thank you and Goodbye!");
                break;
            }
            else if (option == 1) {
                Customer.registerAccount(fileAccount);
            }
            else if (option == 2) {
                System.out.println("Enter your username and password:");
                String username = scanner.next();
                String password = scanner.next();
                currentAccount = Account.login(fileAccount, username, password);
                if (currentAccount != null) {
                    if (currentAccount.isAdmin()) {
                        System.out.printf("Admin logged in, welcome to Order Management System\n");
                        isLoggedAdmin = true;
                        break;
                    } else {
                        currentCustomer = (Customer) currentAccount;

                        System.out.printf("Logged in, welcome %s to Order Management System\n", currentCustomer.getUsername());
                        isLogged = true;
                        break;
                    }
                }
            }
        }

        while (isLogged) {
            choice = 0;

            System.out.printf("Please choose the action:\n\tChoose \"1\" if you want to check or update your information" +
                    "\n\tChoose \"2\" if you want to check your current membership status" +
                    "\n\tChoose \"3\" if you want to list all products" +
                    "\n\tChoose \"4\" if you want to search for a product" +
                    "\n\tChoose \"5\" if you want to check on your cart" +
                    "\n\tChoose \"6\" if you want to place your order" +
                    "\n\tChoose \"7\" if you want to get information about a particular order" +
                    "\n\tChoose \"8\" if you want to get information of all orders made by you" +
                    "Choose \"9\" if you want to sign out!\n");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Your current information is:");
                    System.out.println(currentAccount);
                    System.out.println("Do you want to change your personal information (including name, phone number, email and your address) or you want to change your password? Select (1) to change your information and (2) for password. If you not wish to change anything ,please press (3). 1/2?");
                    choiceInfo = scanner.nextLine();
                    if (choiceInfo.equals("1")) {
                        currentAccount = Method.updateCustomerInformation((Customer) currentAccount);
                    } else if (choiceInfo.equals("2")) {
                        currentAccount = Method.updatePassword((Customer) currentAccount);
                    }
                    System.out.println("Press enter to come back to main screen");
                    scanner.nextLine();
                    System.out.println("Coming back to main screen...");
                    break;
                case 2:
                    String membership = currentCustomer.getMembership();
                    System.out.printf("Your current membership is %s.\n", membership);
                    if (membership.equals("Regular")) {
                        System.out.println("You don't have any discount.");
                    } else if (membership.equals("Silver")) {
                        System.out.println("You have a discount of 5%.");
                    } else if (membership.equals("Gold")) {
                        System.out.println("You have a discount of 10%.");
                    } else if (membership.equals("Platinum")) {
                        System.out.println("You have a discount of 15%.");
                    } else {
                        System.out.println("Invalid membership, please contact the admin for verification!");
                    };
                    System.out.println("Press enter to come back to main screen");
                    scanner.nextLine();
                    System.out.println("Coming back to main screen...");

                    break;
                case 3:
                    System.out.printf("Do you want to\n\t- (1) List all products\n\t- (2) List all products in a category with pricing order (asc or des)" +
                            "\n\t- (3) List all products in a category with a price range" +
                            "\n\t- (4) Go back to main menu.\n");
                    choiceInfo = scanner.nextLine();

                    List<Product> products = null;
                    if (choiceInfo.equals("1")) {
                        products = Product.listProducts();
                    } else if (choiceInfo.equals("2")) {
                        System.out.println("Please input the category name:");
                        String categoryListingName = scanner.nextLine();
                        System.out.println("Please input the pricing order: asc/des");
                        String listingOrder = scanner.nextLine();
                        products = Product.listProductsByCategory(categoryListingName, listingOrder, fileCategory);
                    } else if (choiceInfo.equals("3")) {
                        System.out.println("Please input the category name:");
                        String categoryListingName = scanner.nextLine();
                        System.out.println("Please input the min price:");
                        int minListingPrice = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Please input the max price:");
                        int maxListingPrice = scanner.nextInt();
                        scanner.nextLine();
                        products = Product.listProductsInPriceRangeAndCategory(minListingPrice, maxListingPrice, categoryListingName);
                    } else {
                        System.out.println("CComing back to main screen...");
                        break;
                    };
                    if (products == null) {
                        System.out.println("No products found, please re-check the category or your pricing condition!");
                    } else {
                        System.out.println("All products listing...");

                        System.out.println("Products are listed as \"Product ID - Product Name - Product Price - Product Category\"");
                        for (Product product : products) {
                            String id = product.getId();
                            String productName = product.getProductName();
                            int price = product.getPrice();
                            String category = product.getCategory();
                            System.out.println(id + " - " + productName + " - " + price + " - " + category);
                        }
                    }
                    System.out.println("Press enter to come back to main screen");
                    scanner.nextLine();
                    System.out.println("Coming back to main screen...");
                    break;

                case 4:
                    System.out.println("Do you want to find product by\n\t- (1) Id\n\t- (2) Name\n\t- (3) Go back to main menu.");
                    choiceInfo = scanner.nextLine();
                    if (choiceInfo.equals("1")) {
                        System.out.println("Please input the product id:");
                        String productIdFinding = scanner.nextLine();
                        Product productResult = Product.findProductById(productIdFinding);
                        if (productResult == null) {
                            System.out.printf("There is no product with id %s.\n", productIdFinding);
                        } else {
                            System.out.printf("The product with id %s is:\n %s\n", productIdFinding, productResult.toString());
                        }
                    } else if (choiceInfo.equals("2")) {
                        System.out.println("Please input the product name:");
                        String productNameFinding = scanner.nextLine();
                        Product productResult = Product.findProductByName(productNameFinding);
                        if (productResult == null) {
                            System.out.printf("There is no product with the name %s\n", productNameFinding);
                        } else {
                            System.out.printf("The product with the name %s is:\n %s\n", productNameFinding, productResult.toString());
                        }
                    }
                    System.out.println("Press enter to come back to main screen");
                    scanner.nextLine();
                    System.out.println("Coming back to main screen...");
                    break;
                case 5:
                    if (cart == null) {
                        System.out.println("There is no product in your cart");
                    } else {
                        System.out.println("Your current cart is:");
                        System.out.println(cart);
                        System.out.println("The total price of your cart is:");
                        System.out.println(cart.getTotalAmount());
                    };
                    System.out.println("Press enter to come back to main screen");
                    scanner.nextLine();
                    System.out.println("Coming back to main screen...");
                case 6:
                    if (cart == null) {
                        System.out.println("You cannot order with no products in your cart, please choose some products to order");
                        break;
                    } else {
                        String currentMembership = currentCustomer.getMembership();
                        long oldTotalSpend = currentCustomer.totalSpend();
                        double currentDiscount = 0;
                        if (currentMembership.equals("Silver")) {
                            currentDiscount = 0.05;
                        } else if (currentMembership.equals("Gold")) {
                            currentDiscount = 0.1;
                        } else if (currentMembership.equals("Platinum")) {
                            currentDiscount = 0.15;
                        }
                        Order order = new Order (currentCustomer.getId(), cart, currentDiscount);
                        Method.writeOrderToDatabase(order, fileOrder);
                        System.out.printf("Your order are placed. The order detail is: %s", order.toString());
                        long newTotalSpend = currentCustomer.totalSpend();
                        if (newTotalSpend >= 25000000 && oldTotalSpend < 25000000) {
                            System.out.println("You have reach 25.000.000 VND in our store. Congratulations, you are now our Platinum Customer with a discount of 15%!");
                        } else if (newTotalSpend >= 10000000 && oldTotalSpend < 10000000) {
                            System.out.println("You have reach 10.000.000 VND in our store. Congratulations, you are now our Gold Customer with a discount of 10%!");
                        } else if (newTotalSpend >= 5000000 && oldTotalSpend < 5000000) {
                            System.out.println("You have reach 5.000.000 VND in our store. Congratulations, you are now our Silver Customer with a discount of 5%!");
                        }
                    }
                    System.out.println("Press enter to come back to main screen");
                    scanner.nextLine();
                    System.out.println("Coming back to main screen...");
                    break;
                case 7:
                    System.out.println("Please input the order id or press 0 if you wish to back to main menu:");
                    String orderFindingId = scanner.nextLine();
                    if (orderFindingId.equals("0")) {

                        System.out.println("Coming back to main screen... ");
                        break;
                    }
                    Order orderRequested = Method.findOrderById(orderFindingId, currentCustomer);
                    if (orderRequested == null) {
                        System.out.printf("There is no order with id %s. \n", orderFindingId);
                    } else {
                        System.out.printf("The order with the id %s is: \n%s \n", orderFindingId, orderRequested);
                    }
                    System.out.println("Press enter to come back to main screen");
                    scanner.nextLine();
                    System.out.println("Coming back to main screen...");
                    break;
                case 8:
                    System.out.println("Orders will be display as \"Id - Cart - Customer ID - Date - Status - Total price");
                    System.out.println("All orders has been made by you is:");
                    List<Order> orders = Order.findOrdersByCustomerId(currentCustomer.getId());
                    for (Order order : orders) {
                        String id = order.getId();
                        Cart carT = order.getCart();
                        String customerID = order.getCustomer();
                        Date date = order.getDate();
                        String status = order.getStatus();
                        System.out.println(id + " - " + carT + " - " + customerID + " - " + date + " - " + status + " - " + order.totalPrice());
                    }
                    System.out.println("Press enter to come back to main screen");
                    scanner.nextLine();
                    System.out.println("Coming back to main screen... ");
                    break;
                case 9:
                    System.out.println("Do you want to sign out? yes/no");
                    String signOutCheck = scanner.nextLine();
                    if (signOutCheck.equals("yes")) {
                        isLogged = false;
                        System.out.println("You have logged out!");
                    } else {
                        System.out.println("Press enter to come back to main screen");
                        scanner.nextLine();
                        System.out.println("Coming back to main screen... ");
                    }
                    break;

            }
        }

        while (isLoggedAdmin) {

        }
    }
}

