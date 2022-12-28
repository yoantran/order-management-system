import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

class AccountManager {

    private List<Customer> accounts;
    private int nextId;

    public AccountManager() {
        accounts = new ArrayList<>();
        nextId = 1;
    }


    public Account login(String username, String password) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username) && account.checkPassword(password)) {
                return account;
            }
        }
        return null;
    }




    public int getNextId() {
        return nextId;
    }

}