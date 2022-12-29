import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
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
    public static int readCustomerList() throws IOException {
        BufferedReader fileScanner = new BufferedReader(new FileReader("C:\\Users\\Hy\\Downloads\\GitHub\\order-management-system\\Group_Project\\src\\account.txt"));
        int count = 0;
        while (fileScanner.readLine() != null){
            count++;
        }
        System.out.println(count);
        fileScanner.close();
        return count;
    }
    public static String generateID() throws IOException {
        int customerAmount = readCustomerList();
        if(customerAmount == 0){
            return "C00" + 1;
        }
        else{
            customerAmount = customerAmount + 1;
            return "C00" + customerAmount;
        }
    }
    public static boolean checkUser(String username) throws IOException {
        Scanner fileScanner = new Scanner(new File(("C:\\Users\\Hy\\Downloads\\GitHub\\order-management-system\\Group_Project\\src\\account.txt")));
        String line;
        while (fileScanner.hasNext()){
            line = fileScanner.nextLine();
            StringTokenizer inReader = new StringTokenizer(line," , ");
            if (inReader.countTokens() != 8)
                throw new IOException("Invalid Input Format");
            else {

                // Since there is always 3 tokens of a line so take turn to
                // assign them into name, address and age
                String ID = inReader.nextToken();
                String user = inReader.nextToken();
                if(username.equals(user)){
                    System.out.println("Username is already taken");
                    return false;
                }
                else {
                    return true;
                }

            }
        }
        return false;
    }

    public static void registerCustomerAccount() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        FileWriter fw = new FileWriter("src/account.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        String ID = generateID();
        Scanner sc = new Scanner(System.in);
        System.out.println("Please write your username");
        String username = sc.nextLine();
        if(checkUser(username)){
            System.out.println("Please write the password");
            String password = sc.nextLine();
            String securePassword = generateStorngPasswordHash(password);
            System.out.println("Enter your full name");
            String fullName = sc.nextLine();
            System.out.println("Enter your phone number");
            int phoneNumber = Integer.parseInt(sc.nextLine());
            System.out.println("Enter your email");
            String email = sc.nextLine();
            System.out.println("Enter your address");
            String address = sc.nextLine();
            String accountType = "Customer";
            bw.write(ID + " , " + username + " , " + securePassword + " , " + fullName + " , " + phoneNumber + " , " + email + " , " + address + " , " +accountType);
            bw.newLine();
            System.out.println("Thank You");
         sc.close();
        bw.close();
        }
        else {
            registerCustomerAccount();
        }

    }
        public Boolean verifyLogin() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
            String ID, systemUsername, systemPassword,systemFullName , systemEmail, systemAddress, systemAccountType;
            int systemPhoneNumber;
            Scanner sc = new Scanner(System.in);
            System.out.println("Type in your username");
            String username = sc.nextLine();
            System.out.println("Type in your password");
            String password = sc.nextLine();
            Scanner fileScanner = new Scanner(new File("src/account.txt"));
            String line;
            Boolean login = false;
            // Continue to loop while the fileScanner does not finish reading the whole file
            while (fileScanner.hasNext()) {

                // Read the whole line of the file
                line = fileScanner.nextLine();

                // Use StringTokenizer object to parse a string line into tokens using deliminator ","
                StringTokenizer inReader = new StringTokenizer(line, " , ");

                // Double check if there are exactly three tokens of every line
                if (inReader.countTokens() < 0)
                    throw new IOException("Invalid Input Format");
                else {

                    // Since there is always 3 tokens of a line so take turn to
                    // assign them into name, address and age
                    ID = inReader.nextToken();
                    systemUsername = inReader.nextToken();
                    systemPassword = inReader.nextToken();
                    systemFullName = inReader.nextToken();
                    systemPhoneNumber = Integer.parseInt(inReader.nextToken());
                    systemEmail = inReader.nextToken();
                    systemAddress = inReader.nextToken();
                    System.out.println(systemPassword);
                    System.out.println(password);
                    if (validatePassword(password, systemPassword)) {
                        this.ID = ID;
                        this.username = systemUsername;
                        this.password = systemPassword;
                        this.fullName = systemFullName;
                        this.phoneNumber = systemPhoneNumber;
                        this.email = systemEmail;
                        this.address = systemAddress;
                        login = true;
                    }

                }
            }
            return login;
        }

    private static boolean validatePassword(String originalPassword, String storedPassword)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);

        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(),
                salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }
    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i < bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
    private static String generateStorngPasswordHash(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = getSalt();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }



    private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);

        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }

}

    public String viewAccount() {
        return "accounts{" +
                "ID='" + ID + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
