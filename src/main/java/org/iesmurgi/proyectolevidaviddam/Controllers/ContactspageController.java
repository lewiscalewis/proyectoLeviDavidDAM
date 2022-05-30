package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.HelloApplication;
import org.iesmurgi.proyectolevidaviddam.Middleware.FileGetter;
import org.iesmurgi.proyectolevidaviddam.Middleware.GeneralDecoder;
import org.iesmurgi.proyectolevidaviddam.Middleware.Requester;
import org.iesmurgi.proyectolevidaviddam.Middleware.TokenManager;
import org.iesmurgi.proyectolevidaviddam.models.Notifications;
import org.iesmurgi.proyectolevidaviddam.models.User;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class ContactspageController {

    @FXML
    private StackPane baseRoot;

    @FXML
    private FlowPane container;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField textfieldBrowser;

    @FXML
    private HBox hboxContainer;

    TokenManager tk = new TokenManager();

    GeneralDecoder d = new GeneralDecoder();
    String me = d.getUserFromToken();

    public ContactspageController() throws IOException {
    }

    @FXML
    void initialize() throws IOException, InterruptedException {
        ((AnchorPane)baseRoot.getParent()).setLeftAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setTopAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setRightAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setBottomAnchor(baseRoot,0.0);

        baseRoot.setTranslateZ(-10);
        baseRoot.setAlignment(Pos.CENTER);


        container.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));

        hboxContainer.setSpacing(50);

        loadUsers();

    }

    void reload(){
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("contactspage.fxml"));
        baseRoot.getChildren().clear();
        Pane root = null;
        VBox pageRoot = (VBox) baseRoot.getParent();

        try {
            root = (fxmlLoader.load());
            baseRoot.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addFriend(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-friend-view.fxml"));
        baseRoot.getChildren().clear();
        Pane root = null;
        VBox pageRoot = (VBox) baseRoot.getParent();
        pageRoot.setAlignment(Pos.CENTER);

        try {
            root = (fxmlLoader.load());
            baseRoot.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void filterByName(KeyEvent event) {
        Platform.runLater(()->{
            try {
                loadUsersBySearch();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void search(ActionEvent event) {
        Platform.runLater(()->{
            try {
                loadUsers();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadUsersBySearch() throws IOException, InterruptedException {
        container.getChildren().clear();
        if(!textfieldBrowser.getText().equals("")){

            String url = CONSTANT.URL.getUrl()+"/get-contacts-filter";
            Requester<User[]> r = new Requester(url, Requester.Method.POST, User[].class);
            r.addParam("username", me);
            r.addParam("friend", textfieldBrowser.getText());
            r.addParam("token", tk.getToken());
            User[] users;
            users = r.execute();

            iterateUsers(users);
        }else {
            loadUsers();
        }
    }

    private void iterateUsers(User[] users) throws IOException {
        Platform.runLater(()->{
            Arrays.stream(users).filter(user -> !Objects.equals(user.getUsername(), new GeneralDecoder().getUserFromToken())).forEach((u)->{
                String url1 = CONSTANT.URL.getUrl()+"/download-image";
                FileGetter fileGetter = null;
                try {
                    fileGetter = new FileGetter(url1);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                fileGetter.addParam("username", u.getUsername());
                fileGetter.addParam("token", tk.getToken());

                System.out.println(u.toString());
                VBox userCard = new VBox();
                userCard.setAlignment(Pos.CENTER);
                userCard.maxWidth(300);
                userCard.prefWidth(300);
                userCard.maxHeight(300);
                userCard.getStyleClass().add("userCard2");
                //.setStyle("" +
//                        "-fx-border-color: white; " +
//                        "-fx-border-radius: 5; " +
//                        "-fx-background-radius: 5; " +
//                        "-fx-border-width: 2; " +
//                        "-fx-background-color: white;" +
//                        "-fx-padding: 20");
                ImageView imgView = null;
                try {
                    imgView = fileGetter.getImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imgView.setFitHeight(60);
                imgView.setFitWidth(60);
                Label usernameLabel = new Label("Usuario: "+u.getUsername());
                usernameLabel.setStyle("" +
                        "-fx-text-fill: black; " +
                        "-fx-fill: black; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 15;");
                Text nameLabel = new Text("Nombre: "+u.getName());
                Text surnameLabel = new Text("Apellidos: "+u.getSurname());
                Text state = new Text("Estado: "+u.getState());
                Text online = new Text(u.isOnline() == 1 ? "En línea" : "Desconectado");
                online.setStyle(u.isOnline() == 1 ? "-fx-fill: lightgreen; -fx-font-weight: bold" : "-fx-fill: red; -fx-font-weight: bold");
                Requester<Notifications[]> req = null;
                try {
                    req = new Requester<>("http://tux.iesmurgi.org:11230/get-notifications", Requester.Method.POST, Notifications[].class);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                req.addParam("token", new TokenManager().getToken());
                req.addParam("emisor", u.getUsername());
                req.addParam("receptor", me);
                Notifications[] notifications = new Notifications[0];
                try {
                    notifications = req.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Text not_readed;
                if(notifications.length > 0){
                    not_readed = new Text(notifications[0].getNotification()+" mensajes sin leer");
                    not_readed.setStyle("-fx-fill: red; -fx-font-weight: bold");
                    userCard.getChildren().addAll(imgView, usernameLabel, nameLabel, surnameLabel, state, online, not_readed);
                }else{
                    userCard.getChildren().addAll(imgView, usernameLabel, nameLabel, surnameLabel, state, online);
                }
                userCard.setSpacing(5);
                userCard.setPadding(new Insets(5, 5, 5, 5));
                container.getChildren().add(userCard);
                container.setAlignment(Pos.CENTER);
                container.setPadding(new Insets(10,10,10,10));
                openChat(userCard, u);
            });
        });
    }

    void loadUsers() throws IOException, InterruptedException {
        GeneralDecoder d = new GeneralDecoder();
        String me = d.getUserFromToken();

        container.getChildren().clear();

        String url = CONSTANT.URL.getUrl()+"/get-contacts";
        Requester<User[]> r = new Requester(url, Requester.Method.POST, User[].class);
        r.addParam("username", me);
        r.addParam("token", tk.getToken());
        User[] users;
        users = r.execute();

        System.out.println("Ejecutando get contacts: ");
        iterateUsers(users);

    }

    void openChat(VBox container, User contact){
        container.setOnMouseClicked((event)->{
                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(0.4));
                slide.setNode(baseRoot);
                baseRoot.setAlignment(Pos.CENTER);
                //((HBox) event.getTarget()).setTranslateY(-6);


                slide.setToX(6000);
                slide.play();
                slide.setOnFinished((ev -> {

                baseRoot.setTranslateX(-6000);
                TranslateTransition slide2 = new TranslateTransition();
                slide2.setDuration(Duration.seconds(0.4));
                slide2.setNode(baseRoot);
                //((HBox) event.getTarget()).setTranslateY(-6);


                slide2.setToX(0);

                try {
                    baseRoot.setAlignment(Pos.CENTER);
                    baseRoot.getChildren().clear();
                    FXMLLoader rootFxmlLoader=new FXMLLoader(
                            HelloApplication.class.getResource(
                                    "chat-view.fxml"
                            )
                    );
                    VBox pageRoot = (VBox) baseRoot.getParent();
                    pageRoot.setAlignment(Pos.CENTER);
                    Pane root = rootFxmlLoader.load();
                    baseRoot.getChildren().add(root);

                    ChatController controlador = rootFxmlLoader.getController();
                    controlador.setContactData(contact);


                    //((Stage)root.getScene().getWindow()).setMinWidth(900);
                    // ((Stage)root.getScene().getWindow()).setMinHeight(850);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                slide2.play();
                slide2.setOnFinished((event2)->{
                });
            }));
        });
    }

}
