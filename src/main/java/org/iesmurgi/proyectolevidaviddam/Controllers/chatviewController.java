package org.iesmurgi.proyectolevidaviddam.Controllers;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.iesmurgi.proyectolevidaviddam.models.ClientSocket;

import java.io.IOException;

public class chatviewController{

    @FXML
    private ScrollPane chatContainer;

    @FXML
    private VBox vboxContainer;

    @FXML
    private TextField textfieldMessages;

    ClientSocket c = new ClientSocket();

    @FXML
    void initialize() throws IOException {
        c.start();
    }

    @FXML
    void scrollDown(MouseEvent event) {

    }

    @FXML
    void sendMessage(MouseEvent event) {
        String mess = textfieldMessages.textProperty().get();
        System.out.println(mess);
        c.sendMessage(mess);
    }

    @FXML
    void sendMessagesByKey(ActionEvent event) {
        String mess = textfieldMessages.textProperty().get();
        System.out.println(mess);
        c.sendMessage(mess);
    }

}
