package org.iesmurgi.proyectolevidaviddam;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.iesmurgi.proyectolevidaviddam.Middleware.EncoderMD5;
import org.iesmurgi.proyectolevidaviddam.Middleware.Requester;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    void signup(MouseEvent event) throws IOException, NoSuchAlgorithmException {

        //AÑADIR VALIDACIÓN DE FORMULARIO !!!!!!!!!!!!!!!!!!!!!!!!

        boolean mail = false;
        boolean username = false;

        String url1 = "http://10.147.20.65:3000/check-email";
        Requester<String> stringRequester1 = new Requester<>(url1, Requester.Method.POST,String.class);
        stringRequester1.addParam("email", textFieldCorreo.getText());
        String[] stringRespuesta1 = new String[]{stringRequester1.execute()};
        String result1 = stringRespuesta1[0];

        if(result1.equals("mail_error")) {
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
        Requester<String> stringRequester2 = new Requester<>(url2, Requester.Method.POST,String.class);
        stringRequester2.addParam("username", textFieldNick.getText());
        String[] stringRespuesta2 = new String[]{stringRequester2.execute()};
        String result2 = stringRespuesta2[0];

        if(result2.equals("username_error")) {
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

        if(!pwdContraseña.getText().equals(pwdRepetirContraseña.getText())){
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setTitle("Error!");
            a.setContentText("Las contraseñas no coinciden");
            a.show();
        }else{
            if(mail && username && !textFieldNick.getText().equals("") && !textFieldCorreo.getText().equals("") && !pwdContraseña.getText().equals("")){

                Requester<String> requester = new Requester<>(url3, Requester.Method.POST,String.class);
                requester.addParam("username", textFieldNick.getText());
                requester.addParam("email", textFieldCorreo.getText());
                EncoderMD5 md5 = new EncoderMD5();
                requester.addParam("password", md5.encodeMD5(pwdContraseña.getText()));
                requester.addParam("name", textFieldNombre.getText());
                requester.addParam("surname", textFieldApellidos.getText());

                requester.execute();
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
            }else{
                //AQUI DEBO AÑADIR ADEMÁS QUE SE APLIQUEN CLASES CSS ESPECÍFICAS PARA TEXTFIELD CON ERROR -> PENDIENTE
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.ERROR);
                a.setTitle("Campos vacios!");
                a.setContentText("Asegurese de rellenar todos los campos obligatorios");
                a.show();
            }
        }

    }
}
