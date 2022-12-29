public class Customer extends accounts{
    //attributes
    private String membershipStatus;
    private int totalSpending;
    //constructors

    public Customer(){

    }
    public Customer(String membershipStatus, int totalSpending) {
        this.membershipStatus = membershipStatus;
        this.totalSpending = totalSpending;
    }

    public Customer(String id, String username, String password, String fullName, int phoneNumber, String email, String address, String accountType, String membershipStatus, int totalSpending) {
        super(id, username, password, fullName, phoneNumber, email, address, accountType);
        this.membershipStatus = membershipStatus;
        this.totalSpending = totalSpending;
    }
    //Getters and Setters

}
