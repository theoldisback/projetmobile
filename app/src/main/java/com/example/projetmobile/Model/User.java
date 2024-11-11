package com.example.projetmobile.Model;


import java.util.Date;

public class User

{
     private int id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String adress;
    private Date birthdate;
    private String email;
    private byte[] image;  // This will hold the image data as a byte array

    public User() {
    }



    public User(String username, String password, String firstname, String lastname, String adress, Date birthdate, String email) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.adress = adress;
        this.birthdate = birthdate;
        this.email = email;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", adress='" + adress + '\'' +
                ", birthdate=" + birthdate +
                ", email='" + email + '\'' +
                '}';
    }

    public User(String username, String password, String firstname, String lastname, String adress, Date birthdate, String email, byte[] image) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.adress = adress;
        this.birthdate = birthdate;
        this.email = email;
        this.image = image;
    }
}
