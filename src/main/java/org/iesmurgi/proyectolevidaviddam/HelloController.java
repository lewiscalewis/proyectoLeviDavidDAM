package org.iesmurgi.proyectolevidaviddam;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.iesmurgi.proyectolevidaviddam.Controllers.HomepageController;
import org.iesmurgi.proyectolevidaviddam.models.User;

public class HelloController {


    @FXML
    private Button btnStore;
    @FXML
    private Button btnProfile;
    @FXML
    private AnchorPane pageRoot;
    @FXML
    private Hyperlink hyperlinkUser;                //HyperLink que se encuentra arriba a la derecha junto a la imagen del usuario
    @FXML
    private StackPane baseRoot;
    @FXML
    private ColumnConstraints columnConstraints3;
    @FXML
    private ColumnConstraints columnConstraints31;
    @FXML
    private GridPane contentRoot;
    @FXML
    private VBox chatSlider;
    @FXML
    private GridPane gridRoot;


    public void initialize() throws IOException {
        chatSlider.setOnMouseClicked(actionEvent->{
            if(chatOpen) {
                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(0.4));
                slide.setNode(chatSlider);

                slide.setToX(180);
                slide.play();

                chatSlider.setTranslateX(+176);
                chatOpen=false;
            }else{
                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(0.4));
                slide.setNode(chatSlider);

                slide.setToX(0);
                slide.play();

                chatSlider.setTranslateX(+176);
                chatOpen=true;
            }
            /*slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(false);
                MenuClose.setVisible(true);
            });*/
        });

    }


    void loadUserData(User user){
        hyperlinkUser.setText(user.getName());
        hyperlinkUser.setOnAction(event->{

        });
    }
    boolean chatOpen=true;
    @FXML
    public void loadHomePage()  {

        try {
            pageRoot.getChildren().clear();
            FXMLLoader fxmlLoader =new FXMLLoader(HelloApplication.class.getResource("homepage.fxml"));

            //HomepageController homepageController = fxmlLoader.getController();




            Pane root = (fxmlLoader.load());
            pageRoot.getChildren().add(root);
            hyperlinkUser.setOnAction(actionEvent -> {
                slideChatSlider();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }




    }
    public void slideChatSlider(){
        if(chatOpen) {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(chatSlider);

            slide.setToX(180);
            slide.play();

            chatSlider.setTranslateX(+176);
            chatOpen=false;
        }else{
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(chatSlider);

            slide.setToX(0);
            slide.play();

            chatSlider.setTranslateX(+176);
            chatOpen=true;
        }
    }






    @FXML
    public void loadProfilePage()  {

        try {
            pageRoot.getChildren().clear();
            FXMLLoader rootFxmlLoader=new FXMLLoader(
                    HelloApplication.class.getResource(
                            "profilepage.fxml"
                    )
            );
            Pane root = rootFxmlLoader.load();
            pageRoot.getChildren().add(root);

            //root es la raiz de nuestra página, todoo lo que se ve menos el menu.
            /*
            String url="http://tux.iesmurgi.org:11230/users";
            URL urls1 = new URL(url);
            HttpURLConnection conexion1 = (HttpURLConnection) urls1.openConnection();
            conexion1.setRequestMethod("POST");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conexion1. getInputStream()));

            // Mientras el BufferedReader se pueda leer, agregar contenido a resultado
            String linea="";
            while ((linea = rd.readLine()) != null) {
                Gson gson = new Gson();
                User[] juegos = gson.fromJson(linea, User[].class);
                Label labelRequest=new Label(juegos[0].toString());
                root.getChildren().add(labelRequest);
            }

            // Cerrar el BufferedReader
            rd.close();

*/
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}