import java.io.*;
import java.security.NoSuchAlgorithmException;

public class Method {
//

    public static void writeToDatabase(Customer account, String filename) {
        try {
            FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(account.getUsername() + "," + account.getPassword() + "," + account.getId()
                    + "," + account.getFullName() + "," + account.getPhoneNumber() + "," + account.getEmail() + "," + account.getAddress() + "\n");

            bw.close(); // close the BufferedWriter object
        } catch (IOException e) {
            System.out.println("Error writing to database file: " + e.getMessage());
        }
    }


    public static boolean ifExisted(String fileName, String username) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("./Data/account.txt"));
            String line;
            while((line = br.readLine()) != null) {
                String[] data = line.split(","); // split line by comma delimiter
                System.out.println(data[0]);
                if (username.equals(data[0])) {
                    System.out.println("Username existed! Please choose another one!");
                    return true;
                }
                // do something with the user object
            }
            br.close(); // close the BufferedReader object

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
