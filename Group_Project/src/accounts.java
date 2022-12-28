import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;
import java.util.StringTokenizer;

public class accounts {
    //attributes
    private String ID;
    private String username;
    private String password;
    private String fullName;
    private int phoneNumber;
    private String email;
    private String address;

    private String accountType;
    //Constructor
    public accounts(){
    }
    public accounts(String id, String username, String password, String fullName, int phoneNumber, String email, String address, String accountType) {
        this.ID = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.accountType = accountType;
    }
    //Getters and Setters

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccountType() {
        if(this.accountType.equals("Customer") || this.accountType.equals("Admin")){
            return accountType;
        }
        else{
            return "Not correct type";
        }
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    //methods
    public static int readCustomerList() throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("src/account.txt"));
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
            return "C00" + 1;
        }
        else{
            customerAmount = customerAmount + 1;
            return "C00" + customerAmount;
        }
    }
    public static void registerCustomerAccount() throws NoSuchAlgorithmException, IOException {
        try(FileWriter fw = new FileWriter("src/account.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            String ID = generateID();
            Scanner sc = new Scanner(System.in);
            System.out.println("Please write your username");
            String username = sc.nextLine();
            System.out.println("Please write the password");
            String password = sc.nextLine();
            String salt = getSalt();
            String securePassword = get_SHA_256_SecurePassword(password,salt);
            System.out.println("Enter your full name");
            String fullName = sc.nextLine();
            System.out.println("Enter your phone number");
            int phoneNumber = Integer.parseInt(sc.nextLine());
            System.out.println("Enter your email");
            String email = sc.nextLine();
            System.out.println("Enter your address");
            String address = sc.nextLine();
            String accountType = "Customer";
            System.out.println("Do you want to save?");
            String answer = sc.nextLine();
            if(answer.equals("Yes")) {
                out.println(ID + " , " + username + " , " + securePassword + " , " + fullName + " , " + phoneNumber + " , " + email + " , " + address + " , " + accountType);
            }

            out.close();
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }

    }

    private static String get_SHA_256_SecurePassword(String passwordToHash,
                                                     String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    private static String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }


}
