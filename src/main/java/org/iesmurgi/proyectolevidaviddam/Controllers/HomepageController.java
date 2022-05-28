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
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.web.WebView;
import org.iesmurgi.proyectolevidaviddam.Middleware.GeneralDecoder;
import org.iesmurgi.proyectolevidaviddam.Middleware.Requester;
import org.iesmurgi.proyectolevidaviddam.Middleware.TokenManager;
import org.iesmurgi.proyectolevidaviddam.models.FriendRequest;
import org.iesmurgi.proyectolevidaviddam.models.Item;

//Dentro de contentRoot es donde se supone que va el contenido de nuestra página. Es para que el chatSlider se superponga encima de esta vista.
public class HomepageController {

    @javafx.fxml.FXML
    private StackPane baseRoot;

    @FXML
    private  FlowPane container;

    @FXML
    private ScrollPane scrollPane;

    static VBox vboxPlayer;
    static WebView webviewPlayer;
    static WebEngine webEngine;
    @FXML
    private TextField textfieldBrowser;
    @FXML
    private HBox hboxContainer;
    @FXML
    private ComboBox<String> comboboxGenero;


    public void initialize() throws IOException, URISyntaxException {
        String url = CONSTANT.URL.getUrl()+"/all-items";
        Requester<Item[]> req = new Requester<>(url, Requester.Method.POST, Item[].class);
        req.addParam("token", new TokenManager().getToken());
        Item[] items;
        items = req.execute();
        loadItems(items);
        //Esto es porque para expandirse a todoo lo que ocupe la ventana, necesita indicarselo al padre del gridRoot, que en este caso
        //es el AnchorPane del hello-view.fxml. con fxid pageRoot
        //((AnchorPane) baseRoot.getParent()).setLeftAnchor(baseRoot, 0.0);
        //((AnchorPane) baseRoot.getParent()).setTopAnchor(baseRoot, 0.0);
        //((AnchorPane) baseRoot.getParent()).setRightAnchor(baseRoot, 0.0);
        //((AnchorPane) baseRoot.getParent()).setBottomAnchor(baseRoot, 0.0);

        container.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));

        comboboxGenero.getItems().addAll(
                "Rap",
                "Trap",
                "Pop",
                "Drill",
                "Rock",
                "Metal",
                "Reggaeton",
                "Drum and bass",
                "Reggae",
                "Todos los géneros"
        );
        comboboxGenero.setValue("Todos los géneros");


    }

    public void setWebViewPlayer(WebView webviewPlayer, WebEngine webEngine, VBox vboxPlayer1, Label labelSongNamePlayer2, ImageView imageViewPlayer2, Hyperlink hyperlinkUsernamePlayer2){///////////////////////////////////////////
        labelSongNamePlayer=labelSongNamePlayer2;
        hyperlinkUsernamePlayer=hyperlinkUsernamePlayer2;
        imageviewPlayer=imageViewPlayer2;
        HomepageController.webviewPlayer =webviewPlayer;
        HomepageController.webEngine =webEngine;

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
    static Node getSong(Item item) throws IOException, URISyntaxException {
        String songName = item.getName();
        String author = item.getUsername();
        Image portada = new Image(new URL("https://cdn-icons-png.flaticon.com/512/13/13510.png").toURI().toURL().toExternalForm());


        HBox hbox = new HBox();
        hbox.setStyle("-fx-background-color: black;");
        hbox.setAlignment(Pos.TOP_LEFT);
        hbox.setStyle("-fx-background-color: #e45926;");

        VBox song = new VBox();
        song.setAlignment(Pos.TOP_LEFT);
        song.setPrefWidth(350);
        song.setMaxWidth(350);
        song.setMinWidth(350);


        //Label del título
        Label labelSongName = new Label();
        labelSongName.setMaxWidth(350);
        labelSongName.setMinWidth(350);
        labelSongName.setStyle( "-fx-font-weight: bold; " +
                "-fx-text-fill: black;" +
                "-fx-fill: black;" +
                "-fx-font-size: 44; -fx-background-color: #ffffff;");

        //labelSongName.setMinWidth(100);
        //Hyperlink del autor
        Hyperlink hyperlinkAuthor = new Hyperlink();
        Label labelDescription = new Label();



        hyperlinkAuthor.setFont(new Font(30));


        hyperlinkAuthor.setText(author);
        labelSongName.setText(songName);
        labelDescription.setText(item.description);
        labelDescription.setText("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        labelDescription.setStyle(
                "-fx-text-fill: black; -fx-fill: black;");
        //hyperlinkAuthor.setMaxWidth(Double.MAX_VALUE);

        hyperlinkAuthor.setAlignment(Pos.TOP_LEFT);


        //Imagen
        ImageView imageView = new ImageView();
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);

        imageView.setImage(portada);


        Button buttonPlay;
        buttonPlay = new Button();
        //buttonPlay.setFont(new Font(100));
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



        });

        Button buttonDownload = new Button("download");
        buttonDownload.setOnAction((event -> {


            FileChooser saveChooser = new FileChooser();
            saveChooser.setTitle("Save");
            saveChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3", "*.mp3"));
            //Adding action on the menu item
            Platform.runLater(()->{
            File saveFile = saveChooser.showSaveDialog(buttonPlay.getScene().getWindow());
                Thread th = new Thread(()->{

                    try {
                        byte[] songFile= new byte[0];
                        songFile = downloadAndStore(1).readAllBytes();
                        FileOutputStream fosFile=new FileOutputStream(saveFile);
                        fosFile.write(songFile);
                        fosFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                th.setDaemon(true);
                th.start();
            });


        }));




        Label labelCopyright = new Label();
        labelCopyright.setText("Free use.");
        labelCopyright.setStyle(
                "-fx-text-fill: black; -fx-fill: black; -fx-background-color: #44bb44;");

        if(item.copyright==1){
            System.out.println("Tiene copyright");
            labelCopyright.setText("® All rights reserved.");
            labelCopyright.setStyle(
                    "-fx-text-fill: black; -fx-fill: black; -fx-background-color: #bb4444;");
        }

        labelDescription.setPadding(new Insets(0,0,0,0));
        song.getChildren().addAll(labelSongName,hyperlinkAuthor,labelDescription,labelCopyright,imageView);
        hbox.getChildren().addAll(song,buttonPlay,buttonDownload,imageView);


        return hbox;
    }

    static InputStream downloadAndStore(int id) throws IOException {

        String URL= "http://tux.iesmurgi.org:11230/download-item/"+String.valueOf(id);
        java.net.URL server = new java.net.URL(URL);
        // Open a connection(?) on the URL(??) and cast the response(???)
        HttpURLConnection connection = (HttpURLConnection) server.openConnection();

        // Now it's "open", we can set the request method, headers etc.
        connection.setRequestProperty("accept", "application/x-www-form-urlencoded");
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);

        InputStream responseStream = connection.getInputStream();


        return responseStream;
    }
    static InputStream requestProfileImage(String username) throws IOException {
        String URL= "http://tux.iesmurgi.org:11230/download-image";
        java.net.URL server = new java.net.URL(URL);
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
    private  void loadItems(Item[] items){
        container.getChildren().clear();
        Platform.setImplicitExit(true);
        Platform.runLater(() -> {
            try {
                if(items.length > 0) {
                    ScrollPane itemBar = new ScrollPane();
                    VBox petitionBox = new VBox();
                    itemBar.setContent(petitionBox);
                    petitionBox.setSpacing(30);
                    itemBar.setMinWidth(700);
                    itemBar.setMaxWidth(700);
                    petitionBox.setPadding(new Insets(0, 0, 0, 0));
                    petitionBox.setAlignment(Pos.CENTER);

                    petitionBox.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                            itemBar.getViewportBounds().getWidth(), itemBar.viewportBoundsProperty()));


                    for (Item item : items) {
                        VBox vb = new VBox();

                        vb.setSpacing(0);
                        vb.setAlignment(Pos.TOP_LEFT);
                        vb.setPadding(new Insets(0, 0, 0, 0));
                        vb.setStyle("-fx-border-color: white; -fx-border-width: 2");
                        //Label song_name = new Label(item.getName());
                        //song_name.setStyle("-fx-font-size: 16; -fx-font-weight: bold");
                        vb.getStyleClass().add("item-card");
                        vb.getChildren().addAll( getSong(item));
                        vb.setOnMouseEntered((event -> {
                            vb.setStyle("-fx-effect: dropshadow(three-pass-box, white, 5, 0, 1, 0);-fx-background-color:blue;");

                            TranslateTransition t = new TranslateTransition();
                            t.setNode(vb);
                            t.setDuration(new Duration(60));
                            t.setToX(10);
                            t.play();
                        }));
                        vb.setOnMouseExited((event -> {
                            vb.setStyle("-fx-effect: dropshadow(three-pass-box, white,0, 0, 0, 0);-fx-background-color:blue;");
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


    @FXML
    public void search(ActionEvent actionEvent) throws IOException, URISyntaxException {
        if(!textfieldBrowser.getText().equals("")){
            Requester<Item[]> req = new Requester(CONSTANT.URL.getUrl()+"/items-search", Requester.Method.POST, Item[].class);
            req.addParam("token", new TokenManager().getToken());
            req.addParam("item", textfieldBrowser.getText());
            req.addParam("genre", comboboxGenero.getValue().equals("Todos los géneros") ? "all" : comboboxGenero.getValue());
            Item[] items = req.execute();
            loadItems(items);
        }else{
            Requester<Item[]> req = new Requester(CONSTANT.URL.getUrl()+"/items-search-genre", Requester.Method.POST, Item[].class);
            req.addParam("token", new TokenManager().getToken());
            req.addParam("genre", comboboxGenero.getValue().equals("Todos los géneros") ? "all" : comboboxGenero.getValue());
            Item[] items = req.execute();
            loadItems(items);
        }

    }

    @FXML
    public void filterByName(Event event) throws IOException, URISyntaxException {
       ActionEvent e = null;
       search(e);
    }
}
