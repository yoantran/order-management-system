import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
public class Main {
    public static void signIn(AccountManager manager) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your username and password:");
        String username = scanner.next();
        String password = scanner.next();
        Account account = manager.login(username, password);
        if (account == null) {
            System.out.println("Invalid username or password.");
        }
        else {
            System.out.println("Welcome, " + account.getUsername() + "!");
        }
    }


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        AccountManager manager = new AccountManager();
        int nextId = 1;
        while (true) {
            System.out.println("Enter 1 to register, 2 to login, or 0 to exit:");
            int option = scanner.nextInt();
            if (option == 0) {
                System.out.println("Thank you and Goodbye!");
                break;
            }
            else if (option == 1) {
                System.out.println("Enter a username and password:");
                String username = scanner.next();
                String password = scanner.next();
                System.out.println("Please enter your full name");
                String fullName = scanner.next();

                System.out.println("Please enter your phone number");
                String phoneNumber = scanner.next();

                System.out.println("Please enter your email");
                String email = scanner.next();

                System.out.println("Please enter your address");
                String address = scanner.next();

                Account account = new Customer(nextId, username, password, fullName, phoneNumber, email, address);
                manager.addUser(account);
                nextId++;
            }
            else if (option == 2) {
                signIn(manager);
            }
        }
    }
}

