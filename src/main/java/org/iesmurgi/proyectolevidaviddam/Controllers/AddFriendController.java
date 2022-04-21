package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.Middleware.OpenThread;
import org.iesmurgi.proyectolevidaviddam.models.User;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;

public class AddFriendController {

    @FXML
    private VBox container;

    @FXML
    private TextField textfieldBrowser;

    @FXML
    void filterByName(KeyEvent event) {
        //método que necesita la previa carga de datos de los usuarios de la aplicación que no sean tus amigos para desde un ArrayList
        //se cree una lista de sugerencias de posibles usuarios
    }

    @FXML
    void search(ActionEvent event) throws MalformedURLException, InterruptedException {
        //recogemos todos los usuarios que coincidan con lo escrito en el textfield
        User[] users;
        String url = CONSTANT.URL.getUrl()+"/find-users";
        ArrayList<String[]> params = new ArrayList<>();
        params.add(new String[]{"username", textfieldBrowser.getText()});
        OpenThread<User[]> t = new OpenThread<User[]>(url, params, "POST", User[].class);
        users = t.getResult();

        for(User u: users){
            VBox userCard = new VBox();
            Label usernameLabel = new Label(u.getUsername());
            Text nameLabel = new Text(u.getName());
            Text surnameLabel = new Text(u.getSurname());
            Button addToFriend = new Button("Añadir a lista de amigos");
            userCard.getChildren().addAll(usernameLabel, nameLabel, surnameLabel, addToFriend);
            container.getChildren().add(userCard);
        }
    }

}
