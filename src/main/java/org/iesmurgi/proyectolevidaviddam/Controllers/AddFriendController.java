package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.Middleware.*;
import org.iesmurgi.proyectolevidaviddam.models.FriendRequest;
import org.iesmurgi.proyectolevidaviddam.models.User;

import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.iesmurgi.proyectolevidaviddam.HelloApplication.mainStage;

public class AddFriendController {

    @FXML
    private VBox container;

    @FXML
    private TextField textfieldBrowser;

    @FXML
    private ScrollPane scrollPane;

    TokenManager tk = new TokenManager();

    @FXML
    void initialize() throws IOException, InterruptedException {
        ((AnchorPane)container.getParent()).setLeftAnchor(container,0.0);
        ((AnchorPane)container.getParent()).setTopAnchor(container,0.0);
        ((AnchorPane)container.getParent()).setRightAnchor(container,0.0);
        ((AnchorPane)container.getParent()).setBottomAnchor(container,0.0);

        //Código IMPORTANTE: bindea el scroll con los tamaños de container para que siempre se expanda el contenedor hijo del scroll pane
        container.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));
//        container.minHeightProperty().bind(Bindings.createDoubleBinding(() ->
//                scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));
        Platform.runLater(()->{
            try {
                loadUsers();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void filterByName(KeyEvent event) throws MalformedURLException, InterruptedException, ConnectException {
        //método que necesita la previa carga de datos de los usuarios de la aplicación que no sean tus amigos para desde un ArrayList
        //se cree una lista de sugerencias de posibles usuarios
        Platform.runLater(()->{
            try {
                loadUsersBySearch();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void search(ActionEvent event) throws MalformedURLException, InterruptedException, ConnectException {
        //recogemos todos los usuarios que coincidan con lo escrito en el textfield
        Platform.runLater(()->{
            try {
                loadUsersBySearch();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    void loadUsers() throws IOException, InterruptedException {

        container.getChildren().clear();

        String url = CONSTANT.URL.getUrl()+"/get-noFriends";
        Requester<User[]> requester = new Requester<>(url, Requester.Method.POST, User[].class);
        requester.addParam("token", tk.getToken());
        requester.addParam("username", new GeneralDecoder().getUserFromToken());
        getUsers(requester.execute());
        System.out.println("Obteniendo usuarios ...");
    }

    private void getUsers(User[] users) {
        GeneralDecoder d = new GeneralDecoder();
        String me = d.getUserFromToken();

        Platform.runLater(()->{
            Arrays.stream(users).filter(user -> user.getUsername() != new GeneralDecoder().getUserFromToken()).forEach((u)->{
                try {
                    String url1 = CONSTANT.URL.getUrl()+"/download-image";
                    FileGetter fileGetter = new FileGetter(url1);
                    fileGetter.addParam("username", u.getUsername());
                    fileGetter.addParam("token", tk.getToken());

                    ImageView imgView = fileGetter.getImage();
                    imgView.setFitHeight(70);
                    imgView.setFitWidth(70);

                    VBox cardContainer = new VBox();
                    cardContainer.setAlignment(Pos.CENTER);
                    cardContainer.maxWidth(900);
                    cardContainer.prefWidth(700);
                    cardContainer.maxHeight(400);
                    VBox userCard = new VBox();
                    userCard.setAlignment(Pos.CENTER);
                    userCard.maxWidth(900);
                    userCard.prefWidth(700);
                    userCard.maxHeight(400);
                    userCard.getStyleClass().add("userCard");
                    Label usernameLabel = new Label("Usuario:\t"+u.getUsername());
                    usernameLabel.setStyle("-fx-text-fill: black; -fx-fill: black; -fx-font-weight: bold; -fx-font-size: 15");
                    Text nameLabel = new Text("Nombre:\t"+u.getName());
                    Text surnameLabel = new Text("Apellidos:\t"+u.getSurname());
                    Button addToFriend = new Button("Añadir a lista de amigos");
                    //addToFriend.setStyle("-fx-background-color: #63c963; -fx-fill: white; -fx-text-fill: white; -fx-font-weight: bold");
                    addToFriend.getStyleClass().add("button");
                    userCard.getChildren().addAll(imgView, usernameLabel, nameLabel, surnameLabel);
                    cardContainer.getChildren().addAll(userCard, addToFriend);
                    cardContainer.setSpacing(20);
                    userCard.setSpacing(8);
                    userCard.setPadding(new Insets(5, 5, 5, 5));
                    container.getChildren().add(cardContainer);

                    //button event
                    addToFriend.setOnAction(event -> {
                        String url2 = CONSTANT.URL.getUrl()+"/friend-request";
                        try {
                            Requester<FriendRequest> requester = new Requester<>(url2, Requester.Method.POST, FriendRequest.class);
                            requester.addParam("emisor", me);
                            requester.addParam("receptor", u.getUsername());
                            requester.addParam("token", tk.getToken());
                            requester.execute();
                            String toastMsg = "Petición enviada !!";
                            int toastMsgTime = 2800; //3.5 seconds
                            int fadeInTime = 500; //0.5 seconds
                            int fadeOutTime= 500; //0.5 seconds
                            Toast.makeText(mainStage, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
                            cardContainer.getChildren().clear();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        });
    }

    void loadUsersBySearch() throws IOException, InterruptedException {

        GeneralDecoder d = new GeneralDecoder();
        String me = d.getUserFromToken();

        container.getChildren().clear();
        if(!textfieldBrowser.getText().equals("")){
            String url = CONSTANT.URL.getUrl()+"/find-users";
            Requester<User[]> requester = new Requester<>(url, Requester.Method.POST, User[].class);
            requester.addParam("username", textfieldBrowser.getText());
            requester.addParam("me", me);
            requester.addParam("token", tk.getToken());
            getUsers(requester.execute());
        }else{
            Platform.runLater(()->{
                try {
                    loadUsers();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
