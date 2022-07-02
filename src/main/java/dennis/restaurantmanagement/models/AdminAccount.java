package dennis.restaurantmanagement.models;

import dennis.restaurantmanagement.connection.DbConnect;

public class AdminAccount {
    private int id;
    private String username;
    private String password;

    public int getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }


    public AdminAccount(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public AdminAccount() {
        DbConnect connect =  new DbConnect();
        this.id = connect.getListAccounts().get(0).getId();
        this.username = connect.getListAccounts().get(0).getUsername();
        this.password = connect.getListAccounts().get(0).getPassword();
    }
}

