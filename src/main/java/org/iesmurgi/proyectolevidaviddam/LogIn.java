package org.iesmurgi.proyectolevidaviddam;


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
import org.iesmurgi.proyectolevidaviddam.Middleware.EncoderMD5;
import org.iesmurgi.proyectolevidaviddam.Middleware.Requester;
import org.iesmurgi.proyectolevidaviddam.Middleware.TokenManager;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    }

    @FXML
    void login(MouseEvent event) throws NoSuchAlgorithmException, IOException {

        String url = CONSTANT.URL.getUrl()+"/login";
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
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            try {
                Parent anotherRoot = FXMLLoader.load(getClass().getResource("homepage.fxml"));
                Stage anotherStage = new Stage();
                anotherStage.setTitle("Home");
                anotherStage.setScene(new Scene(anotherRoot));
                anotherStage.setMaximized(true);
                anotherStage.show();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
