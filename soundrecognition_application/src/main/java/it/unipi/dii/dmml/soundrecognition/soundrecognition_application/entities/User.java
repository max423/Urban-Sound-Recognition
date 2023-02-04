package it.unipi.dii.dmml.soundrecognition.soundrecognition_application.entities;
import it.unipi.dii.dmml.soundrecognition.soundrecognition_application.utilities.DatabaseUtils;

public class User {
    private String fullName;
    private String email;
    private String password;
    private String username;

    public User(String fullName, String email, String password, String username){
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean addUser(){
        return DatabaseUtils.addUserToDB(this);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
