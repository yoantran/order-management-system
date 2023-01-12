import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

class Account {
    public static Account login(String file, String username, String password) throws IOException, NoSuchAlgorithmException {
        Account account = null;

//        Check if admin
        BufferedReader reader = new BufferedReader(new FileReader(".\\Data\\admin.txt"));
        String[] adminAccount = reader.readLine().split(",");
        if (username.equals(adminAccount[1]) && password.equals(adminAccount[2])) {
            account = new Admin(adminAccount[0], adminAccount[1], adminAccount[2]);
            return account;
        }
        reader.close();

//       Check if user
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // split line by comma delimiter

                if (username.equals(data[1]) && (password.equals(data[2]))) {
                    account = new Customer(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]);
                    return account;
                }
            }
            if (account == null) {
                System.out.printf("Invalid username or password.\n");
            }
            br.close(); // close the BufferedReader object
        } catch (IOException e) {
            System.out.println("Error reading from database file: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return account;

    }
}

