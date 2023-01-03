import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private final String id;
    private final String customer;
    private final Cart cart;

    private final LocalDateTime dateTime;
    private final double discount;
    private String status;

    public Order(String customer, Cart cart, double discount) throws IOException {
        this.id = Method.generateID("O", "E:\\Study\\order-management-system\\Data\\order.txt");
        this.customer = customer;
        this.cart = cart;
        this.dateTime = LocalDateTime.now();

        this.status = "Pending";
        this.discount = discount;
    }

    public Order(String id, String customer, Cart cart, String status, int discount) {
        this.id = id;
        this.customer = customer;
        this.cart = cart;
        this.dateTime = LocalDateTime.now();
        this.status = status;
        this.discount = discount;
    }

    public Order(String id, String customer, Cart cart, LocalDateTime date, double discount, String status) {
        this.id = id;
        this.customer = customer;
        this.cart = cart;
        this.dateTime = LocalDateTime.now();
        this.discount = discount;
        this.status = status;
    }

    public static void placeOrder(String fileName, Order order) {
        Method.writeOrderToDatabase(order, fileName);
        System.out.println("Order placed!");
    }



    public String getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public Cart getCart() {
        return cart;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatusDone() {
        this.status = "Done";
    }

    public double getDiscount() {
        return discount;
    }

    public static Order findOrderById(String id, Customer customerRequest) throws IOException {



        // Read the order data from the text file
        List<String> lines = Files.readAllLines(Paths.get("E:\\Study\\order-management-system\\Data\\orders.txt"));

        // Find the order with the given id
        for (String line : lines) {
            String[] fields = line.split(",");
            if (id.equals(fields[0])) {
                // Parse the date field
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime orderDateTime = LocalDateTime.parse(fields[3], formatter);
                Cart cart = null;
                // Find the cart and customer objects
                String[] products = fields[2].split("\\|");
                for (int l = 0; l < products.length; l++) {
                    String[] product = products[l].split(";");
                    cart.addProduct(Product.findProductById(product[0]), Integer.parseInt(product[2]));
                }
                if (!customerRequest.equals(fields[1])) {
                    System.out.println("You don't have permission to perform this action!");
                    return null;
                }
                return new Order(fields[0], fields[1], cart, orderDateTime, Double.parseDouble(fields[4]), fields[5]);
            }
        }

        return null;
    }


    public static List<Order> findOrdersByCustomerId(String customerId) throws IOException, ParseException {
        // Read the order data from the text file
        List<String> lines = Files.readAllLines(Paths.get("E:\\Study\\order-management-system\\Data\\orders.txt"));

        // Find the orders made by the customer with the given id
        List<Order> orders = new ArrayList<>();
        for (String line : lines) {
            String[] fields = line.split(",");
            if (customerId.equals(fields[2])) {
                // Parse the date field
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime orderDateTime = LocalDateTime.parse(fields[3], formatter);


                // Find the cart and customer objects
                Cart cart = null;
                // Find the cart and customer objects
                String[] products = fields[2].split("\\|");
                for (int l = 0; l < products.length; l++) {
                    String[] product = products[l].split(";");
                    cart.addProduct(Product.findProductById(product[0]), Integer.parseInt(product[2]));
                }

                orders.add(new Order(fields[0], fields[1], cart, orderDateTime, Double.parseDouble(fields[4]), fields[5]));
            }
        }

        return orders;
    }

    public static List<Order> listOrders() throws IOException, ParseException {
        // Read the order data from the text file
        List<String> lines = Files.readAllLines(Paths.get("E:\\Study\\order-management-system\\Data\\orders.txt"));

        // Parse the lines into a list of Order objects
        List<Order> orders = new ArrayList<>();
        for (String line : lines) {
            String[] fields = line.split(",");
            // Parse the date field
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime orderDateTime = LocalDateTime.parse(fields[3], formatter);

            // Find the cart and customer objects
            Cart cart = null;
            // Find the cart and customer objects
            String[] products = fields[2].split("\\|");
            for (int l = 0; l < products.length; l++) {
                String[] product = products[l].split(";");
                cart.addProduct(Product.findProductById(product[0]), Integer.parseInt(product[2]));
            }
            orders.add(new Order(fields[0], fields[1], cart, orderDateTime, Double.parseDouble(fields[4]), fields[5]));

        }

        return orders;
    }

    public static List<Order> findOrdersByDate(LocalDate date) throws IOException {
        // Read the order data from the text file
        List<String> lines = Files.readAllLines(Paths.get("E:\\Study\\order-management-system\\Data\\orders.txt"));

        // Parse the lines into a list of Order objects
        List<Order> orders = new ArrayList<>();
        for (String line : lines) {
            String[] fields = line.split(",");
            // Parse the date field
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime orderDateTime = LocalDateTime.parse(fields[3], formatter);
            LocalDate orderDate = orderDateTime.toLocalDate();

            // Only add the order if it was made on the specified date
            if (date.equals(orderDate)) {
                // Find the cart and customer objects
                Cart cart = null;
                // Find the cart and customer objects
                String[] products = fields[2].split("\\|");
                for (int l = 0; l < products.length; l++) {
                    String[] product = products[l].split(";");
                    cart.addProduct(Product.findProductById(product[0]), Integer.parseInt(product[2]));
                }
                orders.add(new Order(fields[0], fields[1], cart, orderDateTime, Double.parseDouble(fields[4]), fields[5]));
            }
        }

        return orders;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", customer='" + customer + '\'' +
                ", cart=" + cart +
                ", date=" + dateTime +
                ", discount=" + discount +
                ", totalPrice=" + totalPrice() +
                ", status='" + status + '\'' +
                '}';
    }

    public double totalPrice () {
        return this.cart.getTotalAmount() * this.discount;
    }


}