import java.util.List;
import java.util.ArrayList;

class AccountManager {

    private List<Account> accounts;
    private int nextId;

    public AccountManager() {
        accounts = new ArrayList<>();
        nextId = 1;
    }

    public void addUser(Account account) {
        account.setId(nextId);
        nextId++;
        accounts.add(account);
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