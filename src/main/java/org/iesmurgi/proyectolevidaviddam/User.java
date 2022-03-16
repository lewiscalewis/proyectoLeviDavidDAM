package org.iesmurgi.proyectolevidaviddam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    
    @SerializedName("Nombre")
    @Expose
    String nombre;

    public User(String nombre){
        this.nombre = nombre;
    }

    public String getnombre(){
        return nombre;
    }
    public void setnombre(String nombre){
        this.nombre=nombre;
    }

    @Override
    public String toString(){
        return "Mi nombre es: " + this.nombre;
    }
}
