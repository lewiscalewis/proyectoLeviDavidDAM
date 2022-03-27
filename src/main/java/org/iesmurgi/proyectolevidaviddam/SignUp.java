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
    private Label lblApellidos;

    @FXML
    private Label lblContraseña;

    @FXML
    private Label lblCorreo;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblRepetirContraseña;

    @FXML
    private PasswordField pwdContraseña;

    @FXML
    private PasswordField pwdRepetirContraseña;

    @FXML
    private TextField textFieldApellidos;

    @FXML
    private TextField textFieldCorreo;

    @FXML
    private TextField textFieldNombre;

    @FXML
    private VBox vBoxSignUp;

    @FXML
    private Button btnRegistrarse;

    @FXML
    private Hyperlink login;

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

        String url1 = "http://25.41.23.74:3000/signup";
        String[][] parameters = new String[1][2];
        parameters[0][0] = "email";
        parameters[0][1] = textFieldCorreo.getText();

        ApiCall api = new ApiCall(url1, parameters);
        String result = api.openPostConnection();

        if(result.equals("mail_error")) {
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setTitle("El correo introducido ya está en uso");
            a.show();

        }
    }
}
