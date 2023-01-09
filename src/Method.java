import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Method {
//

    public static void writeToDatabase(Customer account, String filename) {
        try {
            FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(account.getId()).append(",").append(account.getUsername()).append(",").append(account.getPassword()).append(",").append(account.getFullName()).append(",").append(account.getPhoneNumber()).append(",").append(account.getEmail()).append(",").append(account.getAddress()).append(",").append(account.getMembership()).append("\n");
//bw.append(account.getId() + "," + account.getUsername() + "," + account.getPassword()
//                    + "," + account.getFullName() + "," + account.getPhoneNumber() + "," + account.getEmail()
//                    + "," + account.getAddress() + "," + account.getMembership() + "\n");
            bw.close(); // close the BufferedWriter object
            fw.close();
        } catch (IOException e) {
            System.out.println("Error writing to database file: " + e.getMessage());
        }
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






    public static boolean ifUsernameExisted(String fileName, String username) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while((line = br.readLine()) != null) {
                String[] data = line.split(","); // split line by comma delimiter

                if (username.equals(data[1])) {
                    System.out.println("Username existed! Please choose another one!");
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

    public static int readList(String fileName) throws IOException {
//        Scanner fileScanner = new Scanner(new File("E:\\Study\\order-management-system\\Data\\account.txt"));
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = "";
        String lastLine = "";
        while ((line = reader.readLine()) != null) {
            lastLine = line;
        }
        reader.close();
        if (lastLine.equals("")) {
            return 0;
        } else {
            String id = lastLine.split(",")[0];
            return parseInt(id.substring(1));
        }
    }
    public static String generateID(String id, String fileName) throws IOException {
        int amount = readList(fileName);
        if (amount <= 0) {
            return id + (1);
        }
        else {
            amount ++;
            return id + (amount);
        }
    }


    public static String validateEmpty(String variable) {
        Scanner sc = new Scanner(System.in);
        do {
            if (variable == null || variable.trim().isEmpty()) {
                System.out.println("Your full name is invalid, please re-input your full name");
                variable = sc.nextLine();
            } else {
                break;
            }
        } while (true);
        return variable;
    }

    public static String validatePhone(String phoneNumber) {
        Scanner sc = new Scanner(System.in);
        do {
            if (phoneNumber == null || phoneNumber.trim().isEmpty() || !phoneNumber.matches("^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$")) {
                System.out.println("Your phone number is invalid, please re-input your phone number");
                phoneNumber = sc.nextLine();
            } else {
                break;
            }
        } while (true);
        return phoneNumber;
    }

    public static String validateEmail(String email) {
        Scanner sc = new Scanner(System.in);
        do {
            if (email == null || email.trim().isEmpty() || !email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
                System.out.println("Your email is invalid, please re-input your email");
                email = sc.nextLine();
            } else {
                break;
            }
        } while (true);
        return email;
    }

    public static int validatePrice (int price) {
        Scanner sc = new Scanner(System.in);
        do {
            if (price < 0) {
                System.out.println("The product price cannot be less than 0, please re-input the price");
                price = sc.nextInt();
                sc.nextLine();
            } else {
                break;
            }
        } while (true);
        return price;
    }

    public static String validateCategoryName(String categoryName) {
        categoryName = Method.validateEmpty(categoryName);
        Scanner sc = new Scanner(System.in);
        do {
            if (Method.ifCategoryExisted(categoryName)) {
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

        if (!Method.ifCategoryExisted(category)) {
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
                Method.writeCategoryToDatabase(categoryAdd, fileName);
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

    public static void removeById (String id, String fileName) throws IOException {
            // Read the data from the text file
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        // Find the index of the object with the given id
        int index = -1;
        for (int i = 0; i < lines.size(); i++) {
            String[] fields = lines.get(i).split(",");
            if (fields[0].equals(id)) {
                index = i;
                break;
            }
        }

        // If the object was found, remove it from the list
        if (index != -1) {
            lines.remove(index);
        }

        // Open the text file for writing
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        // Write the updated list of objects to the text file
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }

        // Close the writer
        writer.close();
    }


    public static void removeByName(String name, String fileName) throws IOException {
        // Read the data from the text file
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        // Find the index of the object with the given name
        int index = -1;
        for (int i = 0; i < lines.size(); i++) {
            String[] fields = lines.get(i).split(",");
            if (fields[1].equals(name)) {
                index = i;
                break;
            }
        }

        // If the name was found, remove it from the list
        if (index != -1) {
            lines.remove(index);
        }

        // Open the text file for writing
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        // Write the updated list of objects to the text file
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }

        // Close the writer
        writer.close();
    }



    public static Customer updateCustomerInformation(Customer customer) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.printf("You are changing your personal information for %s!\n", customer.getUsername());
        System.out.printf("Your old name is '%s', please input your new name or type your old!\n", customer.getFullName());
        String newName = sc.nextLine();
        customer.setFullName(newName);
        System.out.printf("Your old phone number is %s, please input your new one or type your old!\n", customer.getPhoneNumber());
        String newPhoneNumber = sc.nextLine();
        customer.setPhoneNumber(newPhoneNumber);
        System.out.printf("Your old address is %s, please input your new one or type your old!\n", customer.getAddress());
        String newAddress = sc.nextLine();
        customer.setAddress(newAddress);
        System.out.printf("Your old email is %s, please input your new one or type your old!\n", customer.getEmail());
        String newEmail = sc.nextLine();
        customer.setEmail(newEmail);

        Path path = Paths.get("E:\\Study\\order-management-system\\Data\\account.txt");
        List<String> lines = Files.readAllLines(path);

        // Replace the customer information if it reaches the id
        for (int i = 0; i < lines.size(); i++) {
            String[] fields = lines.get(i).split(",");
            if (fields[0].equals(customer.getId())) {
                fields[3] = customer.getUsername();
                fields[4] = customer.getPhoneNumber();
                fields[5] = customer.getEmail();
                fields[6] = customer.getAddress();
                lines.set(i, fields[0] + "," + fields[1] + "," + fields[2] + "," + fields[3] + "," + fields[4] + "," + fields[5] + "," + fields[6] + "," + fields[7]);
                break;
            }
        }

        // Write the modified lines back to the text file
        Files.write(path, lines);
        return customer;

    }

    public static Customer updatePassword(Customer customer) throws IOException {
        Scanner sc = new Scanner(System.in);
        String username = customer.getUsername();
        System.out.printf("You are changing your passowrd for %s!\n", customer.getUsername());
        System.out.printf("Please input your old password\n", customer.getFullName());
        String oldPassword = sc.nextLine();

        Path path = Paths.get("E:\\Study\\order-management-system\\Data\\account.txt");
        List<String> lines = Files.readAllLines(path);

        // Replace the customer information if it reaches the id
        for (int i = 0; i < lines.size(); i++) {
            String[] fields = lines.get(i).split(",");
            if (fields[1].equals(username) && fields[2].equals(oldPassword)) {
                do {
                    System.out.println("Your password is correct, please input the new password");
                    String newPassword = sc.nextLine();
                    System.out.println("Please re-input your new password");
                    String newRePassword = sc.nextLine();
                    if (newPassword.equals(newRePassword)) {
                        fields[2] = newPassword;
                        lines.set(i, fields[0] + "," + fields[1] + "," + fields[2] + "," + fields[3] + "," + fields[4] + "," + fields[5] + "," + fields[6] + "," + fields[7]);
                        break;
                    }

                } while (true);

                break;
            }
        }
        // Write the modified lines back to the text file
        Files.write(path, lines);
        System.out.println("Password changed successfully!");
        return customer;

    }



    public static void printOrders(List<Order> orders) throws IOException {
        for (Order order : orders) {
            String id = order.getId();
            Cart carT = order.getCart();
            String customerID = order.getCustomer();
            LocalDate date = order.getDateTime();
            String status = order.getStatus();
            System.out.println(id + " - " + carT + " - " + customerID + " - " + date + " - " + status + " - " + order.totalPrice());
        }
    }









}
