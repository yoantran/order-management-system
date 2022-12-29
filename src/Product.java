import java.io.IOException;
import java.util.Scanner;

public class Product {
    private String id;
    private final String productName;
    int price;
    private Category category;

    public Product(String productName, int price, Category category) throws IOException {
        this.id = Method.generateID("P", "E:\\Study\\order-management-system\\Data\\products.txt");
        this.productName = productName;
        setPrice(price);
        this.category = category;
    }

    public static void addProduct(String fileName) {
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
        do {
            if (Method.ifCategoryExisted(category)) {
                System.out.println("The category has already existed, please re-input the category name");
            }
        } while (true);

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrice(int price) {
        price = Method.validatePrice(price);
        this.price = price;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


}
