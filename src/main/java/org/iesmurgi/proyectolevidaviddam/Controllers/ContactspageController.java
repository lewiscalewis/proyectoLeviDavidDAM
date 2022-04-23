package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.HelloApplication;
import org.iesmurgi.proyectolevidaviddam.Middleware.GeneralDecoder;
import org.iesmurgi.proyectolevidaviddam.Middleware.OpenThread;
import org.iesmurgi.proyectolevidaviddam.Middleware.Toast;
import org.iesmurgi.proyectolevidaviddam.Middleware.TokenManager;
import org.iesmurgi.proyectolevidaviddam.models.FriendRequest;
import org.iesmurgi.proyectolevidaviddam.models.User;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.iesmurgi.proyectolevidaviddam.HelloApplication.mainStage;

public class ContactspageController {

    @FXML
    private VBox baseRoot;

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

    @FXML
    void initialize() throws MalformedURLException, InterruptedException {
        ((AnchorPane)baseRoot.getParent()).setLeftAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setTopAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setRightAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot   .getParent()).setBottomAnchor(baseRoot,0.0);

        container.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));

        loadUsers();

        String url = CONSTANT.URL.getUrl()+"/get-friend-requests";
        ArrayList<String[]> params = new ArrayList<>();
        params.add(new String[]{"username", me});
        TokenManager tk = new TokenManager();
        params.add(new String[]{"token", tk.getToken()});
        OpenThread<FriendRequest[]> t = new OpenThread<FriendRequest[]>(url, params, "POST", FriendRequest[].class);
        FriendRequest[] petitions;
        petitions = t.getResult();

        ScrollPane petitionBar = new ScrollPane();
        VBox petitionBox = new VBox();
        petitionBar.setContent(petitionBox);
        petitionBox.setSpacing(10);
        petitionBar.setPrefWidth(Region.USE_COMPUTED_SIZE);
        petitionBox.setPadding(new Insets(10,10,10,10));
        petitionBox.setAlignment(Pos.CENTER);

        petitionBox.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                petitionBar.getViewportBounds().getWidth(), petitionBar.viewportBoundsProperty()));

        for(FriendRequest p : petitions){
            if(p.getState().equals("on hold")){
                VBox vb = new VBox();
                vb.setSpacing(10);
                vb.setAlignment(Pos.CENTER);
                vb.setPadding(new Insets(10,10,5,10));
                vb.setStyle("-fx-border-color: white; -fx-border-width: 2");
                Label msg = new Label("Petición de amistad de "+p.getEmisor());
                msg.setStyle("-fx-font-size: 16; -fx-font-weight: bold");
                HBox hb = new HBox();
                hb.setSpacing(10);
                Button accept = new Button("Aceptar");
                Button decline = new Button("Rechazar");
                hb.getChildren().addAll(accept, decline);
                hb.setAlignment(Pos.CENTER);
                accept.getStyleClass().add("button");
                decline.getStyleClass().add("decline-button");
                vb.getChildren().addAll(msg, hb);
                petitionBox.getChildren().add(vb);

                //buttons events

            }
        }

        hboxContainer.getChildren().add(petitionBar);

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
    void filterByName(KeyEvent event) throws MalformedURLException, InterruptedException {
        loadUsersBySearch();
    }

    @FXML
    void search(ActionEvent event) throws MalformedURLException, InterruptedException {
        loadUsersBySearch();
    }

    private void loadUsersBySearch() throws MalformedURLException, InterruptedException {
        container.getChildren().clear();
        if(!textfieldBrowser.getText().equals("")){
            String url = CONSTANT.URL.getUrl()+"/get-contacts-filter";
            ArrayList<String[]> params = new ArrayList<>();
            params.add(new String[]{"username", me});
            params.add((new String[]{"friend", textfieldBrowser.getText()}));
            params.add(new String[]{"token", tk.getToken()});
            OpenThread<User[]> t = new OpenThread<User[]>(url, params, "POST", User[].class);
            User[] users;
            users = t.getResult();
            System.out.println(Arrays.toString(users));
            for(User u: users){
                VBox userCard = new VBox();
                userCard.setAlignment(Pos.CENTER);
                userCard.maxWidth(300);
                userCard.prefWidth(200);
                userCard.maxHeight(300);
                userCard.getStyleClass().add("userCard");
                userCard.setStyle("-fx-border-color: white; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-width: 2; -fx-background-color: white");
                Label usernameLabel = new Label(u.getUsername());
                usernameLabel.setStyle("-fx-text-fill: black; -fx-fill: black; -fx-font-weight: bold; -fx-font-size: 15");
                Text nameLabel = new Text(u.getName());
                Text surnameLabel = new Text(u.getSurname());
                userCard.getChildren().addAll(usernameLabel, nameLabel, surnameLabel);
                userCard.setSpacing(5);
                userCard.setPadding(new Insets(5, 5, 5, 5));
                container.getChildren().add(userCard);
            }
        }
    }

    void loadUsers() throws MalformedURLException, InterruptedException {
        GeneralDecoder d = new GeneralDecoder();
        String me = d.getUserFromToken();

        container.getChildren().clear();
        if(!textfieldBrowser.getText().equals("")){
            String url = CONSTANT.URL.getUrl()+"/get-contacts";
            ArrayList<String[]> params = new ArrayList<>();
            params.add(new String[]{"username", me});
            params.add(new String[]{"token", tk.getToken()});
            OpenThread<User[]> t = new OpenThread<User[]>(url, params, "POST", User[].class);
            User[] users;
            users = t.getResult();
            System.out.println(Arrays.toString(users));
            for(User u: users){
                VBox userCard = new VBox();
                userCard.setAlignment(Pos.CENTER);
                userCard.maxWidth(300);
                userCard.prefWidth(200);
                userCard.maxHeight(300);
                userCard.getStyleClass().add("userCard");
                userCard.setStyle("-fx-border-color: white; -fx-border-radius: 5; -fx-background-radius: 5; -fx-border-width: 2; -fx-background-color: white");
                Label usernameLabel = new Label(u.getUsername());
                usernameLabel.setStyle("-fx-text-fill: black; -fx-fill: black; -fx-font-weight: bold; -fx-font-size: 15");
                Text nameLabel = new Text(u.getName());
                Text surnameLabel = new Text(u.getSurname());
                userCard.getChildren().addAll(usernameLabel, nameLabel, surnameLabel);
                userCard.setSpacing(5);
                userCard.setPadding(new Insets(5, 5, 5, 5));
                container.getChildren().add(userCard);
            }
        }
    }

}
