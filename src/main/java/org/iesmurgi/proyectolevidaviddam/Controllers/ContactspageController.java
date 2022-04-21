package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.iesmurgi.proyectolevidaviddam.HelloApplication;

import java.io.IOException;

public class ContactspageController {

    @FXML
    private StackPane baseRoot;

    @FXML
    void initialize(){
        ((AnchorPane)baseRoot.getParent()).setLeftAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setTopAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setRightAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot   .getParent()).setBottomAnchor(baseRoot,0.0);
    }

    @FXML
    void addFriend(MouseEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add-friend-view.fxml"));
        baseRoot.getChildren().clear();
        Pane root = null;

        try {
            root = (fxmlLoader.load());
            baseRoot.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
