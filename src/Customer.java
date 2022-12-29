import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Pattern;

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

    public void setMembershipSilver() {
        this.membership = "Silver";
    }

    public void setMembershipGold() {
        this.membership = "Gold";
    }

    public void setMembershipPlatinum() {
        this.membership = "Platinum";
    }
}