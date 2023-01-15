import java.io.*;
import java.security.NoSuchAlgorithmException;

abstract class Account {
    private final String id;
    private final String username;
    private String password;
    private String salt;

    public Account(String id, String usernameReg, String password) throws NoSuchAlgorithmException {
        this.id = id;
        this.username = usernameReg;
        this.salt = hashPassword.getSalt();
        this.password = hashPassword.get_SHA_256_SecurePassword(password, this.salt);
    }

    public Account(String id, String usernameReg, String password, String salt) throws NoSuchAlgorithmException {
        this.id = id;
        this.username = usernameReg;
        this.salt = salt;
        this.password = hashPassword.get_SHA_256_SecurePassword(password, this.salt);
    }

    public String getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }

    public String getSalt() {return this.salt;}

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean checkPassword(String password) {
        String hashedPassword = hashPassword.get_SHA_256_SecurePassword(password, this.salt);
        return this.password.equals(hashedPassword);
    }

    public abstract boolean isAdmin();

    public String getPassword() {
        return password;
    }



   public static Account login(String fileName, String username, String password) throws IOException, NoSuchAlgorithmException {
        Account account = null;

//        Check if admin
       BufferedReader reader = new BufferedReader(new FileReader(".\\Data\\admin.txt"));
       String[] adminAccount = reader.readLine().split("\\|");
       if (username.equals(adminAccount[1]) && password.equals(adminAccount[2])) {
           account = new Admin(adminAccount[0], adminAccount[1],adminAccount[2]);
           return account;
       }
       reader.close();

//       Check if user
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while((line = br.readLine()) != null) {
                String[] data = line.split("\\|"); // split line by comma delimiter
                String salt = data[8];
                String hashedPasswordX = hashPassword.get_SHA_256_SecurePassword(password, salt);
                if (username.equals(data[1]) && (hashedPasswordX.equals(data[2]))) {
                    account = new Customer(data[0], data[1], data[2], data[3], data[4],data[5],data[6],data[7], data[8]);
                    return account;
                }
            }
            if (account == null) {
                System.out.print("Invalid username or password.\n");
            }
            br.close(); // close the BufferedReader object
        } catch (IOException e) {
            System.out.println("Error reading from database file: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
       return account;

    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}