import java.util.regex.Pattern;

class Customer extends Account {
    private String fullName;
    private String phoneNumber;
    private String address;
    private String email;


    public Customer(int id, String username, String password, String fullName, String phoneNumber, String email, String address) {
        super(id, username, password);
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty.");
        }
        this.fullName = fullName;
        if (phoneNumber == null || phoneNumber.trim().isEmpty() || !phoneNumber.matches("/^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$/")) {
            throw new IllegalArgumentException("Phone number is invalid.");
        }
        this.phoneNumber = phoneNumber;
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty.");
        }
        this.address = address;
        if (email == null || email.trim().isEmpty() || !email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            throw new IllegalArgumentException("Email is invalid.");
        }
        this.email = email;
    }

    @Override
    public boolean isAdmin() {
        return false;
    }
}