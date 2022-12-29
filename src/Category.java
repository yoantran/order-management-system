import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Category {
    private String id;
    private String categoryName;

    public Category(String categoryName) throws IOException {
        setCategoryName(categoryName);
        this.id = Method.generateID("G", "E:\\Study\\order-management-system\\Data\\category.txt");
    }

    public Category(String id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }


    public static void addCategory (String fileName) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the category's name");
        String categoryName = scanner.nextLine();
        categoryName = Method.validateCategoryName(categoryName);

        Category category = new Category(categoryName);
        Method.writeCategoryToDatabase(category, fileName);
        System.out.println("Category added successfully!");
    }

    public String getCategoryName() {
        return categoryName;
    }

    private void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static void removeCategoryById (String id) throws IOException {
        Method.removeById(id, "E:\\Study\\order-management-system\\Data\\category.txt");
    }

    public static void removeCategoryByName (String name) throws IOException {
        Method.removeByName(name, "E:\\Study\\order-management-system\\Data\\category.txt");
        Method.replaceCategory(name, "None");
    }

    public static List<Category> listCategories() throws IOException {
        // Read the category data from the text file
        List<String> lines = Files.readAllLines(Paths.get("E:\\Study\\order-management-system\\Data\\category.txt"));

        // Parse the lines into a list of Category objects
        List<Category> categories = new ArrayList<>();
        for (String line : lines) {
            String[] fields = line.split(",");
            categories.add(new Category(fields[0], fields[1]));
        }

        return categories;
    }


















}
