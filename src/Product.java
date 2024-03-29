/*
  RMIT University Vietnam
  Course: COSC2081 Programming 1
  Semester: 2022C
  Assessment: Assignment 3
  Authors:
  Nguyen Nhat Minh
  Luu Quoc Nhat
  Tran Ngoc Hong Doanh
  To Gia Hy
  ID:
  S3932112
  S3924942
  s3927023
  S3927539
  Acknowledgement: None
*/

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Product {
    private final String id;
    private final String productName;
    private int price;
    private String category;

    private static final String fileName = ".\\Data\\products.txt";


    public Product(String productName, int price, String category) throws IOException {
        this.id = Method.generateID("P", fileName, ",");
        this.productName = productName;
        setPrice(String.valueOf(price));
        this.category = category;
    }

    public Product(String id, String productName, int price, String category) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.category = category;
    }

    public Product(String id, String productName, int price) {
        this.id = id;
        this.productName = productName;
        this.price = price;
    }


    public static void addProduct(String fileName) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the product's name");
        String productName = scanner.nextLine();
        productName = Method.validateEmpty(productName);

        System.out.println("Please input the price");
        String priceInput = scanner.nextLine();
        int price = Method.validatePrice(priceInput);

        System.out.println("Please input the category of the product");
        String category = scanner.nextLine();
        category = Category.validateCategory(category, ".\\Data\\category.txt");
        Product product = new Product(productName, price, category);
        writeProductToDatabase(product, fileName);
        System.out.println("Product added successfully!");

    }


    public void setPrice(String priceInput) {
        this.price = Method.validatePrice(priceInput);
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

    public static void writeProductToDatabase(Product product, String filename) {
        try {
            FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            String s = String.valueOf(product.getPrice());
            bw.append(product.getId()).append(",").append(product.getProductName()).append(",").append(s).append(",").append(product.getCategory()).append("\n");

            bw.close(); // close the BufferedWriter object
            fw.close();
        } catch (IOException e) {
            System.out.println("Error writing to database file: " + e.getMessage());
        }
    }


    public static List<Product> listProducts() throws IOException {
        // Read the category data from the text file
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        // Parse the lines into a list of Category objects
        List<Product> products = new ArrayList<>();
        for (String line : lines) {
            String[] fields = line.split(",");
            products.add(new Product(fields[0], fields[1], parseInt(fields[2]), fields[3]));
        }

        return products;
    }

    public static List<Product> listProducts(String category, String order, String fileName) throws IOException {
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
    }


    public static List<Product> listProducts(int minPrice, int maxPrice, String category) throws IOException {
        // Read the product data from the text file
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        // Parse the lines into a list of Product objects
        List<Product> products = new ArrayList<>();
        for (String line : lines) {
            String[] fields = line.split(",");
            int price = Integer.parseInt(fields[2]);
            if (price >= minPrice && price <= maxPrice && category.equals(fields[3])) {
                products.add(new Product(fields[0], fields[1], price, fields[3]));
            }
        }

        return products;
    }


    public static Product findProductById(String id) throws IOException {
        // Read the product data from the text file
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        // Find the product with the given id
        for (String line : lines) {
            String[] fields = line.split(",");
            if (id.equals(fields[0])) {
                return new Product(fields[0], fields[1], Integer.parseInt(fields[2]), fields[3]);
            }
        }

        return null;
    }

    public static Product findProductByName(String name) throws IOException {
        // Read the product data from the text file
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        // Find the product with the given name
        for (String line : lines) {
            String[] fields = line.split(",");
            if (name.equals(fields[1])) {
                return new Product(fields[0], fields[1], Integer.parseInt(fields[2]), fields[3]);
            }
        }

        return null;
    }

    public static void changePriceById(String id, int newPrice) throws IOException {
        // Read the product data from the text file
        Path path = Paths.get(fileName);
        List<String> lines = Files.readAllLines(path);

        // Find the product with the given id and update its price
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] fields = line.split(",");
            if (id.equals(fields[0])) {
                fields[2] = String.valueOf(newPrice);
                lines.set(i, String.join(",", fields));
                break;
            }
        }

        // Write the updated product data back to the text file
        Files.write(path, lines);
    }

    public static void changePriceByName(String name, int newPrice) throws IOException {
        // Read the product data from the text file
        Path path = Paths.get(fileName);
        List<String> lines = Files.readAllLines(path);

        // Find the product with the given id and update its price
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] fields = line.split(",");
            if (name.equals(fields[1])) {
                fields[2] = String.valueOf(newPrice);
                lines.set(i, String.join(",", fields));
                break;
            }
        }

        // Write the updated product data back to the text file
        Files.write(path, lines);
    }


    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                Objects.equals(id, product.id) &&
                Objects.equals(productName, product.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, price);
    }
}







