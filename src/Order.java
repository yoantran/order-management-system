import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
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

    private final LocalDate dateTime;
    private final double discount;
    private String status;

    public Order(String customer, Cart cart, double discount) throws IOException {
        this.id = Method.generateID("O", "E:\\Study\\order-management-system\\Data\\order.txt");
        this.customer = customer;
        this.cart = cart;
        this.dateTime = LocalDate.now();

        this.status = "Pending";
        this.discount = discount;
    }

    public Order(String id, String customer, Cart cart, String status, int discount) {
        this.id = id;
        this.customer = customer;
        this.cart = cart;
        this.dateTime = LocalDate.now();
        this.status = status;
        this.discount = discount;
    }

    public Order(String id, String customer, Cart cart, LocalDate date, double discount, String status) {
        this.id = id;
        this.customer = customer;
        this.cart = cart;
        this.dateTime = date;
        this.discount = discount;
        this.status = status;
    }

    public static void placeOrder(String fileName, Order order) {
        writeOrderToDatabase(order, fileName);
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

    public LocalDate getDateTime() {
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
        List<String> lines = Files.readAllLines(Paths.get("E:\\Study\\order-management-system\\Data\\order.txt"));

        // Find the order with the given id
        for (String line : lines) {
            String[] fields = line.split(",");
            if (id.equals(fields[0])) {
                // Parse the date field
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate orderDateTime = LocalDate.parse(fields[3], formatter);
                Cart cart = new Cart();
                // Find the cart and customer objects
                String[] products = fields[2].split("\\|");
                for (int l = 0; l < products.length; l++) {
                    String[] product = products[l].split(";");
                    cart.addProduct(product[0], Integer.parseInt(product[3]), Integer.parseInt(product[2]), product[1]);
                }
                if (!customerRequest.getId().equals(fields[1])) {
                    System.out.println("You don't have permission to perform this action!");
                    return null;
                }
                return new Order(fields[0], fields[1], cart, orderDateTime, Double.parseDouble(fields[4]), fields[5]);
            }
        }
        System.out.printf("There is no order with id %s\n", id);

        return null;
    }


    public static List<Order> listOrders(String customerId) throws IOException, ParseException {
        // Read the order data from the text file
        List<String> lines = Files.readAllLines(Paths.get("E:\\Study\\order-management-system\\Data\\order.txt"));

        // Find the orders made by the customer with the given id
        List<Order> orders = new ArrayList<>();
        for (String line : lines) {
            String[] fields = line.split(",");
            if (customerId.equals(fields[1])) {
                // Parse the date field
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate orderDateTime = LocalDate.parse(fields[3], formatter);

                Cart cart = new Cart();
                // Find the cart and customer objects
                String[] products = fields[2].split("\\|");
                for (int l = 0; l < products.length; l++) {
                    String[] product = products[l].split(";");
                    cart.addProduct(product[0], Integer.parseInt(product[3]), Integer.parseInt(product[2]), product[1]);

                }

                orders.add(new Order(fields[0], fields[1], cart, orderDateTime, Double.parseDouble(fields[4]), fields[5]));
            }
        }

        return orders;
    }

    public static List<Order> listOrders() throws IOException, ParseException {
        // Read the order data from the text file
        List<String> lines = Files.readAllLines(Paths.get("E:\\Study\\order-management-system\\Data\\order.txt"));

        // Parse the lines into a list of Order objects
        List<Order> orders = new ArrayList<>();
        for (String line : lines) {
            String[] fields = line.split(",");
            // Parse the date field
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate orderDateTime = LocalDate.parse(fields[3], formatter);

            // Find the cart and customer objects
            Cart cart = new Cart();
            // Find the cart and customer objects
            String[] products = fields[2].split("\\|");
            for (int l = 0; l < products.length; l++) {
                String[] product = products[l].split(";");
                cart.addProduct(product[0], Integer.parseInt(product[3]), Integer.parseInt(product[2]), product[1]);

            }
            orders.add(new Order(fields[0], fields[1], cart, orderDateTime, Double.parseDouble(fields[4]), fields[5]));

        }

        return orders;
    }

    public static List<Order> listOrders(LocalDate date) throws IOException {
        // Read the order data from the text file
        List<String> lines = Files.readAllLines(Paths.get("E:\\Study\\order-management-system\\Data\\order.txt"));

        // Parse the lines into a list of Order objects
        List<Order> orders = new ArrayList<>();
        for (String line : lines) {
            String[] fields = line.split(",");
            // Parse the date field
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate orderDateTime = LocalDate.parse(fields[3], formatter);

            // Only add the order if it was made on the specified date
            if (date.equals(orderDateTime)) {
                // Find the cart and customer objects
                Cart cart = new Cart();
                // Find the cart and customer objects
                String[] products = fields[2].split("\\|");
                for (int l = 0; l < products.length; l++) {
                    String[] product = products[l].split(";");
                    cart.addProduct(product[0], Integer.parseInt(product[3]), Integer.parseInt(product[2]), product[1]);

                }
                orders.add(new Order(fields[0], fields[1], cart, orderDateTime, Double.parseDouble(fields[4]), fields[5]));
            }
        }

        return orders;
    }

    public static void writeOrderToDatabase(Order order, String filename) {
        try {
            FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(order.getId() + "," + order.getCustomer() + "," + order.getCart().toString() + "," + order.getDateTime() + "," + order.getDiscount() + "," + order.getStatus() + "\n");

            bw.close(); // close the BufferedWriter object
            fw.close();
        } catch (IOException e) {
            System.out.println("Error writing to database file: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        try {
            return "Order{" +
                    "id='" + id + '\'' +
                    ", customer='" + customer + '\'' +
                    ", cart=" + cart +
                    ", date=" + dateTime +
                    ", discount=" + discount +
                    ", totalPrice=" + totalPrice() +
                    ", status='" + status + '\'' +
                    '}';
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double totalPrice () throws IOException {
        return this.cart.getTotalAmount() * this.discount;
    }


}
