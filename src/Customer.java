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



    public static int readCustomerList() throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("src/Data/account.txt"));
        int count = 0;
        while (fileScanner.hasNext()){
            count++;
        }
        fileScanner.close();
        return count;
    }
    public static String generateID() throws FileNotFoundException {
        int customerAmount = readCustomerList();
        if(customerAmount == 0){
            return "C" + Integer.toString(01);
        }
        else{
            customerAmount++;
            return "C" + Integer.toString(customerAmount);
        }
    }

    public static void registerAccount(String filename) throws IOException, NoSuchAlgorithmException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a username and password:");
        String username = scanner.nextLine();
        String password = scanner.nextLine();
        System.out.println("Please enter your full name");
        String fullName = scanner.nextLine();

        System.out.println("Please enter your phone number");
        String phoneNumber = scanner.nextLine();

        System.out.println("Please enter your email");
        String email = scanner.nextLine();

        System.out.println("Please enter your address");
        String address = scanner.nextLine();
        Customer account = new Customer(generateID(), username, password, fullName, phoneNumber, email, address);
        Method.writeToDatabase(account, filename);
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        Scanner sc = new Scanner(System.in);
        do {
            if (fullName == null || fullName.trim().isEmpty()) {
                System.out.println("Your full name is invalid, please re-input your full name");
                fullName = sc.nextLine();
            } else {
                break;
            }
        } while (true);
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        Scanner sc = new Scanner(System.in);
        do {
            if (phoneNumber == null || phoneNumber.trim().isEmpty() || !phoneNumber.matches("^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$")) {
                System.out.println("Your phone number is invalid, please re-input your phone number");
                phoneNumber = sc.nextLine();
            } else {
                break;
            }
        }while (true);
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        Scanner sc = new Scanner(System.in);
        do {
            if (address == null || address.trim().isEmpty()) {
                System.out.println("Your address is invalid, please re-input your address");
                address = sc.nextLine();
            } else {
                break;
            }
        } while (true);
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        Scanner sc = new Scanner(System.in);
        do {
            if (email == null || email.trim().isEmpty() || !email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
                System.out.println("Your email is invalid, please re-input your email");
                email = sc.nextLine();
            } else {
                break;
            }
        } while (true);
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