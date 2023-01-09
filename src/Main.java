import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException{

        Scanner scanner = new Scanner(System.in);
        String fileAccount = ".\\Data\\account.txt";
        String fileCategory = ".\\Data\\category.txt";
        String fileOrder = ".\\Data\\order.txt";
        String fileProduct = ".\\Data\\products.txt";
        Account currentAccount = null;
        Customer currentCustomer = null;
        Cart cart = new Cart();
        int choice;
        String choiceInfo;
        boolean isLogged = false;
        boolean isLoggedAdmin = false;
        boolean isSystem = true;


        while (isSystem) {


            while (true) {
                System.out.println("Enter 1 to register, 2 to login, or 0 to exit:");
                int option = scanner.nextInt();
                if (option == 0) {
                    isSystem = false;
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
                            System.out.println("Admin logged in, welcome to Order Management System");
                            isLoggedAdmin = true;
                        } else if (!currentAccount.isAdmin()) {
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

                System.out.print("Please choose the action:" +
                        "\n\t- (1)  Check or update your information" +
                        "\n\t- (2)  Check your current membership status" +
                        "\n\t- (3)  List all products" +
                        "\n\t- (4)  Search for a product" +
                        "\n\t- (5)  Check on your cart" +
                        "\n\t- (6)  Add product to your cart" +
                        "\n\t- (7)  Remove a product in your cart" +
                        "\n\t- (8)  Reset your cart" +
                        "\n\t- (9)  Place your order" +
                        "\n\t- (10) Get information about a particular order" +
                        "\n\t- (11) Get information of all orders made by you" +
                        "\n\t- (12) Sign out!\n");
                choice = scanner.nextInt();
                scanner.nextLine();
                label:
                switch (choice) {
                    case 1:
                        System.out.println("Your current information is:");
                        System.out.println(currentAccount);
                        System.out.print("Do you want to change your personal information (including name, phone number, email and your address) or you want to change your password? \nSelect (1) to change your information and (2) for password. \nIf you not wish to change anything ,please press (3).\n");
                        choiceInfo = scanner.nextLine();
                        if (choiceInfo.equals("1")) {
                            Customer.updateCustomerInformation((Customer) currentAccount);
                        } else if (choiceInfo.equals("2")) {
                            Customer.updatePassword((Customer) currentAccount);
                        }
                        System.out.println("Press enter to come back to main screen");
                        scanner.nextLine();
                        System.out.println("Coming back to main screen...");
                        break;
                    case 2:
                        String membership = currentCustomer.getMembership();
                        System.out.printf("Your current membership is %s.\n", membership);
                        switch (membership) {
                            case "Regular":
                                System.out.println("You don't have any discount.");
                                break;
                            case "Silver":
                                System.out.println("You have a discount of 5%.");
                                break;
                            case "Gold":
                                System.out.println("You have a discount of 10%.");
                                break;
                            case "Platinum":
                                System.out.println("You have a discount of 15%.");
                                break;
                            default:
                                System.out.println("Invalid membership, please contact the admin for verification!");
                                break;
                        }

                        System.out.println("Press enter to come back to main screen");
                        scanner.nextLine();
                        System.out.println("Coming back to main screen...");

                        break;
                    case 3:
                        System.out.print("Do you want to\n\t- (1) List all products\n\t- (2) List all products in a category with pricing order (asc or des)" +
                                "\n\t- (3) List all products in a category with a price range" +
                                "\n\t- (4) Go back to main menu.\n");
                        choiceInfo = scanner.nextLine();

                        List<Product> products;
                        switch (choiceInfo) {
                            case "1":
                                products = Product.listProducts();
                                break;
                            case "2": {
                                System.out.println("Please input the category name:");
                                String categoryListingName = scanner.nextLine();
                                System.out.println("Please input the pricing order: asc/des");
                                String listingOrder = scanner.nextLine();
                                products = Product.listProductsByCategory(categoryListingName, listingOrder, fileProduct);
                                break;
                            }
                            case "3": {
                                System.out.println("Please input the category name:");
                                String categoryListingName = scanner.nextLine();
                                System.out.println("Please input the min price:");
                                int minListingPrice = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println("Please input the max price:");
                                int maxListingPrice = scanner.nextInt();
                                scanner.nextLine();
                                products = Product.listProductsInPriceRangeAndCategory(minListingPrice, maxListingPrice, categoryListingName);
                                break;
                            }
                            default:
                                System.out.println("Coming back to main screen...");
                                break label;
                        }

                        if (products.isEmpty()) {
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
                                System.out.printf("The product with id %s is:\n %s\n", productIdFinding, productResult);
                            }
                        } else if (choiceInfo.equals("2")) {
                            System.out.println("Please input the product name:");
                            String productNameFinding = scanner.nextLine();
                            Product productResult = Product.findProductByName(productNameFinding);
                            if (productResult == null) {
                                System.out.printf("There is no product with the name %s\n", productNameFinding);
                            } else {
                                System.out.printf("The product with the name %s is:\n %s\n", productNameFinding, productResult);
                            }
                        } else {
                            break;
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
                        }
                        System.out.println("Press enter to come back to main screen");
                        scanner.nextLine();
                        System.out.println("Coming back to main screen...");

                    case 6:
                        System.out.println("Do you want to add product by\n\t- (1) Id\n\t- (2) Name\n\t- (3) Go back to main menu.");
                        choiceInfo = scanner.nextLine();
                        Product productResult = null;
                        if (choiceInfo.equals("1")) {
                            System.out.println("Please input the product id:");
                            String productIdFinding = scanner.nextLine();
                            productResult = Product.findProductById(productIdFinding);
                            if (productResult == null) {
                                System.out.printf("There is no product with id %s.\n", productIdFinding);
                            } else {
                                System.out.printf("The product with id %s is:\n %s\n", productIdFinding, productResult);
                            }
                        } else if (choiceInfo.equals("2")) {
                            System.out.println("Please input the product name:");
                            String productNameFinding = scanner.nextLine();
                            productResult = Product.findProductByName(productNameFinding);
                            if (productResult == null) {
                                System.out.printf("There is no product with the name %s\n", productNameFinding);
                            } else {
                                System.out.printf("The product with the name %s is:\n %s\n", productNameFinding, productResult);
                            }
                        } else {
                            break;
                        }
                        System.out.println("Please input the amount of product you want to order!");
                        int amount = scanner.nextInt();
                        scanner.nextLine();
                        assert productResult != null;
                        cart.addProduct(productResult.getId(), amount);
                        System.out.println("Product added to cart!");
                        System.out.println("Your current cart is:");
                        System.out.println(cart);
                        System.out.println("The total price of your cart is:");
                        System.out.println(cart.getTotalAmount());
                        System.out.println("Press enter to come back to main screen");
                        scanner.nextLine();
                        System.out.println("Coming back to main screen...");
                        break;

                    case 7:
                        System.out.println("Do you want to remove product by\n\t- (1) Id\n\t- (2) Name\n\t- (3) Go back to main menu.");
                        choiceInfo = scanner.nextLine();
                        Product productRemoveResult;
                        if (choiceInfo.equals("1")) {
                            System.out.println("Please input the product id:");
                            String productIdFinding = scanner.nextLine();
                            productRemoveResult = Product.findProductById(productIdFinding);
                            if (productRemoveResult == null) {
                                System.out.printf("There is no product with id %s.\n", productIdFinding);
                            } else {
                                System.out.printf("The product with id %s is:\n %s\n", productIdFinding, productRemoveResult);
                            }
                        } else if (choiceInfo.equals("2")) {
                            System.out.println("Please input the product name:");
                            String productNameFinding = scanner.nextLine();
                            productRemoveResult = Product.findProductByName(productNameFinding);
                            if (productRemoveResult == null) {
                                System.out.printf("There is no product with the name %s\n", productNameFinding);
                            } else {
                                System.out.printf("The product with the name %s is:\n %s\n", productNameFinding, productRemoveResult);
                            }
                        } else {
                            break;
                        }

                        cart.deleteProduct(productRemoveResult);
                        System.out.println("Product removed from cart!");
                        System.out.println("Your current cart is:");
                        System.out.println(cart);
                        System.out.println("The total price of your cart is:");
                        System.out.println(cart.getTotalAmount());
                        System.out.println("Press enter to come back to main screen");
                        scanner.nextLine();
                        System.out.println("Coming back to main screen...");
                        break;

                    case 8:
                        System.out.println("Do you really want to reset your cart? yes/no");
                        String resetCartCheck = scanner.nextLine();
                        if (resetCartCheck.equals("yes")) {
                            cart.reset();
                            System.out.println("Cart reset successfully!");
                        } else {
                            System.out.println("Press enter to come back to main screen");
                            scanner.nextLine();
                            System.out.println("Coming back to main screen... ");
                        }
                        break;

                    case 9:
                        if (cart == null) {
                            System.out.println("You cannot order with no products in your cart, please choose some products to order");
                            break;
                        } else {
                            String currentMembership = currentCustomer.getMembership();
                            double oldTotalSpend = currentCustomer.totalSpend();
                            double currentDiscount = 1;
                            switch (currentMembership) {
                                case "Silver":
                                    currentDiscount -= 0.05;
                                    break;
                                case "Gold":
                                    currentDiscount -= 0.1;
                                    break;
                                case "Platinum":
                                    currentDiscount -= 0.15;
                                    break;
                            }
                            Order order = new Order (currentCustomer.getId(), cart, currentDiscount);
                            Order.writeOrderToDatabase(order, fileOrder);
                            System.out.printf("Your order are placed. The order detail is: %s\n", order.toString());
                            double newTotalSpend = currentCustomer.totalSpend();
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
                    case 10:
                        System.out.println("Please input the order id or press 0 if you wish to back to main menu:");
                        String orderFindingId = scanner.nextLine();
                        if (orderFindingId.equals("0")) {

                            System.out.println("Coming back to main screen... ");
                            break;
                        }
                        Order orderRequested = Order.findOrderById(orderFindingId, currentCustomer);
                        if (!(orderRequested == null)) {
                            System.out.printf("The order with the id %s is: \n%s \n", orderFindingId, orderRequested);
                        }
                        System.out.println("Press enter to come back to main screen");
                        scanner.nextLine();
                        System.out.println("Coming back to main screen...");
                        break;
                    case 11:
                        System.out.println("Orders will be display as \"Id - Cart - Customer ID - Date - Status - Total price");
                        System.out.println("All orders has been made by you is:");
                        List<Order> orders = null;
                        try {
                            orders = Order.listOrders(currentCustomer.getId(), fileOrder);;
                        } catch (FileException e) {
                            System.out.println(e.getMessage());
                        }
                        Method.printOrders(orders);
                        System.out.println("Press enter to come back to main screen");
                        scanner.nextLine();
                        System.out.println("Coming back to main screen... ");
                        break;
                    case 12:
                        System.out.println("Do you want to sign out? yes/no");
                        String signOutCheck = scanner.nextLine();
                        if (signOutCheck.equals("yes")) {
                            isLogged = false;
                            System.out.println("You have logged out!");
                            currentAccount = null;
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

                System.out.print("Please choose the action:" +
                        "\n\t- (1)  View lists of customers" +
                        "\n\t- (2)  Remove a customer" +
                        "\n\t- (3)  Get information of all orders by a customer" +
                        "\n\t- (4)  View lists of products" + // From this, make option to add/remove products, update products
                        "\n\t- (5)  Add a products" +
                        "\n\t- (6)  Delete a products" + // by id or by name
                        "\n\t- (7)  Change the price of a product" +
                        "\n\t- (8)  View lists of orders" +
                        "\n\t- (9)  Change the status of an order" +
                        "\n\t- (10) Check the information of all orders executed in a day" +
                        "\n\t- (11) Do some statistics operators" +
                        "\n\t- (12) See lists of category" +
                        "\n\t- (13) Add a category" +
                        "\n\t- (14) Remove a category" +
                        "\n\t- (15) Sign out!\n");
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
                                "\n\t(3) Go back to main menu.\n");
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
                        System.out.printf("All orders has been made by the customer with id %s are:\n", customerIdRequest);
                        List<Order> orders = null;
                        try {
                            orders = Order.listOrders(customerIdRequest, fileOrder);
                        } catch (FileException e) {
                            System.out.println(e.getMessage());
                        }
                        Method.printOrders(orders);
                        System.out.println("Press enter to come back to main screen");
                        scanner.nextLine();
                        System.out.println("Coming back to main screen... ");
                        break;
                    case 4:
                        System.out.print("Do you want to\n\t- (1) List all products\n\t- (2) List all products in a category with pricing order (asc or des)" +
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
                        }
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
                                    "\n\t (3) Back to main menu\n");
                            String removeChoice = scanner.nextLine();
                            if (removeChoice.equals("1")) {
                                System.out.println("Please input the id of the product");
                                String productIdRequest = scanner.nextLine();
                                Method.removeById(productIdRequest, fileProduct);
                                System.out.printf("Product with id %s is removed!\n", productIdRequest);
                            } else if (removeChoice.equals("2")) {
                                System.out.println("Please input the name of the product");
                                String productNameRequest = scanner.nextLine();
                                Method.removeByName(productNameRequest, fileProduct);
                                System.out.printf("Product with id %s is removed!\n", productNameRequest);
                            } else {
                                break;
                            }
                        } else if (choiceMinor.equals("3")) {
                            System.out.printf("Do you want to change a product price by" +
                                    "\n\t (1) Id" +
                                    "\n\t (2) Name" +
                                    "\n\t (3) Back to main menu\n");
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
                                System.out.printf("Product with id %s is has change the price from %s to %s!\n", productIdRequest, productNew.getPrice(),newPrice);
                            } else if (changeChoice.equals("2")) {
                                System.out.println("Please input the name of the product");
                                String productNameRequest = scanner.nextLine();
                                productNew = Product.findProductById(productNameRequest);
                                System.out.printf("You have choose product name %s, id %s with the price %s. Please input the new price\n",productNew.getProductName() , productNew.getId(), productNew.getPrice());
                                int newPrice = scanner.nextInt();
                                scanner.nextLine();
                                Product.changePriceByName(productNameRequest,newPrice);
                                System.out.printf("Product with name %s is has change the price from %s to %s!\n", productNameRequest, productNew.getPrice(),newPrice);
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
                        break;
                    case 6:
                        System.out.printf("Do you want to remove a product by" +
                                "\n\t (1) Id" +
                                "\n\t (2) Name" +
                                "\n\t (3) Back to main menu\n");
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
                                "\n\t (3) Back to main menu\n");
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
                            System.out.printf("Product with id %s is has change the price from %s to %s!\n", productIdRequest, productNew.getPrice(),newPrice);
                        } else if (changeChoice.equals("2")) {
                            System.out.println("Please input the name of the product");
                            String productNameRequest = scanner.nextLine();
                            productNew = Product.findProductById(productNameRequest);
                            System.out.printf("You have choose product name %s, id %s with the price %s. Please input the new price\n",productNew.getProductName() , productNew.getId(), productNew.getPrice());
                            int newPrice = scanner.nextInt();
                            scanner.nextLine();
                            Product.changePriceByName(productNameRequest,newPrice);
                            System.out.printf("Product with name %s is has change the price from %s to %s!\n", productNameRequest, productNew.getPrice(),newPrice);
                        } else {
                            break;
                        }
                        System.out.println("Press enter to come back to main screen");
                        scanner.nextLine();
                        System.out.println("Coming back to main screen...");
                        break;
                    case 8:
                        System.out.println("List of orders:");
                        try {
                            // Call the listOrders method
                            orders = Order.listOrders();

                            // Print the list of orders to the console
                            Method.printOrders(orders);

                        } catch (FileException e) {
                            System.out.println(e.getMessage());
                        }
                        System.out.println("Press enter to come back to main screen");
                        scanner.nextLine();
                        System.out.println("Coming back to main screen... ");
                        break;
                    case 9:
//                        Change the status of an order
                        System.out.println("Please input the order ID to change status:");
                        String orderID = scanner.nextLine();
                        try {
                            // Check if the order ID is in the correct format
                            if (!orderID.matches("O\\d+")) {
                                throw new IllegalArgumentException("Invalid order ID format");
                            }

                            try {
                                Order.changeOrderStatus(orderID, "PAID", fileOrder);
                            } catch (FileException e) {
                                System.out.println(e.getMessage());
                            }

                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                        System.out.printf("Order with id %s was approved! The status has been changed to \"PAID\"\n", orderID);
                        System.out.println("Press enter to come back to main screen");
                        scanner.nextLine();
                        System.out.println("Coming back to main screen... ");
                        break;
                    case 10:
                        System.out.println("Please input the year:");
                        int dateCheckYear = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Please input the month:");
                        int dateCheckMonth = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Please input the day:");
                        int dateCheckDay = scanner.nextInt();
                        scanner.nextLine();
                        LocalDate date = LocalDate.of(dateCheckYear, dateCheckMonth, dateCheckDay);
                        try {
                            orders = Order.listOrders(date);  // Get the list of orders for the specified date
                            System.out.printf("All orders on %s %s, %s is:\n", dateCheckDay, dateCheckMonth, dateCheckYear);
                            Method.printOrders(orders);
                        } catch (FileException e) {
                            System.out.println("Error: " + e.getMessage());
                            return;
                        }
                        System.out.println("Press enter to come back to main screen");
                        scanner.nextLine();
                        System.out.println("Coming back to main screen... ");
                        break;
                    case 11:
                        System.out.printf("This is the admin Dashboard. Do you want to" +
                                "\n\t (1) Calculate the store total revenue" +
                                "\n\t (2) Calculate the store revenue on a day" +
                                "\n\t (3) Name the current most popular product" +
                                "\n\t (4) Name the current least popular product" +
                                "\n\t (5) Name the customer pays the most in the store" +
                                "\n\t (6) List out the numbers of different types of membership" +
                                "\n\t (7) Back to main menu\n");
                        changeChoice = scanner.nextLine();
                        if (changeChoice.equals("1")) {
                            // Calculate the store total revenue
                            try {
                                // Call the listOrders method
                                orders = Order.listOrders();
                                double totalRevenue = Admin.calculateTotalRevenue(orders);
                                System.out.printf("The store total revenue is %.2f\n", totalRevenue);

                            } catch (FileException e) {
                                System.out.println(e.getMessage());
                            }
                        } else if (changeChoice.equals("2")) {
                            // Calculate the store revenue on a day

                            System.out.println("Please input the year:");
                            dateCheckYear = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Please input the month:");
                            dateCheckMonth = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Please input the day:");
                            dateCheckDay = scanner.nextInt();
                            scanner.nextLine();
                            date = LocalDate.of(dateCheckYear, dateCheckMonth, dateCheckDay);
                            try {
                                orders = Order.listOrders(date);  // Get the list of orders for the specified date
                                double totalRevenue = Admin.calculateTotalRevenue(orders);
                                System.out.printf("The store total revenue on %s/%s/%s is %.2f\n",dateCheckDay, dateCheckMonth, dateCheckYear, totalRevenue);
                            } catch (FileException e) {
                                System.out.println("Error: " + e.getMessage());
                                return;
                            }

                        } else if (changeChoice.equals("3")) {
                            // Name the current most popular product
                            try {
                                // Call the listOrders method
                                orders = Order.listOrders();
                                Admin.findMostPopularProduct(orders);

                            } catch (FileException e) {
                                System.out.println(e.getMessage());
                            }

                        } else if (changeChoice.equals("4")) {
                            // Name the current least popular product

                            try {
                                // Call the listOrders method
                                orders = Order.listOrders();
                                Admin.findLeastPopularProduct(orders);

                            } catch (FileException e) {
                                System.out.println(e.getMessage());
                            }
                        } else if (changeChoice.equals("5")) {
                            // Name the customer pays the most in the store
                            try {
                                // Call the listOrders method
                                orders = Order.listOrders();
                                Admin.findBiggestSpender(orders);

                            } catch (FileException e) {
                                System.out.println(e.getMessage());
                            }
                        } else if (changeChoice.equals("6")) {
                            // List out the numbers of different types of membership
                            customers = Customer.listCustomers();
                            Admin.countMembership(customers);

                        } else {
                            System.out.println("Thank you!");
                        }

                        System.out.println("Press enter to come back to main screen");
                        scanner.nextLine();
                        System.out.println("Coming back to main screen... ");
                        break;


                    case 12:
                        System.out.println("List of all categories is presents as \"ID - Name\"");
                        List<Category> categories = Category.listCategories();
                        for (Category category : categories) {
                            System.out.println(category);
                        }
                        System.out.println("Press enter to come back to main screen");
                        scanner.nextLine();
                        System.out.println("Coming back to main screen... ");
                        break;
                    case 13:
                        Category.addCategory(fileCategory);
                        System.out.println("Press enter to come back to main screen");
                        System.out.println("Coming back to main screen... ");
                        break;
                    case 14:
                        System.out.println("Please input the name of the category");
                        String categoryNameRequest = scanner.nextLine();
                        Category.removeCategoryByName(categoryNameRequest);
                        System.out.printf("The category %s has been removed. All products with category %s are no longer belong to a category!\n", categoryNameRequest, categoryNameRequest);
                        System.out.println("Press enter to come back to main screen");
                        System.out.println("Coming back to main screen... ");
                        break;
                    case 15:
                        System.out.println("Do you want to sign out? yes/no");
                        String signOutCheck = scanner.nextLine();
                        if (signOutCheck.equals("yes")) {
                            isLoggedAdmin = false;
                            System.out.println("You have logged out!");
                            currentAccount = null;

                        } else {
                            System.out.println("Press enter to come back to main screen");
                            scanner.nextLine();
                            System.out.println("Coming back to main screen... ");
                        }
                        break;
                }
            }
        }

    }
}