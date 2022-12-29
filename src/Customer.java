import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

class Customer extends Account {
    private String fullName;
    private String phoneNumber;
    private String address;
    private String email;

    private String membership;




    public Customer(String id, String username, String password, String fullName, String phoneNumber, String email, String address) throws FileNotFoundException, NoSuchAlgorithmException {
        super(id, username, password);
        setId(id);
        setFullName(fullName);
        setPhoneNumber(phoneNumber);
        setAddress(address);
        setEmail(email);
        setMembershipRegular();

    }

    public Customer(String id, String usernameReg, String password, String fullName, String phoneNumber, String address, String email, String membership) throws NoSuchAlgorithmException {
        super(id, usernameReg, password);
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.membership = membership;
    }

    public static void registerAccount(String filename) throws IOException, NoSuchAlgorithmException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a username and password:");
        String username = scanner.nextLine();
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

        Customer account = new Customer(Method.generateID("C", filename), username, password, fullName, phoneNumber, email, address);
        Method.writeToDatabase(account, filename);
        System.out.println("Register successfully!");
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
        Scanner sc = new Scanner(System.in);
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

    public void setMembershipRegular() {
        this.membership = "Regular";
    }

    public void setMembershipSilver() {
        this.membership = "Silver";
    }

    public void setMembershipGold() {
        this.membership = "Gold";
    }

    public void setMembershipPlatinum() {
        this.membership = "Platinum";
    }

    public static List<Customer> listCustomers() throws IOException, NoSuchAlgorithmException {
        // Read the category data from the text file
        List<String> lines = Files.readAllLines(Paths.get("E:\\Study\\order-management-system\\Data\\category.txt"));

        // Parse the lines into a list of Category objects
        List<Customer> customers = new ArrayList<>();
        for (String line : lines) {
            String[] fields = line.split(",");
            customers.add(new Customer(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6], fields[7]));
        }

        return customers;
    }




























}