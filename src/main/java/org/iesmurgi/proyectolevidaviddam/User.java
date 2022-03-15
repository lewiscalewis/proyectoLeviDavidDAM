package org.iesmurgi.proyectolevidaviddam;


import java.io.Serializable;

public class User implements Serializable {
    String nombre;

    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
}
