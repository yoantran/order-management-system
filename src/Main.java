import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin("A00","admin", "admin");
        String fileName = "E:\\Study\\order-management-system\\Data\\account.txt";
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
                Customer.registerAccount(fileName);
            }
            else if (option == 2) {
                System.out.println("Enter your username and password:");
                String username = scanner.next();
                String password = scanner.next();
                Account.login(fileName, username, password);
            }
        }
    }
}

