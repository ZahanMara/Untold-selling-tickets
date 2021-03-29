package model;

public class Users {

    private int iduser;
    private String username;
    private String password;
    private String type;

    public Users(int id, String username, String password, String type) {
        this.iduser = id;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public Users(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public int getId() {
        return iduser;
    }

    public void setId(int id) {
        this.iduser = id;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
