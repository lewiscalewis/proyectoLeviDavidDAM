package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.Middleware.GeneralDecoder;
import org.iesmurgi.proyectolevidaviddam.Middleware.OpenThread;
import org.iesmurgi.proyectolevidaviddam.Middleware.TokenManager;
import org.iesmurgi.proyectolevidaviddam.models.User;

import java.io.InputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;

public class AddFriendController {

    @FXML
    private VBox container;

    @FXML
    private TextField textfieldBrowser;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    void initialize(){
        ((AnchorPane)container.getParent()).setLeftAnchor(container,0.0);
        ((AnchorPane)container.getParent()).setTopAnchor(container,0.0);
        ((AnchorPane)container.getParent()).setRightAnchor(container,0.0);
        ((AnchorPane)container.getParent()).setBottomAnchor(container,0.0);

        //Código IMPORTANTE: bindea el scroll con los tamaños de container para que siempre se expanda el contenedor hijo del scroll pane
        container.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));
//        container.minHeightProperty().bind(Bindings.createDoubleBinding(() ->
//                scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));
    }

    @FXML
    void filterByName(KeyEvent event) throws MalformedURLException, InterruptedException, ConnectException {
        //método que necesita la previa carga de datos de los usuarios de la aplicación que no sean tus amigos para desde un ArrayList
        //se cree una lista de sugerencias de posibles usuarios
        loadUsers();
    }

    @FXML
    void search(ActionEvent event) throws MalformedURLException, InterruptedException, ConnectException {
        //recogemos todos los usuarios que coincidan con lo escrito en el textfield
        loadUsers();
    }

    void loadUsers() throws MalformedURLException, InterruptedException {

        container.getChildren().clear();
        if(!textfieldBrowser.getText().equals("")){
            String url = CONSTANT.URL.getUrl()+"/find-users";
            ArrayList<String[]> params = new ArrayList<>();
            params.add(new String[]{"username", textfieldBrowser.getText()});
            TokenManager tk = new TokenManager();
            params.add(new String[]{"token", tk.getToken()});
            OpenThread<User[]> t = new OpenThread<User[]>(url, params, "POST", User[].class);
            User[] users;
            users = t.getResult();
            System.out.println(Arrays.toString(users));
            for(User u: users){
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
                userCard.setStyle("-fx-border-color: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-border-width: 2; -fx-background-color: white");
                Label usernameLabel = new Label(u.getUsername());
                usernameLabel.setStyle("-fx-text-fill: black; -fx-fill: black; -fx-font-weight: bold; -fx-font-size: 15");
                Text nameLabel = new Text(u.getName());
                Text surnameLabel = new Text(u.getSurname());
                Button addToFriend = new Button("Añadir a lista de amigos");
                addToFriend.setStyle("-fx-background-color: #63c963; -fx-fill: white; -fx-text-fill: white; -fx-font-weight: bold");
                userCard.getChildren().addAll(usernameLabel, nameLabel, surnameLabel);
                cardContainer.getChildren().addAll(userCard, addToFriend);
                cardContainer.setSpacing(20);
                userCard.setSpacing(5);
                userCard.setPadding(new Insets(5, 5, 5, 5));
                container.getChildren().add(cardContainer);
            }
        }
    }

}
