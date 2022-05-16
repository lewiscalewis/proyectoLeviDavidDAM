package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.Middleware.FileGetter;
import org.iesmurgi.proyectolevidaviddam.Middleware.GeneralDecoder;
import org.iesmurgi.proyectolevidaviddam.Middleware.TokenManager;

import java.io.IOException;

public class SettingsController {

    TokenManager tk = new TokenManager();
    GeneralDecoder gd = new GeneralDecoder();

    @FXML
    private StackPane baseRoot;

    @FXML
    private ImageView profileImage;

    @FXML
    private TextField changepdw;

    @FXML
    private TextField rechangepwd;

    @FXML
    private TextField state;


    @FXML
    void initialize() throws IOException {
        String url = CONSTANT.URL.getUrl()+"/download-image";
        FileGetter fileGetter = new FileGetter(url);
        fileGetter.addParam("username", gd.getUserFromToken());
        fileGetter.addParam("token", tk.getToken());
        profileImage.imageProperty().bind(fileGetter.getImage().imageProperty());
        profileImage.setFitHeight(200);
        profileImage.setFitWidth(200);
    }

    @FXML
    void sendData(ActionEvent event) {
        if(!changepdw.getText().equals("") && !rechangepwd.getText().equals("") && changepdw.getText().equals(rechangepwd.getText())){
            //si el usuario escribió bien los campos de contraseña, esta se cambia al darle al botón de aplicar
        }

        if(!state.getText().equals("")){
            //si el usuario escribio algo en estado este se cambia al darle al botón de aplicar
        }
    }
}
