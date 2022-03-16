package org.iesmurgi.proyectolevidaviddam;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.controlsfx.control.HyperlinkLabel;

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
    void initialize(){
//        lblNombre.getStyleClass().setAll("lbl","lbl-primary");
//        lblApellidos.getStyleClass().setAll("lbl", "lbl-primary");
//        lblCorreo.getStyleClass().setAll("lbl", "lbl-primary");
//        lblContraseña.getStyleClass().setAll("lbl", "lbl-primary");
//        lblRepetirContraseña.getStyleClass().setAll("lbl", "lbl-primary");
        btnRegistrarse.getStyleClass().setAll("btn", "btn-primary");
    }
}
