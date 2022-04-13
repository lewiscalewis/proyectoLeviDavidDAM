package org.iesmurgi.proyectolevidaviddam.models;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;


public class ClientSocket extends Thread {

    Socket socket;
    Socket socketReceiver;

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

        socketReceiver = IO.socket(URI.create(CONSTANT.URL.getUrl()), options);

        socketReceiver.on("chat message", args -> {
            System.out.println("Mensaje recibido: " + Arrays.toString(args));
        });

        socketReceiver.connect();
    }

    public void sendMessage(String message) {
        String room;
        String emisorId;
        String receptorId;
        String socketId;
        String created;
        String receptorSocketId;

        IO.Options options = IO.Options.builder()
                //.setPath("/chat message")
                .setForceNew(false)
                .build();

        socket = IO.socket(URI.create(CONSTANT.URL.getUrl()), options);
        socket.emit("chat message", message);
        //socket.emit("message", new String[]{ room, emisorId, receptorId, socketId, created, receptorSocketId });
        socket.connect();
    }
}
