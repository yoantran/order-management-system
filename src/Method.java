import java.io.*;
import java.security.NoSuchAlgorithmException;

public class Method {
//    public static void readFromDatabase(File file) {
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String line;
//            while((line = br.readLine()) != null) {
//                String[] data = line.split(","); // split line by comma delimiter
//                Customer account = new Customer(data[0], data[1], data[3], );
//                // do something with the user object
//            }
//            br.close(); // close the BufferedReader object
//        } catch (IOException e) {
//            System.out.println("Error reading from database file: " + e.getMessage());
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }

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
}
