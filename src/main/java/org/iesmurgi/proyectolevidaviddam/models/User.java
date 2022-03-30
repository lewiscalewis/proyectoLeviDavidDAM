package org.iesmurgi.proyectolevidaviddam.models;

public class User {

    String name;
    String surname;
    String username;
    String password;
    String email;

    public User(String name, String surname, String username, String password, String email){

    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
