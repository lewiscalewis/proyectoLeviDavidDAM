package org.iesmurgi.proyectolevidaviddam;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.iesmurgi.proyectolevidaviddam.Middleware.EncoderMD5;
import org.iesmurgi.proyectolevidaviddam.Middleware.Requester;
import org.iesmurgi.proyectolevidaviddam.Middleware.TokenManager;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

public class LogInController {

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
            goToRegister();
        });

        btnIniciarSesion.setOnAction(actionEvent -> {
            try {
                login();
            } catch (NoSuchAlgorithmException | IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void goToRegister(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sign_up.fxml"));
            profileRoot.getChildren().clear();
            profileRoot.getChildren().add(fxmlLoader.load());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    Stage stage;
    Scene scene;


    void onBtnLogin(){

    }
    private void login() throws NoSuchAlgorithmException, IOException {

        String url = "http://tux.iesmurgi.org:11230/login";
        Requester<String> requester = new Requester<>(url, Requester.Method.POST, String.class);
        requester.addParam("username", textFieldUsuario.getText());
        EncoderMD5 md5 = new EncoderMD5();
        requester.addParam("password", md5.encodeMD5(textFieldContraseña.getText()));
        String response = requester.execute();

        if(response.equals("login_error")){
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setTitle("Error de Autentificación");
            a.setContentText("El usuario o la contraseña son incorrectos");
            a.show();
        }else{
            //Llamamos al gestor del token para que guarde localmente el token
            TokenManager tkm = new TokenManager(response);
            tkm.tokenStorage();
            System.out.println("Token de usuario: "+response);

            scene =btnIniciarSesion.getScene();
            stage = (Stage) scene.getWindow();
            try {

                URL helloLocation=getClass().getResource("hello-view.fxml");
                FXMLLoader helloFXMLLoader =new FXMLLoader(helloLocation);
                Parent helloView = helloFXMLLoader.load() ;
                HelloController helloController = helloFXMLLoader.getController();
                helloController.loadHomePage(); //Loads Home page


                Scene s = new Scene(helloView, scene.getWidth()-34, stage.getHeight()-34, Color.BLACK);

                stage.setScene(s);
                stage.show();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
