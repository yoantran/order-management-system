import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
public class Main {
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
                Account account = new Customer(nextId, username, password);
                manager.addUser(account);
                nextId++;
            }
            else if (option == 2) {
                System.out.println("Enter your username and password:");
                String username = scanner.next();
                String password = scanner.next();
                Account account = manager.login(username, password);
                if (account == null) {
                    System.out.println("Invalid username or password.");
                }
                else {
                    System.out.println("Welcome, " + account.getUsername() + "!");
    }}}}}
