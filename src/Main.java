import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin("A00","admin", "admin");
        boolean isLogged = false;
        boolean isLoggedAdmin = false;


        while (true) {
            System.out.println("Enter 1 to register, 2 to login, or 0 to exit:");
            int option = scanner.nextInt();
            if (option == 0) {
                System.out.println("Thank you and Goodbye!");
                break;
            }
            else if (option == 1) {
                Customer.registerAccount("src/Data/account.txt");
            }
            else if (option == 2) {
                System.out.println("Enter your username and password:");
                String username = scanner.next();
                String password = scanner.next();
                Account.login("src/Data/account.txt", username, password);
            }
        }
    }
}

