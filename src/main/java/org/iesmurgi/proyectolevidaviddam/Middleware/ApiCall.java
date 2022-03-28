package org.iesmurgi.proyectolevidaviddam.Middleware;

import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class ApiCall {

    /*
    * La clase está pensada para trabajar en el proyecto de la siguiente manera: para SELECT(s) normales lanzamos peticiones GET, salvo en caso
    * de que tengas que enviar parámetros sensibles como contraseñas en el que usariamos POST para este y para hacer UPDATES, DELETES O INSERTS.
    * Dicho esto una vez que se haga una petición POST puede darse el caso de que devuelva datos en caso de ser para SELECT o no, por ello para el primer caso (hay resultado de vuelta)
    * usaremos openPostConnection, para el caso contrario openVoidPostConnection y en caso de usar GET openGetConnection.
    * */

    private String URL;
    private String[][] parameters;
    private String requestMethod;

    /**
     * Contructor para llamadas POST
     * @param URL
     * @param parameters
     * @throws IOException
     */
    public ApiCall(String URL, String[][] parameters) throws IOException {
        this.URL = URL;
        this.parameters = parameters;
    }

    /**
     * Constructor para llamadas llamadas GET (con o sin parámetros)
     * @param URL
     * @throws IOException
     */
    public ApiCall(String URL) throws IOException {
        this.URL = URL;
    }

    /**
     * Abre una conexión con la API de tipo POST y devuelve una cadena con el resultado formateado con saltos de linea.
     * Este método está pensado para usarse en POST que devuelan datos.
     * @return
     * @throws IOException
     */
    public String openPostConnection() throws IOException {

        //Formamos la URL

        java.net.URL url = new URL(URL);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conexion.setRequestMethod("POST");
        conexion.setDoOutput(true);
        //conexion.setRequestProperty("Content-Type", "application/json; utf-8");
        //conexion.setRequestProperty("Accept", "application/json");

        //Recorremos el array bidimensional de parametros para enviarlos en el POST/GET

        OutputStream os = conexion.getOutputStream();

        for(int i = 0; i < parameters.length; i++){
            String urlParameters  = parameters[i][i]+"="+parameters[i][i+1];
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            os.write(postData, 0, postDataLength);
        }

        os.flush();
        os.close();

        //Leemos resultados

        BufferedReader rd = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
        String linea;
        String l="";

        // Mientras el BufferedReader se pueda leer, agregar contenido a resultado
        while ((linea = rd.readLine()) != null) {
            l += linea+"\n";
            System.out.println(linea);
        }

        // Cerrar el BufferedReader
        rd.close();

        return l;
    }

    /**
     * Abre una conexión con la API de tipo POST sin esperar resultados.
     * @throws IOException
     */
    public void openVoidPostConnection() throws IOException {

        //Formamos la URL

        java.net.URL url = new URL(URL);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conexion.setRequestMethod("POST");
        conexion.setDoOutput(true);
        //conexion.setRequestProperty("Content-Type", "application/json; utf-8");
        //conexion.setRequestProperty("Accept", "application/json");

        //Recorremos el array bidimensional de parametros para enviarlos en el POST/GET

        OutputStream os = conexion.getOutputStream();

        for(int i = 0; i < parameters.length; i++){
            String urlParameters  = parameters[i][i]+"="+parameters[i][i+1];
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            os.write(postData, 0, postDataLength);
        }

        os.flush();
        os.close();
    }

    /**
     * Abre una conexión de tipo GET y devuelve los datos
     * @return
     * @throws IOException
     */
    public String openGetConnection() throws IOException {
        //Formamos la URL

        java.net.URL url = new URL(URL);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conexion.setRequestMethod("GET");
        conexion.setDoOutput(true);

        //Leemos resultados

        BufferedReader rd = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
        String linea;
        String l="";

        // Mientras el BufferedReader se pueda leer, agregar contenido a resultado
        while ((linea = rd.readLine()) != null) {
            l += linea+"\n";
            System.out.println(linea);
        }

        // Cerrar el BufferedReader
        rd.close();

        return l;
    }
}
