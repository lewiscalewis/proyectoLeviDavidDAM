package org.iesmurgi.proyectolevidaviddam;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HelloController {


    @FXML
    private Button btnStore;
    @FXML
    private Button btnProfile;
    @FXML
    private Label labelSampleRequest;
    @FXML
    private VBox pageRoot;

    public void initialize(){
        //Abre la pagina de inicio cuando se abre la aplicacion:
        loadHomePage();
    }


    @Deprecated
    protected void onHelloButtonClick() throws IOException {
        sampleRequest();
    }

    public void sampleRequest() throws IOException{
        String url="http://25.41.23.74:3000";
        URL urls1 = new URL(url);
        HttpURLConnection conexion1 = (HttpURLConnection) urls1.openConnection();
        conexion1.setRequestMethod("POST");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conexion1. getInputStream()));

        // Mientras el BufferedReader se pueda leer, agregar contenido a resultado
        String linea="";
        while ((linea = rd.readLine()) != null) {
            Gson gson = new Gson();
            labelSampleRequest.setText(linea);
        }

        // Cerrar el BufferedReader
        rd.close();

    }


    @FXML
    public void loadHomePage()  {

        try {
            pageRoot.getChildren().clear();
            Pane root = (new FXMLLoader(HelloApplication.class.getResource("homepage.fxml")).load());
            pageRoot.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void loadProfilePage()  {

        try {
            pageRoot.getChildren().clear();
            Pane root = (new FXMLLoader(HelloApplication.class.getResource("profilepage.fxml")).load());
            pageRoot.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}