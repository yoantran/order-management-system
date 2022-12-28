import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

abstract class Account {
    private String id;
    private final String username;
    private String password;

    public Account(String id, String username, String password) throws NoSuchAlgorithmException {
        this.id = id;
        this.username = username;
//        String hashedPassword = get_SHA_256_SecurePassword(password, getSalt());
        this.password = password;
    }

    public Account(String username) {

        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public abstract boolean isAdmin();

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

   public static String login(File file, String username, String password) {
        String id = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line = br.readLine()) != null) {
                String[] data = line.split(","); // split line by comma delimiter
                if (username == data[0] && !(password == data[1])) {

                    System.out.println("Wrong username or password");
                } else if (username == data[0] && (password == data[1])) {
                    id = data[3];
                    System.out.printf("Welcome %s!\n", data[0]);
                } else {
                    System.out.printf("Invalid username or password.");
                }
                // do something with the user object
            }
            br.close(); // close the BufferedReader object
        } catch (IOException e) {
            System.out.println("Error reading from database file: " + e.getMessage());
        }
        return id;


    }
}