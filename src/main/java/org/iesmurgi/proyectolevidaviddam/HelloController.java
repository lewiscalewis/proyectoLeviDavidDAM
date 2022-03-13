package org.iesmurgi.proyectolevidaviddam;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import com.google.gson.Gson;

public class HelloController {

    @FXML
    private Label labelHijoDePuta;
    @FXML
    private Button btnHijoDePuta;

    @FXML
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
            labelHijoDePuta.setText(linea);
        }

        // Cerrar el BufferedReader
        rd.close();

    }
}