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
                    } else {
                        currentCustomer = (Customer) currentAccount;

                        System.out.printf("Logged in, welcome %s to Order Management System\n", currentCustomer.getUsername());
                        isLogged = true;
                    }
                    break;
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
                    "\n\tChoose \"9\" if you want to sign out!\n");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Your current information is:");
                    System.out.println(currentAccount);
                    System.out.printf("Do you want to change your personal information (including name, phone number, email and your address) or you want to change your password? \nSelect (1) to change your information and (2) for password. \nIf you not wish to change anything ,please press (3).\n");
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
                    if (products.equals("")) {
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
            choice = 0;

            System.out.printf("Please choose the action:" +
                    "\n\tChoose \"1\" if you want to view lists of customers" +
                    "\n\tChoose \"2\" if you want to remove a customer" +
                    "\n\tChoose \"3\" if you want to get information of all orders by a customer" +
                    "\n\tChoose \"4\" if you want to view lists of products" + // From this, make option to add/remove products, update products
                    "\n\tChoose \"5\" if you want to add a products" +
                    "\n\tChoose \"6\" if you want to delete a products" + // by id or by name
                    "\n\tChoose \"7\" if you want to change the price of a product" +
                    "\n\tChoose \"8\" if you want to view lists of orders" +
                    "\n\tChoose \"9\" if you want to change the status of an order" +
                    "\n\tChoose \"9\" if you want to check the information of all orders executed in a day" +
                    "\n\tChoose \"10\" if you want to do some statistics operators" +
                    "\n\tChoose \"11\" if you want to see lists of category" +
                    "\n\tChoose \"12\" if you want to add a category" +
                    "\n\tChoose \"13\" if you want to remove a category" +
                    "\n\tChoose \"14\" if you want to sign out!\n");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Customers are displays as \"Id - username - password - full name - phone number - email - address - membership");
                    System.out.println("List of customers: ");
                    List<Customer> customers = Customer.listCustomers();
                    for (Customer customer : customers) {
                        String id = customer.getId();
                        String username = customer.getUsername();
                        String password = customer.getPassword();
                        String fullName = customer.getFullName();
                        String phoneNumber = customer.getPhoneNumber();
                        String email = customer.getEmail();
                        String address = customer.getAddress();
                        String membership = customer.getMembership();
                        System.out.println(id + " - " + username + " " + password + " - " + fullName + " - " + phoneNumber + " - " + email + " - " + address + " - " + membership);
                    }
                    System.out.println("Press enter to come back to main screen");
                    scanner.nextLine();
                    System.out.println("Coming back to main screen... ");
                    break;
                case 2:
                    System.out.printf("Do you want to:" +
                            "\n\t(1) Remove a customer by ID" +
                            "\n\t(2) Remove a customer by username" +
                            "\n\t(3) Go back to main menu.");
                    choiceInfo = scanner.nextLine();
                    if (choiceInfo.equals("1")) {
                        System.out.println("Please input the id of the customer");
                        String customerIdRemoveRequest = scanner.nextLine();
                        Method.removeById(customerIdRemoveRequest, fileAccount);
                        System.out.printf("Account of the customer with id %s is removed successfully!\n", customerIdRemoveRequest);
                    } else if (choiceInfo.equals("2")) {
                        System.out.println("Please input the username of the customer");
                        String customerNameRemoveRequest = scanner.nextLine();
                        Method.removeByName(customerNameRemoveRequest, fileAccount);
                        System.out.printf("Account of the customer with username %s is removed successfully!\n", customerNameRemoveRequest);
                    } else {
                        break;
                    }
                    System.out.println("Press enter to come back to main screen");
                    scanner.nextLine();
                    System.out.println("Coming back to main screen... ");
                    break;
                case 3:
                    System.out.println("Please input the id of the customer you would like to check");
                    String customerIdRequest = scanner.nextLine();
                    System.out.println("Orders will be display as \"Id - Cart - Customer ID - Date - Status - Total price");
                    System.out.printf("All orders has been made by the customer with id %s are:", customerIdRequest);
                    List<Order> orders = Order.findOrdersByCustomerId(customerIdRequest);
                    for (Order order : orders) {
                        String id = order.getId();
                        Cart carT = order.getCart();
                        String customerID = order.getCustomer();
                        Date date = order.getDate();
                        String status = order.getStatus();
                        System.out.println(id + " - " + cart + " - " + customerID + " - " + date + " - " + status + " - " + order.totalPrice());
                    }
                    System.out.println("Press enter to come back to main screen");
                    scanner.nextLine();
                    System.out.println("Coming back to main screen... ");
                    break;
                case 4:
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
                    if (products.equals("")) {
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

                    System.out.printf("Do you want to:" +
                            "\n\t (1) Add a product" +
                            "\n\t (2) Remove a product" +
                            "\n\t (3) Update the price of a product" +
                            "\n\t (4) Back to main menu\n");
                    String choiceMinor = scanner.nextLine();
                    if (choiceMinor.equals("1")) {
                        Product.addProduct(fileProduct);
                    } else if (choiceMinor.equals("2")) {
                        System.out.printf("Do you want to remove a product by" +
                                "\n\t (1) Id" +
                                "\n\t (2) Name" +
                                "\n\t (3) Back to main menu");
                        String removeChoice = scanner.nextLine();
                        if (removeChoice.equals("1")) {
                            System.out.println("Please input the id of the product");
                            String productIdRequest = scanner.nextLine();
                            Method.removeById(productIdRequest, fileProduct);
                            System.out.printf("Product with id %s is removed!", productIdRequest);
                        } else if (removeChoice.equals("2")) {
                            System.out.println("Please input the name of the product");
                            String productNameRequest = scanner.nextLine();
                            Method.removeByName(productNameRequest, fileProduct);
                            System.out.printf("Product with id %s is removed!", productNameRequest);
                        } else {
                            break;
                        }
                    } else if (choiceMinor.equals("3")) {
                        System.out.printf("Do you want to change a product price by" +
                                "\n\t (1) Id" +
                                "\n\t (2) Name" +
                                "\n\t (3) Back to main menu");
                        String changeChoice = scanner.nextLine();
                        Product productNew = null;
                        if (changeChoice.equals("1")) {
                            System.out.println("Please input the id of the product");
                            String productIdRequest = scanner.nextLine();
                            productNew = Product.findProductById(productIdRequest);
                            System.out.printf("You have choose product id %s, name %s with the price %s. Please input the new price\n", productNew.getId(), productNew.getProductName(), productNew.getPrice());
                            int newPrice = scanner.nextInt();
                            scanner.nextLine();
                            Product.changePriceById(productIdRequest,newPrice);
                            System.out.printf("Product with id %s is has change the price from %s to %s!", productIdRequest, productNew.getPrice(),newPrice);
                        } else if (changeChoice.equals("2")) {
                            System.out.println("Please input the name of the product");
                            String productNameRequest = scanner.nextLine();
                            productNew = Product.findProductById(productNameRequest);
                            System.out.printf("You have choose product name %s, id %s with the price %s. Please input the new price\n",productNew.getProductName() , productNew.getId(), productNew.getPrice());
                            int newPrice = scanner.nextInt();
                            scanner.nextLine();
                            Product.changePriceByName(productNameRequest,newPrice);
                            System.out.printf("Product with name %s is has change the price from %s to %s!", productNameRequest, productNew.getPrice(),newPrice);
                        } else {
                            break;
                        }

                    } else {
                        break;
                    }

                    System.out.println("Press enter to come back to main screen");
                    scanner.nextLine();
                    System.out.println("Coming back to main screen...");
                    break;
                case 5:
                    Product.addProduct(fileProduct);
                case 6:
                    System.out.printf("Do you want to remove a product by" +
                            "\n\t (1) Id" +
                            "\n\t (2) Name" +
                            "\n\t (3) Back to main menu");
                    String removeChoice = scanner.nextLine();
                    if (removeChoice.equals("1")) {
                        System.out.println("Please input the id of the product");
                        String productIdRequest = scanner.nextLine();
                        Method.removeById(productIdRequest, fileProduct);
                        System.out.printf("Product with id %s is removed!", productIdRequest);
                    } else if (removeChoice.equals("2")) {
                        System.out.println("Please input the name of the product");
                        String productNameRequest = scanner.nextLine();
                        Method.removeByName(productNameRequest, fileProduct);
                        System.out.printf("Product with id %s is removed!", productNameRequest);
                    } else {
                        break;
                    }
                    System.out.println("Press enter to come back to main screen");
                    scanner.nextLine();
                    System.out.println("Coming back to main screen...");
                    break;
                case 7:
                    System.out.printf("Do you want to change a product price by" +
                            "\n\t (1) Id" +
                            "\n\t (2) Name" +
                            "\n\t (3) Back to main menu");
                    String changeChoice = scanner.nextLine();
                    Product productNew = null;
                    if (changeChoice.equals("1")) {
                        System.out.println("Please input the id of the product");
                        String productIdRequest = scanner.nextLine();
                        productNew = Product.findProductById(productIdRequest);
                        System.out.printf("You have choose product id %s, name %s with the price %s. Please input the new price\n", productNew.getId(), productNew.getProductName(), productNew.getPrice());
                        int newPrice = scanner.nextInt();
                        scanner.nextLine();
                        Product.changePriceById(productIdRequest,newPrice);
                        System.out.printf("Product with id %s is has change the price from %s to %s!", productIdRequest, productNew.getPrice(),newPrice);
                    } else if (changeChoice.equals("2")) {
                        System.out.println("Please input the name of the product");
                        String productNameRequest = scanner.nextLine();
                        productNew = Product.findProductById(productNameRequest);
                        System.out.printf("You have choose product name %s, id %s with the price %s. Please input the new price\n",productNew.getProductName() , productNew.getId(), productNew.getPrice());
                        int newPrice = scanner.nextInt();
                        scanner.nextLine();
                        Product.changePriceByName(productNameRequest,newPrice);
                        System.out.printf("Product with name %s is has change the price from %s to %s!", productNameRequest, productNew.getPrice(),newPrice);
                    } else {
                        break;
                    }
                    System.out.println("Press enter to come back to main screen");
                    scanner.nextLine();
                    System.out.println("Coming back to main screen...");
                    break;
                case 8:
                    System.out.println("List of orders:");
            }
        }
    }
}

