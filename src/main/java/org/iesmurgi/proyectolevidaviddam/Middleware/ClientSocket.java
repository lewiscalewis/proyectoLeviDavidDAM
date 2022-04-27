package org.iesmurgi.proyectolevidaviddam.Middleware;

import io.socket.client.IO;
import io.socket.client.Socket;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.models.Message;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;


public class ClientSocket {

    Socket socket;
    Socket socketReceiver;
    ArrayList<Message> messages;
    Message message;
    String room;

    public ClientSocket(String room){
        this.room = room;
    }

    public ClientSocket(){

    }

    public Message init() throws IOException, URISyntaxException {
        IO.Options options = IO.Options.builder()
                .setForceNew(false)
                .build();

        socketReceiver = IO.socket(URI.create(CONSTANT.SOCKET.getUrl()), options);
        socketReceiver.emit("start-room", room);


        socketReceiver.on ("message", objetos -> {
//                    for(Object o : objetos){
//                        message = Message.class.cast(o);
//                    }
            //CONVERTIR TIPO CON GSON PARA PROBAR
                    System.out.println("MENSAJE RECIBIDO->" + Arrays.toString(objetos));
                }
        );

        socketReceiver.connect();
        setMessages();
        return message;
    }

    private void setMessages(){
        messages.add(message);
    }

    public ArrayList<Message> getMessagesAsArray(){
        return messages;
    }

    public void sendMessage(Message message) {
        IO.Options options = IO.Options.builder()
                //.setPath("/chat message")
                .setForceNew(false)
                .build();

        socket = IO.socket(URI.create(CONSTANT.SOCKET.getUrl()), options);
        socket.emit("message", message);

        socket.connect();
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
