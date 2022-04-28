package org.iesmurgi.proyectolevidaviddam.Controllers;


import com.google.gson.Gson;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ChatController {

    @FXML
    public ScrollPane chatContainer;

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

    //Cuando la clase chatview se lanza, el usuario logeado hace join en la sala de chat pasándole a socketio
    //su id de sala, para ello primero tiene que conseguirlo haciendo uso de la API donde habrá un escuchador
    //que se encargue de conseguir el chat en cuestión a partir de los dos username (el propio y el del receptor)
    //y si no existe lo crea y devuelve el id de chat que será el room en socketio
    String chat;
    ArrayList<Message> messages = new ArrayList<>();
    ClientSocket c;

    @FXML
    void initialize() throws IOException, InterruptedException {
        Platform.runLater(()->{
            String url = CONSTANT.URL.getUrl()+"/chatID";
            Platform.setImplicitExit(false);
            Platform.runLater(()->{
                c = new ClientSocket(this);
                try {
                    Requester<String> req = new Requester<>(url, Requester.Method.POST, String.class);
                    req.addParam("token", tk.getToken());
                    req.addParam("username1", gd.getUserFromToken());
                    req.addParam("username2", contact.getUsername());
                    chat = req.execute();
                    c.setRoom(chat);
                    c.init();
                    getMessages();
                    scrollDown();
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            });
        });

        chatBox.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                chatContainer.getViewportBounds().getWidth(), chatContainer.viewportBoundsProperty()));
    }

    @FXML
    public void scrollDown() {
        chatContainer.setVvalue(1.0);
    }

    @FXML
    void sendMessage(MouseEvent event) throws IOException {
        String mess = textfieldMessages.textProperty().get();
        Date date = new Date();
        String formatedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
        message = new Message(mess, gd.getUserFromToken(), contact.getUsername(), formatedDate);
        c.sendMessage(message.toString());
        textfieldMessages.clear();
        messages = c.getMessagesAsArray();
        System.out.println("Mensajes: "+messages);
        printMessages(message);
        saveMessages(message);
        chatContainer.layout();
        scrollDown();
    }

    @FXML
    void sendMessagesByKey(ActionEvent event) throws IOException {
        String mess = textfieldMessages.textProperty().get();
        Date date = new Date();
        String formatedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
        message = new Message(mess, gd.getUserFromToken(), contact.getUsername(), formatedDate);
        c.sendMessage(message.toString());
        textfieldMessages.clear();
        messages = c.getMessagesAsArray();
        System.out.println("Mensajes: "+messages);
        printMessages(message);
        saveMessages(message);
        chatContainer.layout();
        scrollDown();
    }

    public void setContactData(User contact){
        this.contact = contact;
    }

    public void printMessages(Message message){
        Platform.runLater(()->{
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
            Label usernameLabel = new Label(message.getEmisor());
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
            if(!message.getEmisor().equals(gd.getUserFromToken())){
                messageCard.setStyle("-fx-background-color: #e8e8e8");
            }else{
                messageCard.setStyle("-fx-background-color: #befabe");
            }
            messageCard.setPadding(new Insets(5, 5, 5, 5));
            chatBox.getChildren().add(messageCard);
            chatBox.setAlignment(Pos.CENTER);
            chatBox.setPadding(new Insets(10,10,20,10));
            chatBox.layout();
            chatContainer.layout();
            scrollDown();
        });
    }

    public void saveMessages(Message message) throws IOException {
        Platform.runLater(()->{
            try {
                Requester<String> req = new Requester<>(CONSTANT.URL.getUrl()+"/save-message", Requester.Method.POST, null);
                req.addParam("token", tk.getToken());
                req.addParam("emisor", gd.getUserFromToken());
                req.addParam("receptor", contact.getUsername());
                req.addParam("message", message.getMessage());
                req.addParam("date", message.getDatetime());
                req.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public Message[] getMessages() throws IOException {
        Message[] messages1;
        Requester<Message[]> req = new Requester<>(CONSTANT.URL.getUrl()+"/get-messages", Requester.Method.POST, Message[].class);
        req.addParam("token", tk.getToken());
        req.addParam("username1", gd.getUserFromToken());
        req.addParam("username2", contact.getUsername());
        messages1 = req.execute();

        for(Message m : messages1){
            printMessages(m);
        }

        return messages1;
    }

}
