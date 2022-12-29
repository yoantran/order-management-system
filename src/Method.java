import java.io.*;
import java.util.Scanner;

public class Method {
//

    public static void writeToDatabase(Customer account, String filename) {
        try {
            FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(account.getUsername() + "," + account.getPassword() + "," + account.getId()
                    + "," + account.getFullName() + "," + account.getPhoneNumber() + "," + account.getEmail() + "," + account.getAddress() + "\n");

            bw.close(); // close the BufferedWriter object
            fw.close();
        } catch (IOException e) {
            System.out.println("Error writing to database file: " + e.getMessage());
        }
    }


    public static boolean ifExisted(String fileName, String username) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("E:\\Study\\order-management-system\\Data\\account.txt"));
            String line;
            while((line = br.readLine()) != null) {
                String[] data = line.split(","); // split line by comma delimiter

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

    public static int readList() throws FileNotFoundException {
//        Scanner fileScanner = new Scanner(new File("E:\\Study\\order-management-system\\Data\\account.txt"));
        int lineCount = 0;

        // Open the file
        try (BufferedReader reader = new BufferedReader(new FileReader("E:\\Study\\order-management-system\\Data\\account.txt"))) {
            // Read each line
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCount;
    }
    public static String generateID(String id) throws FileNotFoundException {
        int amount = readList();
        if (amount == 0) {
            return id + Integer.toString(01);
        }
        else {
            amount++;
            return id + Integer.toString(amount);
        }
    }
}
