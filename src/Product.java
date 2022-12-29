import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Product {
    private String id;
    private final String productName;
    private int price;
    private String category;

    public Product(String productName, int price, String category) throws IOException {
        this.id = Method.generateID("P", "E:\\Study\\order-management-system\\Data\\products.txt");
        this.productName = productName;
        setPrice(price);
        this.category = category;
    }

    public Product(String id, String productName, int price, String category) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.category = category;
    }

    public static void addProduct(String fileName) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the product's name");
        String productName = scanner.nextLine();
        productName = Method.validateEmpty(productName);

        System.out.println("Please input the price");
        int price = scanner.nextInt();
        price = Method.validatePrice(price);
        scanner.nextLine();

        System.out.println("Please input the category of the product");
        String category = scanner.nextLine();
        category = Method.validateCategory(category,fileName);
        Product product = new Product(productName, price, category);
        Method.writeProductToDatabase(product, fileName);
        System.out.println("Product added successfully!");

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrice(int price) {
        price = Method.validatePrice(price);
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public static void removeProductById (String id) throws IOException {
        Method.removeById(id, "E:\\Study\\order-management-system\\Data\\products.txt");
    }

    public static void removeProductByName (String name) throws IOException {
        Method.removeByName(name, "E:\\Study\\order-management-system\\Data\\products.txt");
    }

    public static List<Product> listProducts() throws IOException {
        // Read the category data from the text file
        List<String> lines = Files.readAllLines(Paths.get("E:\\Study\\order-management-system\\Data\\category.txt"));

        // Parse the lines into a list of Category objects
        List<Product> products = new ArrayList<>();
        for (String line : lines) {
            String[] fields = line.split(",");
            products.add(new Product(fields[0], fields[1], parseInt(fields[2]), fields[3]));
        }

        return products;
    }

    public static List<Product> listProductsByCategory(String category, String order, String fileName) throws IOException {
        // Read the product data from the text file
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        // Parse the lines into a list of Product objects
        List<Product> products = new ArrayList<>();
        for (String line : lines) {
            String[] fields = line.split(",");
            if (fields[3].equals(category)) {
                products.add(new Product(fields[0], fields[1], parseInt(fields[2]), fields[3]));
            }
        }

        // Sort the list of products by price
        products.sort((p1, p2) -> {
            if (order.equals("asc")) {
                return p1.getPrice() - p2.getPrice();
            } else {
                return p2.getPrice() - p1.getPrice();
            }
        });

        return products;

//        List<Product> products = Product.listProductsByCategory("Category", "asc", "E:\\Study\\order-management-system\\Data\\products.txt");

//        for (Product product : products) {
//    System.out.println("ID: " + product.getId());
//    System.out.println("Product name: " + product.getProductName());
//    System.out.println("Price: " + product.getPrice());
//    System.out.println("Category: " + product.getCategory());
//}
    }




















}
