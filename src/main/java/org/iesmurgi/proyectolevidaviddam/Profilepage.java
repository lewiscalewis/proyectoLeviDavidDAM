package org.iesmurgi.proyectolevidaviddam;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class Profilepage {
    @javafx.fxml.FXML
    private VBox profileRoot;
    @javafx.fxml.FXML
    private Button botonCrearCuenta;

    public void initialize(){
        botonCrearCuenta.setOnAction(actionEvent -> {

            /*
            Scene scene = new Scene(fxmlLoader.load(), 700, 300);
            stage.setTitle("Hello!");
            stage.setMaximized(true);
            stage.setMinWidth(700);
            stage.setMinHeight(300);
            stage.setScene(scene);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

            stage.show();
            Thread.sleep(500);
            stage.toFront();
            */
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sign_up.fxml"));
                profileRoot.getChildren().clear();
                profileRoot.getChildren().add(fxmlLoader.load());

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
