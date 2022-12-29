package member;

import repository.dataManagement;
import repository.Converter;

public class Member {
    private static final String[] labelFields = new String[]{"Member ID", "User Name", "Password", "Full Name", "Phone Number", "Membership"};
    private static dataManagement repo;
    private String mID;
    private String userName;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String membership;
    private double expenditure;

    public Member(dataManagement repo) {
        if (Member.repo == null) {
            Member.repo = repo;
        }

    }

    public Member(String mID, String userName, String password, String fullName, String phoneNumber, double expenditure) {
        this.mID = mID.trim();
        this.userName = userName.trim();
        this.password = password.trim();
        this.fullName = fullName.trim();
        this.phoneNumber = phoneNumber;
        this.membership = "None";
        this.expenditure = 0.0;
    }

    public Member(String mID, String userName, String password, String fullName, String phoneNumber, String membership, double expenditure) {
        this.mID = mID;
        this.userName = userName.trim();
        this.password = password.trim();
        this.fullName = fullName.trim();
        this.phoneNumber = phoneNumber;
        this.membership = membership;
        this.expenditure = expenditure;
    }

    public String getMID() {
        return mID;
    }

    public void setMID(String mID) {
        this.mID = mID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public double getexpenditure() {
        return expenditure;
    }

    public void setexpenditure(double expenditure) {
        this.expenditure = expenditure;
    }

    public String toString() {
        String var10000 = this.mID;
        return var10000 + "," + this.userName + "," + this.password + "," + this.fullName + "," + this.phoneNumber + "," + this.membership + "," + Converter.toDecimal(this.expenditure) + "\n";
    }
}
