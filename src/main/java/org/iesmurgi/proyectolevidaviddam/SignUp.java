package org.iesmurgi.proyectolevidaviddam;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.controlsfx.control.HyperlinkLabel;
import org.iesmurgi.proyectolevidaviddam.Middleware.ApiCall;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SignUp {
    @FXML
    private Button btnRegistrarse;

    @FXML
    private Label lblApellidos;

    @FXML
    private Label lblContraseña;

    @FXML
    private Label lblCorreo;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblNombre1;

    @FXML
    private Label lblRepetirContraseña;

    @FXML
    private Hyperlink login;

    @FXML
    private PasswordField pwdContraseña;

    @FXML
    private PasswordField pwdRepetirContraseña;

    @FXML
    private TextField textFieldApellidos;

    @FXML
    private TextField textFieldCorreo;

    @FXML
    private TextField textFieldNick;

    @FXML
    private TextField textFieldNombre;

    @FXML
    private VBox vBoxCard;

    @FXML
    private VBox vBoxSignUp;

    @FXML
    void initialize(){
//        lblNombre.getStyleClass().setAll("lbl","lbl-primary");
//        lblApellidos.getStyleClass().setAll("lbl", "lbl-primary");
//        lblCorreo.getStyleClass().setAll("lbl", "lbl-primary");
//        lblContraseña.getStyleClass().setAll("lbl", "lbl-primary");
//        lblRepetirContraseña.getStyleClass().setAll("lbl", "lbl-primary");
        btnRegistrarse.getStyleClass().setAll("btn", "btn-success");
    }

    @FXML
    void login(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("log_in.fxml"));
            vBoxSignUp.getChildren().clear();
            vBoxSignUp.getChildren().add(fxmlLoader.load());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void signup(MouseEvent event) throws IOException {

        boolean mail = false;
        boolean username = false;

        String url1 = "http://10.147.20.65:3000/check-email";
        String[][] parameters1 = new String[1][2];
        parameters1[0][0] = "email";
        parameters1[0][1] = textFieldCorreo.getText();

        ApiCall api1 = new ApiCall(url1, parameters1);
        String result1 = api1.openPostConnection();

        if(result1.equals("mail_error\n")) {
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setTitle("Error!!");
            a.setContentText("El correo introducido ya está en uso");
            a.show();
        }else {
            System.out.println("Email validado");
            mail = true;
        }

        //---------------------------------------------------------

        String url2 = "http://10.147.20.65:3000/check-username";
        String[][] parameters2 = new String[1][2];
        parameters2[0][0] = "username";
        parameters2[0][1] = textFieldNick.getText();

        ApiCall api2 = new ApiCall(url2, parameters2);
        String result2 = api2.openPostConnection();

        if(result2.equals("username_error\n")) {
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setTitle("Error!");
            a.setContentText("El nick introducido ya está en uso");
            a.show();
        }else{
            System.out.println("Nick validado");
            username = true;
        }

        //---------------------------------------------------------

        String url3 = "http://10.147.20.65:3000/signup";
        String[][] parameters3 = new String[5][2];
        parameters3[0][0] = "email";
        parameters3[0][1] = textFieldCorreo.getText();
        parameters3[1][0] = "name";
        parameters3[1][1] = textFieldNombre.getText();
        parameters3[2][0] = "surname";
        parameters3[2][1] = textFieldApellidos.getText();
        parameters3[3][0] = "username";
        parameters3[3][1] = textFieldNick.getText();
        parameters3[4][0] = "password";
        parameters3[4][1] = pwdContraseña.getText();

        if(mail && username){
            ApiCall api3 = new ApiCall(url3, parameters3);
            String result3 = api3.openPostConnection();
            System.out.println(result3);
        }

    }
}
