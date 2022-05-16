package org.iesmurgi.proyectolevidaviddam.models;


public class User {

    String name;
    String surname;
    String username;
    String password;
    String profileimage;
    String email;

    public User(String name, String surname, String username, String password, String email, String profileimage){
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.profileimage = profileimage;
    }

    public User(){

    }

    public User(String username){
        this.username = username;
    }



    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return email;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public String getProfileimage() {
        return profileimage;
    }
}
