import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Scanner;

public class Category {
    private String id;
    private String categoryName;

    public Category(String categoryName) throws IOException {
        setCategoryName(categoryName);
        this.id = Method.generateID("G", "E:\\Study\\order-management-system\\Data\\category.txt");
    }

    public String getCategoryName() {
        return categoryName;
    }

    private void setCategoryName(String categoryName) {
        categoryName = Method.validateEmpty(categoryName);
        Scanner sc = new Scanner(System.in);
        do {
            if (Method.ifCategoryExisted(categoryName)) {
                categoryName = sc.nextLine();
            } else {
                break;
            }
        } while (true);
        this.categoryName = categoryName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
