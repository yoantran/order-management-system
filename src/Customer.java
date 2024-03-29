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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Double.parseDouble;
import static java.lang.Long.parseLong;

class Customer extends Account {
    private String fullName;
    private String phoneNumber;
    private String address;
    private String email;
    private String membership;

    private static final String fileName = ".\\Data\\account.txt";


    public Customer(String id, String username, String password, String fullName, String phoneNumber, String email, String address) throws NoSuchAlgorithmException {
        super(id, username, password);
        setFullName(fullName);
        setPhoneNumber(phoneNumber);
        setAddress(address);
        setEmail(email);
        this.membership = "Regular";

    }

    public Customer(String id, String usernameReg, String password, String fullName, String phoneNumber, String email, String address, String membership, String salt) throws NoSuchAlgorithmException {
        super(id, usernameReg, password, salt);
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.membership = membership;
    }


    public static void registerAccount(String filename) throws IOException, NoSuchAlgorithmException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a username:");
        String username = scanner.nextLine();
        Scanner sc = new Scanner(System.in);
        do {
            if (ifUsernameExisted(fileName, username)) {
                username = sc.nextLine();
            } else {
                break;
            }
        } while (true);

        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        System.out.println("Please enter your full name");
        String fullName = scanner.nextLine();
        fullName = Method.validateEmpty(fullName);

        System.out.println("Please enter your phone number");
        String phoneNumber = scanner.nextLine();
        phoneNumber = Method.validatePhone(phoneNumber);

        System.out.println("Please enter your email");
        String email = scanner.nextLine();
        email = Method.validateEmail(email);


        System.out.println("Please enter your address");
        String address = scanner.nextLine();
        address = Method.validateEmpty(address);

        Customer account = new Customer(Method.generateID("C", filename, "\\|"), username, password, fullName, phoneNumber, email, address);
        writeToDatabase(account, filename);
        System.out.println("Register successfully!");
    }

    public static boolean ifUsernameExisted(String fileName, String username) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|"); // split line by comma delimiter

                if (username.equals(data[1])) {
                    System.out.println("Username existed! Please choose another one!");
                    return true;
                }
            }
            br.close(); // close the BufferedReader object

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void updateCustomerInformation() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.printf("You are changing your personal information for %s!\n", this.getUsername());
        System.out.printf("Your old name is '%s', please input your new name or type your old!\n", this.getFullName());
        String newName = sc.nextLine();
        this.setFullName(newName);
        System.out.printf("Your old phone number is %s, please input your new one or type your old!\n", this.getPhoneNumber());
        String newPhoneNumber = sc.nextLine();
        this.setPhoneNumber(newPhoneNumber);
        System.out.printf("Your old address is %s, please input your new one or type your old!\n", this.getAddress());
        String newAddress = sc.nextLine();
        this.setAddress(newAddress);
        System.out.printf("Your old email is %s, please input your new one or type your old!\n", this.getEmail());
        String newEmail = sc.nextLine();
        this.setEmail(newEmail);

        Path path = Paths.get(fileName);
        List<String> lines = Files.readAllLines(path);

        // Replace the customer information if it reaches the id
        for (int i = 0; i < lines.size(); i++) {
            String[] fields = lines.get(i).split("\\|");
            if (fields[0].equals(this.getId())) {
                fields[3] = this.getUsername();
                fields[4] = this.getPhoneNumber();
                fields[5] = this.getEmail();
                fields[6] = this.getAddress();
                lines.set(i, fields[0] + "|" + fields[1] + "|" + fields[2] + "|" + fields[3] + "|" + fields[4] + "|" + fields[5] + "|" + fields[6] + "|" + fields[7] + "|" + fields[8]);
                break;
            }
        }

        // Write the modified lines back to the text file
        Files.write(path, lines);

    }

    public void setMembership(String membership) throws IOException {
        this.membership = membership;
        Path path = Paths.get(fileName);
        List<String> lines = Files.readAllLines(path);

        // Replace the customer information if it reaches the id
        for (int i = 0; i < lines.size(); i++) {
            String[] fields = lines.get(i).split("\\|");
            if (fields[0].equals(this.getId())) {

                fields[7] = membership;
                lines.set(i, fields[0] + "|" + fields[1] + "|" + fields[2] + "|" + fields[3] + "|" + fields[4] + "|" + fields[5] + "|" + fields[6] + "|" + fields[7] + "|" + fields[8]);
                break;
            }
        }

        // Write the modified lines back to the text file
        Files.write(path, lines);
    }

    public static Customer updatePassword(Customer customer) throws IOException {
        Scanner sc = new Scanner(System.in);
        String username = customer.getUsername();
        System.out.printf("You are changing your password for %s!\n", customer.getUsername());
        System.out.print("Please input your old password\n");
        String oldPassword = sc.nextLine();

        Path path = Paths.get(fileName);
        List<String> lines = Files.readAllLines(path);

        // Replace the customer information if it reaches the id
        for (int i = 0; i < lines.size(); i++) {
            String[] fields = lines.get(i).split("\\|");
            String salt = fields[8];
            String hashedPassword = hashPassword.get_SHA_256_SecurePassword(oldPassword, salt);
            if (fields[1].equals(username) && fields[2].equals(hashedPassword)) {
                System.out.println("Your password is correct!");
                do {
                    System.out.println("Please input the new password");
                    String newPassword = sc.nextLine();
                    System.out.println("Please re-input your new password");
                    String newRePassword = sc.nextLine();

                    if (newPassword.equals(newRePassword)) {
                        fields[2] = hashPassword.get_SHA_256_SecurePassword(newPassword, salt);
                        lines.set(i, fields[0] + "|" + fields[1] + "|" + fields[2] + "|" + fields[3] + "|" + fields[4] + "|" + fields[5] + "|" + fields[6] + "|" + fields[7] + "|" + fields[8]);
                        System.out.println("Password changed successfully!");
                        break;
                    } else {
                        System.out.println("The two password must match in order to change it!");
                    }

                } while (true);

                break;
            }
        }
        // Write the modified lines back to the text file
        Files.write(path, lines);
        return customer;

    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        fullName = Method.validateEmpty(fullName);
        this.fullName = fullName;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        phoneNumber = Method.validatePhone(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        address = Method.validateEmpty(address);
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = Method.validateEmail(email);
        this.email = email;
    }

    public String getMembership() {
        return membership;
    }


    public static void writeToDatabase(Customer account, String filename) {
        try {
            FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(account.getId()).append("|").append(account.getUsername()).append("|").append(account.getPassword()).append("|").append(account.getFullName()).append("|").append(account.getPhoneNumber()).append("|").append(account.getEmail()).append("|").append(account.getAddress()).append("|").append(account.getMembership()).append("|").append(account.getSalt()).append("\n");
            bw.close(); // close the BufferedWriter object
            fw.close();
        } catch (IOException e) {
            System.out.println("Error writing to database file: " + e.getMessage());
        }
    }

    public static List<Customer> listCustomers() throws IOException, NoSuchAlgorithmException {
        // Read the category data from the text file
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        // Parse the lines into a list of Category objects
        List<Customer> customers = new ArrayList<>();
        for (String line : lines) {
            String[] fields = line.split("\\|");
            customers.add(new Customer(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6], fields[7], fields[8]));
        }

        return customers;
    }

    public static Customer findCustomerById(String customerId) throws IOException, NoSuchAlgorithmException {
        // Read the category data from the text file
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        // Parse the lines into a list of Category objects
        Customer customer = null;
        for (String line : lines) {
            String[] fields = line.split("\\|");
            if (customerId.equals(fields[0])) {
                customer = new Customer(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6], fields[7], fields[8]);
            }

        }

        return customer;
    }

    public double getTotalSpend() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(".\\Data\\order.txt"));
        double totalSpend = 0;

        // Find the index of the object with the given id
        for (String line : lines) {
            String[] fields = line.split(",");
            if (fields[1].equals(this.getId())) {
                String[] products = fields[2].split("\\|");
                long totalCart = 0;
                for (String s : products) {
                    String[] product = s.split(";");
                    totalCart += parseLong(product[2]) * parseLong(product[3]);
                }
                totalCart *= parseDouble(fields[4]);
                totalSpend += totalCart;
            }
        }

        return totalSpend;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id='" + this.getId() + '\'' +
                ", username='" + this.getUsername() + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", membership='" + membership + '\'' +
                '}';
    }
}