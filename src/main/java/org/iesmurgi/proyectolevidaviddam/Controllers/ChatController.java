package org.iesmurgi.proyectolevidaviddam.Controllers;


import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.Middleware.*;
import org.iesmurgi.proyectolevidaviddam.models.Message;
import org.iesmurgi.proyectolevidaviddam.models.User;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

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
    Message message;
    TokenManager tk = new TokenManager();
    GeneralDecoder gd = new GeneralDecoder();

    ClientSocket c = new ClientSocket();
    //Cuando la clase chatview se lanza, el usuario logeado hace join en la sala de chat pasándole a socketio
    //su id de sala, para ello primero tiene que conseguirlo haciendo uso de la API donde habrá un escuchador
    //que se encargue de conseguir el chat en cuestión a partir de los dos username (el propio y el del receptor)
    //y si no existe lo crea y devuelve el id de chat que será el room en socketio
    String chat;
    ArrayList<Message> messages = new ArrayList<>();

    @FXML
    void initialize() throws IOException, InterruptedException {
        Platform.runLater(()->{
            String url = CONSTANT.URL.getUrl()+"/chatID";
            Platform.runLater(()->{
                try {
                    Requester<String> req = new Requester<>(url, Requester.Method.POST, String.class);
                    req.addParam("token", tk.getToken());
                    req.addParam("username1", gd.getUserFromToken());
                    req.addParam("username2", contact.getUsername());
                    chat = req.execute();
                    c.setRoom(chat);
                    c.init();
                } catch (IOException | URISyntaxException e) {
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
        Date date = new Date();
        message = new Message(mess, gd.getUserFromToken(), date.toString());
        message.setDatetime(date);
        c.sendMessage(message.toString());
        textfieldMessages.clear();
        messages = c.getMessagesAsArray();
        System.out.println("Mensajes: "+messages);
        printMessages(message);
    }

    @FXML
    void sendMessagesByKey(ActionEvent event) {
        String mess = textfieldMessages.textProperty().get();
        Date date = new Date();
        message = new Message(mess, gd.getUserFromToken(), date.toString());
        message.setDatetime(date);
        c.sendMessage(message.toString());
        textfieldMessages.clear();
        messages = c.getMessagesAsArray();
        System.out.println("Mensajes: "+messages);
        printMessages(message);
    }

    public void setContactData(User contact){
        this.contact = contact;
    }

    public void printMessages(Message message){
        VBox messageCard = new VBox();
        messageCard.setAlignment(Pos.CENTER);
        messageCard.maxWidth(300);
        messageCard.maxHeight(300);
        messageCard.getStyleClass().add("messageCard2");
//            messageCard.setStyle("" +
//                    "-fx-border-color: white; " +
//                    "-fx-border-radius: 5; " +
//                    "-fx-background-radius: 5; " +
//                    "-fx-border-width: 2; " +
//                    "-fx-background-color: white;" +
//                    "-fx-padding: 20");
        Label usernameLabel = new Label(message.getUsername());
        usernameLabel.setStyle("" +
                "-fx-text-fill: black; " +
                "-fx-fill: black; " +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 15;");
        Text bodyMessage = new Text(message.getMessage());
        bodyMessage.setStyle("-fx-font-size: 14");
        Text footDate = new Text(message.getDatetime());
        footDate.setStyle("-fx-font-size: 9");
        messageCard.getChildren().addAll(usernameLabel, bodyMessage, footDate);
        messageCard.setSpacing(5);
        if(!message.getUsername().equals(gd.getUserFromToken())){
            messageCard.setStyle("-fx-background-color: whitesmoke");
        }else{
            messageCard.setStyle("-fx-background-color: #befabe");
        }
        messageCard.setPadding(new Insets(5, 5, 5, 5));
        chatBox.getChildren().add(messageCard);
        chatBox.setAlignment(Pos.CENTER);
        chatBox.setPadding(new Insets(10,10,10,10));
    }

}
