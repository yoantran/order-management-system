import java.security.NoSuchAlgorithmException;

class Admin extends Account {
    public Admin(String id, String username, String password) throws NoSuchAlgorithmException {
        super(id, username, password);
    }

    @Override
    public boolean isAdmin() {
        return true;
    }
}