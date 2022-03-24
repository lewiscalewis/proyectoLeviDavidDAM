package org.iesmurgi.proyectolevidaviddam;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.controlsfx.control.HyperlinkLabel;
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

        BufferedReader rd;
        String linea;

        String urlParameters  = "email="+textFieldCorreo.getText();
        //JSONObject emailParamJson = new JSONObject("{\"email\":"+"\""+urlParameters+"\"}");
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;

        URL urls1 = new URL(url1);
        HttpURLConnection conexion = (HttpURLConnection) urls1.openConnection();
        conexion.setRequestMethod("POST");
        conexion.setDoOutput(true);
        //conexion.setRequestProperty("Content-Type", "application/json; utf-8");
        //conexion.setRequestProperty("Accept", "application/json");

        OutputStream os = conexion.getOutputStream();

        os.write(postData, 0, postDataLength);
        os.flush();
        os.close();

        rd = new BufferedReader(new InputStreamReader(conexion. getInputStream()));

        // Mientras el BufferedReader se pueda leer, agregar contenido a resultado
        while ((linea = rd.readLine()) != null) {
            System.out.println(linea);
            if(linea.equals("mail_error")){
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.ERROR);
                a.setTitle("El correo introducido ya está en uso");
                a.show();
            }
        }

        // Cerrar el BufferedReader
        rd.close();
    }
}
