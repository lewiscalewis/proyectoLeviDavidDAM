package org.iesmurgi.proyectolevidaviddam;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import javafx.scene.control.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import javafx.scene.control.skin.LabeledSkinBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.iesmurgi.proyectolevidaviddam.Controllers.HomepageController;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.Middleware.Requester;
import org.iesmurgi.proyectolevidaviddam.models.User;

public class HelloController {


    @FXML
    private AnchorPane pageRoot;
    @FXML
    private Hyperlink hyperlinkUser;                //HyperLink que se encuentra arriba a la derecha junto a la imagen del usuario
    @FXML
    private StackPane baseRoot;
    @FXML
    private ColumnConstraints columnConstraints3;
    @FXML
    private GridPane contentRoot;
    @FXML
    private VBox chatSlider;
    @FXML
    private GridPane gridRoot;
    @FXML
    private ImageView imageviewProfileImage;
    @FXML
    private Label tileSettings;
    @FXML
    private Label tileSettings41;
    @FXML
    private Label tileSettings4;
    @FXML
    private Label tileSettings3;
    @FXML
    private Label tileSettings2;
    @FXML
    private Label tileSettings1;


    public void initialize() throws IOException {
        chatSlider.setTranslateX(265);

        /*chatSlider.setOnMouseClicked(actionEvent->{
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
            });
        });*/

    }


    public void loadUserData(User user){
        hyperlinkUser.setText(user.getName());
        hyperlinkUser.setOnAction(event->{

        });


        //FOR TESTING:
        //INSERT IMAGE STRING INTO DATABASE.




        ///////////////////////////////////////////////////////////






        //char[] arrayImage = user.getProfileImage().toCharArray();
        //System.out.println(user.getProfileImage());
        //Crea una imagen a partir del String de la base de datos.
        /*Image profileimage= new Image(new ByteArrayInputStream(
                user.getProfileImage().getBytes(StandardCharsets.UTF_8)
        ),30,30,true,true
        );
        //Image profileimage= new Image(user.getProfileImage());

        imageviewProfileImage.setImage(profileimage);}}*/
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

            //((Stage)pageRoot.getScene().getWindow()).setMinWidth(900);
            //((Stage)pageRoot.getScene().getWindow()).setMinHeight(500);


            //hyperlinkUser.setOnAction(actionEvent -> {
                //slideChatSlider();
           // });


            chatSlider.setOnMouseEntered(actionEvent->openChatSlider());
            chatSlider.setOnMouseExited(actionEvent->closeChatSlider());

        } catch (IOException e) {
            e.printStackTrace();
        }




    }


    public void openChatSlider() {

            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(chatSlider);

            slide.setToX(0);
            slide.play();

            chatSlider.setTranslateX(+176);
    }

    public void closeChatSlider(){

            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(chatSlider);

            slide.setToX(265);
            slide.play();

            //chatSlider.setTranslateX(+176);

    }

    public void slideChatSlider(){
        if(chatOpen) {
            closeChatSlider();
            chatOpen=false;
        }else{
            openChatSlider();

            //chatSlider.setTranslateX(+180);
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


            ((Stage)root.getScene().getWindow()).setMinWidth(900);
            ((Stage)root.getScene().getWindow()).setMinHeight(500);



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

    @FXML
    public void slideChatSlider(Event event) {

    }

    @FXML
    public void onSlideHoverExited(Event event) {
        ((HBox) event.getTarget()).setStyle("-fx-background-color: #4433aa;");
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.1));
        slide.setNode((HBox) event.getTarget());
        //((HBox) event.getTarget()).setTranslateY(-6);
        slide.setToX(0);
        slide.setToY(0);
        slide.play();


    }

    @FXML
    public void onSlideHoverEnter(Event event) {
        ((HBox) event.getTarget()).setStyle("-fx-background-color:#4436cc; ");

        

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.1));
        slide.setNode((HBox) event.getTarget());

        slide.setToX(-10);
        slide.setToY(-2);
        slide.play();

        //((HBox) event.getTarget()).setTranslateY(-6);
    }

    @FXML
    public void onMenuItemEnter(Event event) {
        ((HBox) event.getTarget()).setStyle("-fx-background-color:#4436cc; ");



        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.1));
        slide.setNode((HBox) event.getTarget());

        slide.setToY(5);
        //slide.setToX(2);
        slide.play();

    }

    @FXML
    public void onMenuItemExited(Event event) {
        ((HBox) event.getTarget()).setStyle("-fx-background-color: #6168f3;");
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.1));
        slide.setNode((HBox) event.getTarget());
        //((HBox) event.getTarget()).setTranslateY(-6);
        slide.setToY(0);
        //slide.setToX(0);
        slide.play();


    }

    @FXML
    public void onSliderPressed(Event event) {/*
        System.out.println(event.getSource().getClass().getName());

        if(event.getSource().getClass()==HBox.class){
            ((HBox) event.getTarget()).setStyle("-fx-background-color:#4046c1; ");
        }else {
            ((Node) event.getTarget()).getParent().getParent().setStyle("-fx-background-color:#4046c1; ");
            event.consume();
        }*/
    }

    @FXML
    public void onSliderReleased(Event event) {/*

        if(event.getSource().getClass()==HBox.class){
            ((HBox) event.getTarget()).setStyle("-fx-background-color:#4436cc; ");
        }else {
            ((Node) event.getTarget()).getParent().getParent().setStyle("-fx-background-color:#4436cc; ");
            event.consume();
        }*/
    }
}