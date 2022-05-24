package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;
import org.iesmurgi.proyectolevidaviddam.Middleware.GeneralDecoder;
import org.iesmurgi.proyectolevidaviddam.Middleware.Requester;
import org.iesmurgi.proyectolevidaviddam.Middleware.TokenManager;
import org.iesmurgi.proyectolevidaviddam.models.Item;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

//Dentro de contentRoot es donde se supone que va el contenido de nuestra página. Es para que el chatSlider se superponga encima de esta vista.
public class ProfilepageController {

    @FXML
    private StackPane baseRoot;

    @FXML
    private FlowPane container;

    @FXML
    private ScrollPane scrollPane;

    static VBox vboxPlayer;
    static WebView webviewPlayer;
    static WebEngine webEngine;
    @FXML
    private HBox hboxContainer;
    @FXML
    private ImageView imageviewProfileImage;
    @FXML
    private Label labelUsername;


    public void initialize() throws IOException, URISyntaxException {


        imageviewProfileImage.setImage(new Image(requestProfileImage(new GeneralDecoder().getUserFromToken())));
        imageviewProfileImage.setFitWidth(150);
        imageviewProfileImage.setFitHeight(150);
        imageviewProfileImage.maxHeight(150);
        imageviewProfileImage.maxWidth(150);
        imageviewProfileImage.setPreserveRatio(false);

        labelUsername.setText(new GeneralDecoder().getUserFromToken());

        //Esto es porque para expandirse a todoo lo que ocupe la ventana, necesita indicarselo al padre del gridRoot, que en este caso
        //es el AnchorPane del hello-view.fxml. con fxid pageRoot
        ((AnchorPane)baseRoot.getParent()).setLeftAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setTopAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setRightAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot .getParent()).setBottomAnchor(baseRoot,0.0);

        //Fin de profilepage
        /////////////////////////////////////////////////////////////////////////////









        loadItems();    //Carga las canciones

        container.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));



    }

    public void setWebViewPlayer(WebView webviewPlayer, WebEngine webEngine, VBox vboxPlayer1, Label labelSongNamePlayer2, ImageView imageViewPlayer2, Hyperlink hyperlinkUsernamePlayer2){///////////////////////////////////////////
        labelSongNamePlayer=labelSongNamePlayer2;
        hyperlinkUsernamePlayer=hyperlinkUsernamePlayer2;
        imageviewPlayer=imageViewPlayer2;
        ProfilepageController.webviewPlayer =webviewPlayer;
        ProfilepageController.webEngine =webEngine;

        vboxPlayer=vboxPlayer1;
    }


    public static void play(String itemid){
        webEngine.load(null);   //STOP MUSIC BEFORE STARTING AGAIN
        webEngine.load(CONSTANT.URL.getUrl()+"/download-item/"+itemid);
        vboxPlayer.getChildren().clear();
        vboxPlayer.getChildren().add(webviewPlayer);
    }

    public boolean first=true;
    public void initializePlayer(VBox vboxPlayer, WebView webView){

        if(first) {
            vboxPlayer.getChildren().clear();
            first=false;
        }

        vboxPlayer.setMaxHeight(100);
        vboxPlayer.setMinHeight(100);
        //vboxPlayer.setStyle("-fx-background-color:black;");

        //vboxPlayer.setTranslateZ(-1);
        vboxPlayer.setAlignment(Pos.TOP_RIGHT);
        webviewPlayer.setTranslateX(-100);
        //webviewPlayer.setMaxWidth(1000);
        webviewPlayer.setMaxHeight(100);
        webviewPlayer.setMinHeight(100);
        webviewPlayer.setMinWidth(340);
        webviewPlayer.setMaxWidth(340);
        webviewPlayer.setTranslateY(46);
        webviewPlayer.setScaleX(2);
        webviewPlayer.setScaleY(2);
        webviewPlayer.setTranslateX(-40);


    }
    static Label labelSongNamePlayer;
    static Hyperlink hyperlinkUsernamePlayer;
    static ImageView imageviewPlayer;
    public void testHomepageController(){
        System.out.println("TEST OK....");
    }

    //Devuelve el nodo de la interfaz de una interfaz nosotros le pasamos un objeto cancion de la base de datos.
    Node getSong(Item item) throws IOException, URISyntaxException {

        String songName = item.getName();
        String author = item.getUsername();
        Image portada = new Image(new URL("https://images.ecestaticos.com/9Sfa715zzc8Efc_taTLIbLkKeJ4=/0x0:2272x1501/1200x900/filters:fill(white):format(jpg)/f.elconfidencial.com%2Foriginal%2F38a%2F667%2Fee7%2F38a667ee7dbc85c2f8935eeff36be807.jpg").toURI().toURL().toExternalForm());;


        HBox hbox = new HBox();

        hbox.setAlignment(Pos.CENTER);
        //hbox.setStyle("-fx-background-color: #e45926;");

        VBox song = new VBox();
        song.setAlignment(Pos.TOP_LEFT);
        song.setPrefWidth(600);
        song.setMaxWidth(600);
        song.setMinWidth(600);


        //Label del título
        Label labelSongName = new Label();
        labelSongName.setMaxWidth(500);
        labelSongName.setStyle( "-fx-font-weight: bold; " +
                                "-fx-text-fill: black;" +
                                "-fx-fill: black;" +
                                "-fx-font-size: 16; -fx-background-color: #ffffff;");
        labelSongName.setMinWidth(100);
        //Hyperlink del autor
        Hyperlink hyperlinkAuthor = new Hyperlink();
        hyperlinkAuthor.setText(author);
        labelSongName.setText(songName);
        //hyperlinkAuthor.setMaxWidth(Double.MAX_VALUE);
        hyperlinkAuthor.setAlignment(Pos.TOP_LEFT);


        //Imagen
        ImageView imageView = new ImageView();
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        imageView.setImage(portada);


        Button buttonPlay;
        buttonPlay = new Button();
        buttonPlay.setFont(new Font(100));
        buttonPlay.setText("▶");


        System.out.println(item.getId());

        buttonPlay.setOnAction((event)->{
            play(String.valueOf(item.getId()));
            labelSongNamePlayer.setText(item.getName());

            //Hyperlink del autor
            Hyperlink hyperlinkAuthorPlayer = new Hyperlink();
            hyperlinkUsernamePlayer.setText(item.getUsername());
            try {
                imageviewPlayer.setImage(new Image(requestProfileImage(new GeneralDecoder().getUserFromToken())));
                //Aqui no hay que cargar la imagen de usuario sino el COVER!!!!!!!
                //HAY QUE CREAR UN REQUESTCOVER(itemid)
            } catch (IOException e) {
                e.printStackTrace();
            }
            labelSongName.setText(songName);
        });


        song.getChildren().addAll(labelSongName,hyperlinkAuthor,imageView);
        hbox.getChildren().addAll(song,buttonPlay,imageView);


        return hbox;
    }


    InputStream requestProfileImage(String username) throws IOException {
        String URL= "http://tux.iesmurgi.org:11230/download-image";
        java.net.URL server = new URL(URL);
        // Open a connection(?) on the URL(??) and cast the response(???)
        HttpURLConnection connection = (HttpURLConnection) server.openConnection();




        // Now it's "open", we can set the request method, headers etc.
        connection.setRequestProperty("accept", "application/x-www-form-urlencoded");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        Map<String,String> params= new HashMap<>();
        params.put("username", new GeneralDecoder().getUserFromToken());                  //La petición no llega al servidor cuando le pongo parámetros
        params.put("token", new TokenManager().getToken());
        //ADD PARAMETERS
        int i = 0;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            //System.out.println(entry.getKey() + "/" + entry.getValue());

            String urlParameters  = (i > 0 ? "&" : "") + entry.getKey()+"="+entry.getValue();
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            System.out.println("Escribiendo parámetros: key -> "+entry.getKey()+"|| value -> "+entry.getValue());
            connection.getOutputStream().write(postData, 0, postDataLength);
            i++;
        }





        InputStream responseStream= connection.getInputStream();

        return responseStream;


    }


    private void loadItems(){
        Platform.setImplicitExit(true);
        Platform.runLater(() -> {
            try {
                String url = CONSTANT.URL.getUrl()+"/all-items";
                Requester<Item[]> req = new Requester<>(url, Requester.Method.POST, Item[].class);
                req.addParam("token", new TokenManager().getToken());
                Item[] items;
                items = req.execute();

                if(items.length > 0) {
                    ScrollPane itemBar = new ScrollPane();
                    VBox petitionBox = new VBox();
                    itemBar.setContent(petitionBox);
                    petitionBox.setSpacing(30);
                    itemBar.setMinWidth(300);
                    petitionBox.setPadding(new Insets(10, 10, 10, 10));
                    petitionBox.setAlignment(Pos.CENTER);

                    petitionBox.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                            itemBar.getViewportBounds().getWidth(), itemBar.viewportBoundsProperty()));


                    for (Item item : items) {
                        VBox vb = new VBox();
                        vb.setSpacing(10);
                        vb.setAlignment(Pos.CENTER);
                        vb.setPadding(new Insets(10, 10, 5, 10));
                        vb.setStyle("-fx-border-color: white; -fx-border-width: 2");
                        Label song_name = new Label(item.getName());
                        song_name.setStyle("-fx-font-size: 16; -fx-font-weight: bold");
                        vb.getStyleClass().add("item-card");
                        vb.getChildren().addAll(song_name, getSong(item));
                        vb.setOnMouseEntered((event -> {
                            vb.setStyle("-fx-effect: dropshadow(three-pass-box, white, 5, 0, 1, 0);");

                            TranslateTransition t = new TranslateTransition();
                            t.setNode(vb);
                            t.setDuration(new Duration(60));
                            t.setToX(10);
                            t.play();
                        }));
                        vb.setOnMouseExited((event -> {
                            vb.setStyle("-fx-effect: dropshadow(three-pass-box, white,0, 0, 0, 0);");
                            TranslateTransition t = new TranslateTransition();
                            t.setNode(vb);
                            t.setDuration(new Duration(60));
                            t.setToX(0);
                            t.play();
                        }));
                        petitionBox.getChildren().add(vb);
                        //Aquí iría el código para pasar los datos del usuario a la vista perfil
                    }
                    container.getChildren().add(itemBar);
                }
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
    }









}
