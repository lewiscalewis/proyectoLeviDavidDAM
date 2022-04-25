package org.iesmurgi.proyectolevidaviddam.Controllers;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.HelloApplication;
import org.iesmurgi.proyectolevidaviddam.Middleware.GeneralDecoder;
import org.iesmurgi.proyectolevidaviddam.Middleware.OpenThread;
import org.iesmurgi.proyectolevidaviddam.Middleware.Requester;
import org.iesmurgi.proyectolevidaviddam.Middleware.TokenManager;
import org.iesmurgi.proyectolevidaviddam.models.User;

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
    private TextField textFieldUsuario;

    Stage stage;
    Scene scene;
    @FXML
    private PasswordField textFieldContrasena;

    public void initialize(){

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
            GeneralDecoder md5 = new GeneralDecoder();

            try {
                ArrayList<String[]> params = new ArrayList<>();
                params.add(new String[]{"username", textFieldUsuario.getText()});
                params.add(new String[]{"password", md5.encodeMD5(textFieldContrasena.getText())});
                OpenThread<String> t = new OpenThread<>(url, params, "POST", String.class);
                response = t.getResult();

            } catch (IOException | InterruptedException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            System.out.println("Respuesta: "+response);
            if(response!=null) {//////////////////////////////////////////////////////SALE SI LA RESPUESTA ES NULL
                if (response.equals("login_error") || response.equals("")) {
                    Alert a = new Alert(Alert.AlertType.NONE);
                    a.setAlertType(Alert.AlertType.ERROR);
                    a.setTitle("Error de Autentificación");
                    a.setContentText("El usuario o la contraseña son incorrectos");
                    a.show();
                } else {
                    //Llamamos al gestor del token para que guarde localmente el token
                    TokenManager tkm = new TokenManager();
                    tkm.tokenStorage(response);
                    System.out.println("Token de usuario: " + response);

                    scene = btnIniciarSesion.getScene();
                    stage = (Stage) scene.getWindow();
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
                        Parent helloView = fxmlLoader.load();
                        HelloController helloController = fxmlLoader.getController();


                        GeneralDecoder gd = new GeneralDecoder();


                        String username = gd.getUserFromToken();


                        Platform.runLater(() -> {
                            Requester<User[]> userRequester = null;
                            try {



                                userRequester = new Requester<>("http://tux.iesmurgi.org:11230/user", Requester.Method.POST, User[].class);
                                userRequester.addParam("username", username);
                                helloController.loadUserData(userRequester.execute()[0]);

                                Scene s = new Scene(helloView, scene.getWidth(), stage.getHeight() - 34, Color.BLACK);
                                stage.setScene(s);
                                stage.show();
                                //Loads Home page

                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @FXML
    void login(ActionEvent event) throws NoSuchAlgorithmException, IOException {
//        String response = "";
//
//        String url = CONSTANT.URL.getUrl()+"/login";
//        GeneralDecoder md5 = new GeneralDecoder();
//
//        try {
//            ArrayList<String[]> params = new ArrayList<>();
//            params.add(new String[]{"username", textFieldUsuario.getText()});
//            params.add(new String[]{"password", md5.encodeMD5(textFieldContrasena.getText())});
//            OpenThread<String> t = new OpenThread<>(url, params, "POST", String.class);
//            response = t.getResult();
//
//        } catch (IOException | InterruptedException | NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Respuesta: "+response);
//        if(response.equals("login_error") || response.equals("")){
//            Alert a = new Alert(Alert.AlertType.NONE);
//            a.setAlertType(Alert.AlertType.ERROR);
//            a.setTitle("Error de Autentificación");
//            a.setContentText("El usuario o la contraseña son incorrectos");
//            a.show();
//        }else{
//            //Llamamos al gestor del token para que guarde localmente el token
//            TokenManager tkm = new TokenManager();
//            tkm.tokenStorage(response);
//            System.out.println("Token de usuario: "+response);
//
//            scene =btnIniciarSesion.getScene();
//            stage = (Stage) scene.getWindow();
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//                Parent helloView = fxmlLoader.load() ;
//                HelloController helloController = fxmlLoader.getController();
//                helloController.loadHomePage(); //Loads Home page
//
//
//
//                GeneralDecoder gd = new GeneralDecoder();
//
//
//                String username= gd.getUserFromToken();
//
//
//                Requester<User[]> userRequester = new Requester<>("http://tux.iesmurgi.org:11230/user",Requester.Method.POST,User[].class);
//                userRequester.addParam("username",username);
//                helloController.loadUserData(userRequester.execute()[0]);
//
//                Scene s = new Scene(helloView, scene.getWidth(), stage.getHeight()-34, Color.BLACK);
//                stage.setScene(s);
//                stage.show();
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        }
    }

}
