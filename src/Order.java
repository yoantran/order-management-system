import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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


    public static List<Order> listOrders(String customerId, String fileName) throws FileException {
        // Create a list to store the orders
        List<Order> orders = new ArrayList<>();

        // Read the order data from the text file using a BufferedReader
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                // Check if the customer ID matches the given customer ID
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
        } catch (IOException e) {
            throw new FileException("An error occurred while reading the file: " + e.getMessage());
        }

        return orders;
    }

    public static List<Order> listOrders() throws FileException {
        // Create a list to store the orders
        List<Order> orders = new ArrayList<>();

        // Read the order data from the text file using a BufferedReader
        try (BufferedReader reader = new BufferedReader(new FileReader("E:\\Study\\order-management-system\\Data\\order.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                // Parse the date field
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate orderDateTime = LocalDate.parse(fields[3], formatter);

                // Find the cart and customer objects
                Cart cart = new Cart();
                String[] products = fields[2].split("\\|");
                for (int l = 0; l < products.length; l++) {
                    String[] product = products[l].split(";");
                    cart.addProduct(product[0], Integer.parseInt(product[3]), Integer.parseInt(product[2]), product[1]);
                }

                orders.add(new Order(fields[0], fields[1], cart, orderDateTime, Double.parseDouble(fields[4]), fields[5]));
            }
        } catch (IOException e) {
            throw new FileException("An error occurred while reading the file: " + e.getMessage());
        }

        return orders;
    }

    public static List<Order> listOrders(LocalDate date) throws FileException {
        List<Order> orders = new ArrayList<>();
        String filePath = "E:\\Study\\order-management-system\\Data\\order.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate orderDateTime = LocalDate.parse(fields[3], formatter);

                if (date.equals(orderDateTime)) {
                    Cart cart = new Cart();
                    String[] products = fields[2].split("\\|");
                    for (int l = 0; l < products.length; l++) {
                        String[] product = products[l].split(";");
                        cart.addProduct(product[0], Integer.parseInt(product[3]), Integer.parseInt(product[2]), product[1]);
                    }
                    orders.add(new Order(fields[0], fields[1], cart, orderDateTime, Double.parseDouble(fields[4]), fields[5]));
                }
            }
        } catch (IOException e) {
            throw new FileException("An error occurred while reading the file: " + e.getMessage());
        }

        return orders;
    }

    public static void changeOrderStatus(String orderId, String status, String fileName) throws FileException {
        // Create a list to store the lines from the file
        List<String> lines = new ArrayList<>();

        // Read the lines from the file using a BufferedReader
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new FileException("An error occurred while reading the file: " + e.getMessage());
        }

        // Create a new list to store the modified lines
        List<String> modifiedLines = new ArrayList<>();

        // Find the order with the given id and update its status
        for (String line : lines) {
            String[] fields = line.split(",");
            if (orderId.equals(fields[0])) {
                // Update the status field
                fields[5] = status;
                // Concatenate the fields back into a single line and add it to the modified lines list
                modifiedLines.add(String.join(",", fields));
            } else {
                // Add the original line to the modified lines list
                modifiedLines.add(line);
            }
        }

        // Write the modified lines to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String modifiedLine : modifiedLines) {
                writer.write(modifiedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new FileException("An error occurred while writing to the file: " + e.getMessage());
        }
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