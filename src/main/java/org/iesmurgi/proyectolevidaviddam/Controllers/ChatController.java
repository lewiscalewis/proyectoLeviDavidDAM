package org.iesmurgi.proyectolevidaviddam.Controllers;


import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.Middleware.*;
import org.iesmurgi.proyectolevidaviddam.models.User;

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

    @FXML
    private VBox chatBox;

    User contact;

    ClientSocket c = new ClientSocket();
    //Cuando la clase chatview se lanza, el usuario logeado hace join en la sala de chat pasándole a socketio
    //su id de sala, para ello primero tiene que conseguirlo haciendo uso de la API donde habrá un escuchador
    //que se encargue de conseguir el chat en cuestión a partir de los dos username (el propio y el del receptor)
    //y si no existe lo crea y devuelve el id de chat que será el room en socketio
    String chat;

    @FXML
    void initialize() throws IOException, InterruptedException {
        Platform.runLater(()->{
            TokenManager tk = new TokenManager();
            GeneralDecoder gd = new GeneralDecoder();
            String url = CONSTANT.URL.getUrl()+"/chatID";
            Platform.runLater(()->{
                try {
                    Requester<String> req = new Requester<>(url, Requester.Method.POST, String.class);
                    req.addParam("token", tk.getToken());
                    req.addParam("username1", gd.getUserFromToken());
                    req.addParam("username2", contact.getUsername());
                    chat = req.execute();
                    c.setRoom(chat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        chatBox.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                chatContainer.getViewportBounds().getWidth(), chatContainer.viewportBoundsProperty()));
    }

    @FXML
    void scrollDown(MouseEvent event) {
        chatContainer.setVvalue(0);
    }

    @FXML
    void sendMessage(MouseEvent event) {
        String mess = textfieldMessages.textProperty().get();
        c.sendMessage(mess);
        textfieldMessages.clear();
    }

    @FXML
    void sendMessagesByKey(ActionEvent event) {
        String mess = textfieldMessages.textProperty().get();
        c.sendMessage(mess);
        textfieldMessages.clear();
    }

    public void setContactData(User contact){
        this.contact = contact;
    }

}
