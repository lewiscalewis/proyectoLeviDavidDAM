package org.iesmurgi.proyectolevidaviddam.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Message  {

    String message;
    String username;
    Date datetime = new Date();
    String datetimeString;

    public Message(){

    }

    public Message(String message, String username, String datetimeString){
        this.message = message;
        this.username = username;
        this.datetimeString = datetimeString;
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

    public String getDatetime() {
        return parseDate();
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString(){
        return  "{"
                +"\"message\":\""+ message+ "\","
                +"\"username\":\""+ username + "\","
                +"\"datetimeString\":\""+ parseDate() + "\""
                +"}";
    }

    private String parseDate() {
        String formatedDate = new SimpleDateFormat("hh:mm:ss  dd-mm-yyyy").format(datetime);
        return formatedDate;
    }
}
