import java.io.IOException;
import java.util.Scanner;

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




















}
