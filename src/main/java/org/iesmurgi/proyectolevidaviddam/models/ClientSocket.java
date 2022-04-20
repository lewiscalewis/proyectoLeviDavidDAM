package org.iesmurgi.proyectolevidaviddam.models;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;


public class ClientSocket extends Thread {

    Socket socket;
    Socket socketReceiver;
    ArrayList<String> messages;
    String room;

    public ClientSocket(String room){
        this.room = room;
    }

    public ClientSocket(){

    }

    @Override
    public void run() {
        try {
            init();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        super.run();
    }

    public void init() throws IOException, URISyntaxException {
        IO.Options options = IO.Options.builder()
                .setForceNew(false)
                .build();

        socketReceiver = IO.socket(URI.create(CONSTANT.SOCKET.getUrl()), options);

        socketReceiver.emit("start-room", room);

        socketReceiver.on ("message", objetos ->
                System.out.println ("cliente: recibió msg->" + Arrays.toString (objetos))
        );

        socketReceiver.connect();
    }

    public ArrayList<String> getMessagesAsArray(){
        return messages;
    }

    public void sendMessage(String message) {
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
