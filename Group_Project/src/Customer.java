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

    public String getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(String membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    public int getTotalSpending() {
        return totalSpending;
    }

    public void setTotalSpending(int totalSpending) {
        this.totalSpending = totalSpending;
    }
    //methods
    @Override
    public String viewAccount() {
        return "accounts{" +
                "ID='" + super.getID() + '\'' +
                ", username='" + super.getUsername() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                ", fullName='" + super.getFullName() + '\'' +
                ", phoneNumber=" + super.getPhoneNumber() +
                ", email='" + super.getEmail() + '\'' +
                ", address='" + super.getAddress() + '\'' +
                ", Membership Status='" + this.getMembershipStatus() + '\'' +
                ", Total Spending= '" + this.getTotalSpending() + '\'' +
                '}';
    }
}
