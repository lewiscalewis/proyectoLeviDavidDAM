package org.iesmurgi.proyectolevidaviddam.Controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.HelloApplication;
import org.iesmurgi.proyectolevidaviddam.Middleware.GeneralDecoder;
import org.iesmurgi.proyectolevidaviddam.Middleware.OpenThread;
import org.iesmurgi.proyectolevidaviddam.Middleware.Requester;
import org.iesmurgi.proyectolevidaviddam.Middleware.TokenManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class LogIn {

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private Button btnRegistrarse;

    @FXML
    private VBox profileRoot;

    @FXML
    private PasswordField textFieldContraseña;

    @FXML
    private TextField textFieldUsuario;


    public void initialize(){

        btnRegistrarse.getStyleClass().setAll("btn", "btn-success");
        btnIniciarSesion.getStyleClass().setAll("btn", "btn-primary");

        btnRegistrarse.setOnAction(actionEvent -> {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sign_up.fxml"));
                profileRoot.getChildren().clear();
                profileRoot.getChildren().add(fxmlLoader.load());

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnIniciarSesion.setOnAction(actionEvent -> {

            String response = "";

            String url = CONSTANT.URL.getUrl()+"/login";

            try {
                ArrayList<String[]> params = new ArrayList<>();
                params.add(new String[]{"username", textFieldUsuario.getText()});
                params.add(new String[]{"password", textFieldContraseña.getText()});
                OpenThread<String> t = new OpenThread<>(url, params, "POST", String.class);
                response = t.getResult();

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Respuesta: "+response);
            if(response.equals("login_error") || response == null || response.equals("")){
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.ERROR);
                a.setTitle("Error de Autentificación");
                a.setContentText("El usuario o la contraseña son incorrectos");
                a.show();
            }else{
                //Llamamos al gestor del token para que guarde localmente el token
                TokenManager tkm = new TokenManager();
                tkm.tokenStorage(response);
                System.out.println("Token de usuario: "+response);

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("homepage.fxml"));
                    profileRoot.getChildren().clear();
                    profileRoot.getChildren().add(fxmlLoader.load());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    @FXML
    void login(MouseEvent event) throws NoSuchAlgorithmException, IOException {

    }

}
