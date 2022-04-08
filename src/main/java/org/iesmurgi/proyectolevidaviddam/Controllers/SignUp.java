package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.HelloApplication;
import org.iesmurgi.proyectolevidaviddam.Middleware.GeneralDecoder;
import org.iesmurgi.proyectolevidaviddam.Middleware.OpenThread;
import org.iesmurgi.proyectolevidaviddam.Middleware.Requester;
import org.iesmurgi.proyectolevidaviddam.Middleware.TokenManager;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;

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

    boolean mail = false;
    boolean username = false;

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
    void signup(MouseEvent event) throws IOException, NoSuchAlgorithmException, InterruptedException {

//        TokenManager tk = new TokenManager();
//        String token = tk.getToken();
//        GeneralDecoder dec = new GeneralDecoder();
//        System.out.println(dec.getUserFromToken());

        //AÑADIR VALIDACIÓN DE FORMULARIO !!!!!!!!!!!!!!!!!!!!!!!!

        String url3 = CONSTANT.URL.getUrl()+"/signup";

        if(!pwdContraseña.getText().equals(pwdRepetirContraseña.getText())){
            textFieldCorreo.getStyleClass().add("text-field");
            textFieldNick.getStyleClass().add("text-field");
            pwdContraseña.getStyleClass().add("text-field-error");
            pwdRepetirContraseña.getStyleClass().add("text-field-error");
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setTitle("Error!");
            a.setContentText("Las contraseñas no coinciden");
            a.show();
        }else{
            if(!textFieldNick.getText().equals("") && !textFieldCorreo.getText().equals("") && !pwdContraseña.getText().equals("")){
                if(checkMail() && checkUsername()){
                    //                Requester<String> requester = new Requester<>(url3, Requester.Method.POST,String.class);
//                requester.addParam("username", textFieldNick.getText());
//                requester.addParam("email", textFieldCorreo.getText());
                    GeneralDecoder md5 = new GeneralDecoder();
//                requester.addParam("password", md5.encodeMD5(pwdContraseña.getText()));
//                requester.addParam("name", textFieldNombre.getText());
//                requester.addParam("surname", textFieldApellidos.getText());

                    ArrayList<String[]> params3 = new ArrayList<>();
                    params3.add(new String[] {"username", textFieldNick.getText()});
                    params3.add(new String[] {"email", textFieldCorreo.getText()});
                    params3.add(new String[] {"password", md5.encodeMD5(pwdContraseña.getText())});
                    params3.add(new String[] {"name", textFieldNombre.getText()});
                    params3.add(new String[] {"surname", textFieldApellidos.getText()});
                    OpenThread<String> t = new OpenThread<String>(url3, params3, "POST", String.class);
                    t.getResult();

//                requester.execute();
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();
                    try {
                        Stage stage1 = new Stage();
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Homepage.fxml")));
                        Scene scene = new Scene(root);
                        stage1 = new Stage(StageStyle.DECORATED);
                        stage1.setScene(scene);
                        stage1.show();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }else{
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.ERROR);
                a.setTitle("Campos vacios!");
                a.setContentText("Asegurese de rellenar todos los campos obligatorios");
                a.show();
                //AQUI DEBO AÑADIR ADEMÁS QUE SE APLIQUEN CLASES CSS ESPECÍFICAS PARA TEXTFIELD CON ERROR -> PENDIENTE
                pwdContraseña.getStyleClass().add("text-field-error");
                pwdContraseña.getStyleClass().add("text-field-error");
                pwdRepetirContraseña.getStyleClass().add("text-field-error");
                textFieldCorreo.getStyleClass().add("text-field-error");
                textFieldNick.getStyleClass().add("text-field-error");
            }
        }

    }

    private synchronized boolean checkMail() throws IOException, InterruptedException {

        boolean mail = false;

        String url1 = CONSTANT.URL.getUrl()+"/check-email";
//        Requester<String> stringRequester1 = new Requester<>(url1, Requester.Method.POST,String.class);
//        stringRequester1.addParam("email", textFieldCorreo.getText());
//        String[] stringRespuesta1 = new String[]{stringRequester1.execute()};
//        String result1 = stringRespuesta1[0];

        ArrayList<String[]> params = new ArrayList<>();
        params.add(new String[]{"email", textFieldCorreo.getText()});
        OpenThread<String> t = new OpenThread<>(url1, params, "POST", String.class);

        if(t.getResult().equals("mail_error")) {
            textFieldNick.getStyleClass().add("text-field");
            textFieldCorreo.getStyleClass().add("text-field-error");
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setTitle("Error!!");
            a.setContentText("El correo introducido ya está en uso");
            a.show();
        }else {
            System.out.println("Email validado");
            mail = true;
        }
        System.out.println("Comprobando username");

        return mail;
    }

    private synchronized boolean checkUsername() throws IOException, InterruptedException {

        boolean username = false;

        String url2 = CONSTANT.URL.getUrl()+"/check-username";
//        Requester<String> stringRequester2 = new Requester<>(url2, Requester.Method.POST,String.class);
//        stringRequester2.addParam("username", textFieldNick.getText());
//        String[] stringRespuesta2 = new String[]{stringRequester2.execute()};
//        String result2 = stringRespuesta2[0];

        ArrayList<String[]> params = new ArrayList<>();
        params.add(new String[]{"username", textFieldNick.getText()});
        OpenThread<String> t = new OpenThread<>(url2, params, "POST", String.class);

        if(t.getResult().equals("username_error")) {
            textFieldNick.getStyleClass().add("text-field-error");
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setTitle("Error!");
            a.setContentText("El nick introducido ya está en uso");
            a.show();
        }else{
            System.out.println("Nick validado");
            username = true;
        }

        return username;
    }
}
