class Admin extends Account {
    public Admin(int id, String username, String password) {
        super(id, username, password);
    }

    @Override
    public boolean isAdmin() {
        return true;
    }
}