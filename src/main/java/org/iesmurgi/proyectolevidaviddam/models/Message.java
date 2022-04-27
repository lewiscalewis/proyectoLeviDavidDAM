package org.iesmurgi.proyectolevidaviddam.models;

import java.util.Date;

public class Message  {

    String message;
    String username;
    Date datetime;

    public Message(String message, String username, Date date){
        this.message = message;
        this.username = username;
        this.datetime = date;
    }

    public Message(){

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString(){
        return  "Username: " + username+
                ", Mensaje: " + message+
                ", Fecha: " + datetime;
    }
}
