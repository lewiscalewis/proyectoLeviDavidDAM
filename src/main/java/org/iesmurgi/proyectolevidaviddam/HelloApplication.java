package org.iesmurgi.proyectolevidaviddam;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.iesmurgi.proyectolevidaviddam.Middleware.Requester;
import org.iesmurgi.proyectolevidaviddam.models.User;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("log_in.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setMaximized(true);
        stage.setMinWidth(500);
        stage.setMinHeight(500);
        stage.setScene(scene);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        stage.show();
        Thread.sleep(500);
        stage.toFront();


        ///
        /// Endpoint /users request
        ///
/*
        // Create a neat value object to hold the URL
        URL url = new URL("http://10.147.20.65:3000/users");

        // Open a connection(?) on the URL(??) and cast the response(???)
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Now it's "open", we can set the request method, headers etc.
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestMethod("POST");
        // This line makes the request
        InputStream responseStream = connection.getInputStream();
        Gson gson = new Gson();


        BufferedReader responseBR=new BufferedReader(new InputStreamReader(responseStream));
        String usersJSON="";
        int character=0;
        while((character=responseBR.read())!=-1){
            usersJSON+=(char)character;
        }
        System.out.println(usersJSON);
        User[] users=gson.fromJson(usersJSON,User[].class);

        for (User user:users) {
            System.out.println(user.toString());
        }


        Requester<User[]> requester=new Requester<>("http://10.147.20.65:3000/users", Requester.Method.POST,User[].class);
        User [] users =requester.execute();
        for(User user:users){
            System.out.println(user.toString());
        }
*/
        //Obtiene el los nombres de todos los usuarios.
        Thread requesterThread =new Thread(new Runnable() {
            @Override
            public void run() {
                Requester<User[]> requester= null;


                try {
                    requester = new Requester<User[]>("http://tux.iesmurgi.org:11230/users", Requester.Method.POST,User[].class);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }


                User [] users = new User[0];
                try {
                    users = requester.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }




                for(User user:users){
                    System.out.println(user.getName());
                }
            }
        });
        requesterThread.start();



        /*
        Requester<User[]> requester=new Requester<>("http://10.147.20.65:3000/", Requester.Method.POST,User[].class);
        requester.addParam("username","lewiscalewis");
        User[] users =requester.execute();
        for(User user:users){
            System.out.println(user.toString());
        }*/


        //STRING REQUESTER PARA LEVI
/*
        Requester<String> stringRequester=new Requester<>(
                "http://tux.iesmurgi.org:11230",
                Requester.Method.POST,
                String.class
        );
        //stringRequester.addParam("username","lewiscalewis");
        String[] stringRespuesta=new String[]{
                stringRequester.execute()
        };
        System.out.println(stringRespuesta[0]);
*/

        ////////////////////////////////////////////////////////////////////////////////
    }


    public static void main(String[] args) {
        launch();
    }
}