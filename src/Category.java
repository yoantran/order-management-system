import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    public static void writeCategoryToDatabase(Category category, String filename) {
        try {
            FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(category.getId() + "," + category.getCategoryName());

            bw.close(); // close the BufferedWriter object
            fw.close();
        } catch (IOException e) {
            System.out.println("Error writing to database file: " + e.getMessage());
        }
    }

    public static boolean ifCategoryExisted(String category) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("E:\\Study\\order-management-system\\Data\\category.txt"));
            String line;
            while((line = br.readLine()) != null) {
                String[] data = line.split(","); // split line by comma delimiter

                if (category.equals(data[1])) {
                    return true;
                }
            }
            br.close(); // close the BufferedReader object

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    public static void addCategory (String fileName) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the category's name");
        String categoryName = scanner.nextLine();
        categoryName = validateCategoryName(categoryName);

        Category category = new Category(categoryName);
        writeCategoryToDatabase(category, fileName);
        System.out.println("Category added successfully!");
    }

    public static String validateCategoryName(String categoryName) {
        categoryName = Method.validateEmpty(categoryName);
        Scanner sc = new Scanner(System.in);
        do {
            if (ifCategoryExisted(categoryName)) {
                System.out.println("Category existed! Please choose another one!");
                categoryName = sc.nextLine();
            } else {
                break;
            }
        } while (true);
        return categoryName;
    }

    public static String validateCategory(String category, String fileName) throws IOException {
        Scanner scanner = new Scanner(System.in);

        if (!ifCategoryExisted(category)) {
            System.out.println("The category you input does not exist in the system. Would you like to add new category (1) or re-input the category (2)? 1/2");
            int productCase = scanner.nextInt();
            scanner.nextLine();
            do {
                if (productCase != 1 && productCase != 2) {
                    System.out.println("Please input 1 or 2 only! (1) to add new category and (2) to re-input the category!");
                    productCase = scanner.nextInt();
                    scanner.nextLine();
                } else
                    break;
            } while (true);
            if (productCase == 1) {
                Category categoryAdd = new Category(category);
                Category.writeCategoryToDatabase(categoryAdd, fileName);
                System.out.println("New category added!");
            } else {
                System.out.println("Please input the category");
                category = scanner.nextLine();
                category = validateCategory(category, fileName);
            }
            return category;
        }
        return category;
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
        replaceCategory(name, "None");
    }

    public static void replaceCategory(String oldCategory, String newCategory) throws IOException {
        // Read the product data from the text file
        Path path = Paths.get("E:\\Study\\order-management-system\\Data\\products.txt");
        List<String> lines = Files.readAllLines(path);

        // Replace the category name in each line
        for (int i = 0; i < lines.size(); i++) {
            String[] fields = lines.get(i).split(",");
            if (fields[3].equals(oldCategory)) {
                fields[3] = newCategory;
                lines.set(i, fields[0] + "," + fields[1] + "," + fields[2] + "," + fields[3]);
            }
        }

        // Write the modified lines back to the text file
        Files.write(path, lines);
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

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}