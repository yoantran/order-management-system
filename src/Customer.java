class Customer extends Account {
    public Customer(int id, String username, String password) {
        super(id, username, password);
    }

    @Override
    public boolean isAdmin() {
        return false;
    }
}