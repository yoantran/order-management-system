import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.lang.Double.parseDouble;
import static java.lang.Long.parseLong;

class Customer extends Account {
    private String fullName;
    private String phoneNumber;
    private String address;
    private String email;


    public Customer(int id, String username, String password, String fullName, String phoneNumber, String email, String address) {
        super(id, username, password);
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty.");
        }
        this.fullName = fullName;
        if (phoneNumber == null || phoneNumber.trim().isEmpty() || !phoneNumber.matches("/^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$/")) {
            throw new IllegalArgumentException("Phone number is invalid.");
        }
        this.phoneNumber = phoneNumber;
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty.");
        }
        this.address = address;
        if (email == null || email.trim().isEmpty() || !email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            throw new IllegalArgumentException("Email is invalid.");
        }
        this.email = email;
    }

    @Override    public boolean isAdmin() {
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

    public static void writeToDatabase(Customer account, String filename) {
        try {
            FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(account.getId()).append(",").append(account.getUsername()).append(",").append(account.getPassword()).append(",").append(account.getFullName()).append(",").append(account.getPhoneNumber()).append(",").append(account.getEmail()).append(",").append(account.getAddress()).append(",").append(account.getMembership()).append("\n");
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
            String[] fields = line.split(",");
            customers.add(new Customer(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6], fields[7]));
        }

        return customers;
    }

    public static Customer findCustomerById(String customerId) throws IOException, NoSuchAlgorithmException {
        // Read the category data from the text file
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        // Parse the lines into a list of Category objects
        Customer customer = null;
        for (String line : lines) {
            String[] fields = line.split(",");
            if (customerId.equals(fields[0])) {
                customer = new Customer(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6], fields[7]);
            }

        }

        return customer;
    }

    public double totalSpend() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        double totalSpend = 0;

        // Find the index of the object with the given id
        for (int i = 0; i < lines.size(); i++) {
            String[] fields = lines.get(i).split(",");
            if (fields[1].equals(this.getId())) {
                String[] products = fields[2].split("\\|");
                long totalCart = 0;
                for (int l = 0; l < products.length; l++) {
                    String[] product = products[l].split(";");
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