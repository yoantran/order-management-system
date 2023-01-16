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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Method {


    public static int readList(String fileName, String splitPoint) throws IOException {
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
            String id = lastLine.split(splitPoint)[0];
            return parseInt(id.substring(1));
        }
    }

    public static String generateID(String id, String fileName, String splitPoint) throws IOException {
        int amount = readList(fileName, splitPoint);
        if (amount <= 0) {
            return id + (1);
        } else {
            amount++;
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

    public static int validatePrice(String priceInput) {
        Scanner sc = new Scanner(System.in);
        int price = 0;
        boolean valid = false;
        while (!valid) {
            try {
                price = Integer.parseInt(priceInput);
                if (price < 0) {
                    System.out.println("The product price cannot be less than 0, please re-input the price:");
                } else if (price > 2000000000) {
                    System.out.println("The product price cannot be more than 2 billions, please re-input the price:");
                } else {
                    valid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid positive number:");
            }
            if (!valid) priceInput = sc.nextLine();
        }
        return price;
    }

    public static int validateNumber(String string) {
        Scanner sc = new Scanner(System.in);
        int number = 0;
        boolean valid = false;
        while (!valid) {
            try {
                number = Integer.parseInt(string);
                if (number < 0) {
                    System.out.println("The product price cannot be less than 0, please re-input the price:");
                } else {
                    valid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid positive number:");
            }
            if (!valid) string = sc.nextLine();
        }
        return number;
    }


    public static void removeById(String id, String fileName, String split) throws IOException {
        // Read the data from the text file
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        // Find the index of the object with the given id
        int index = -1;
        for (int i = 0; i < lines.size(); i++) {
            String[] fields = lines.get(i).split(split);
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


    public static void removeByName(String name, String fileName, String split) throws IOException {
        // Read the data from the text file
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        // Find the index of the object with the given name
        int index = -1;
        for (int i = 0; i < lines.size(); i++) {
            String[] fields = lines.get(i).split(split);
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
