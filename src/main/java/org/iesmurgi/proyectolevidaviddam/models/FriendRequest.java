package org.iesmurgi.proyectolevidaviddam.models;


//Modelo de una Petición de amistad
public class FriendRequest {

    int id;     //request id
       
    String emisor;  //Usuario que envía la petición
    String receptor;    //Usuario a quien es enviada la petición
    String state;   //Para saber si la petición ha sido aceptada, denegada o aun sigue pendiente.
    
    public FriendRequest(int id, String emisor, String receptor, String state){
        this.id = id;
        this.emisor = emisor;
        this.receptor = receptor;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public String getEmisor() {
        return emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
