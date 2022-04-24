package org.iesmurgi.proyectolevidaviddam.Controllers;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.Middleware.OpenThread;
import org.iesmurgi.proyectolevidaviddam.Middleware.ClientSocket;
import org.iesmurgi.proyectolevidaviddam.Middleware.TokenManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class ChatController {

    @FXML
    private ScrollPane chatContainer;

    @FXML
    private VBox vboxContainer;

    @FXML
    private TextField textfieldMessages;

    ClientSocket c = new ClientSocket();
    //Cuando la clase chatview se lanza, el usuario logeado hace join en la sala de chat pasándole a socketio
    //su id de sala, para ello primero tiene que conseguirlo haciendo uso de la API donde habrá un escuchador
    //que se encargue de conseguir el chat en cuestión a partir de los dos username (el propio y el del receptor)
    //y si no existe lo crea y devuelve el id de chat que será el room en socketio
    String chat;

    @FXML
    void initialize() throws IOException, InterruptedException {
        Platform.runLater(()->{
            try{
                TokenManager tk = new TokenManager();
                String url = CONSTANT.URL.getUrl()+"/chatID";
                ArrayList<String[]> params = new ArrayList<>();
                params.add(new String[]{"username1", tk.getToken()});
                params.add(new String[]{"username2",  "elias"});
                OpenThread<String> t = new OpenThread<>(url, params, "POST", String.class);
                chat = t.getResult();
                c.start();
                c.setRoom(chat);
            }catch (MalformedURLException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void scrollDown(MouseEvent event) {

    }

    @FXML
    void sendMessage(MouseEvent event) {
        String mess = textfieldMessages.textProperty().get();
        System.out.println(mess);
        textfieldMessages.clear();
    }

    @FXML
    void sendMessagesByKey(ActionEvent event) {
        String mess = textfieldMessages.textProperty().get();
        c.sendMessage(mess);
        textfieldMessages.clear();
    }

}
