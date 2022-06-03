package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.web.WebView;
import org.iesmurgi.proyectolevidaviddam.HelloApplication;
import org.iesmurgi.proyectolevidaviddam.Middleware.*;
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

    @FXML
    private TextField textfieldBrowser;

    @FXML
    private HBox hboxContainer;

    @FXML
    private ComboBox<String> comboboxGenero;

    static Label labelSongNamePlayer = new Label();

    static Hyperlink hyperlinkUsernamePlayer = new Hyperlink();

    static ImageView imageviewPlayer = new ImageView();

    private static VBox vBoxPlayer;

    Thread player_thread = new Thread();

    static MusickPlayer player = new MusickPlayer();


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

    public void setVboxPlayer(VBox vbox){
        vBoxPlayer = vbox;
    }

    public void setItemsFromFXML(Label label, Hyperlink hyperlink, ImageView img){
        labelSongNamePlayer = label;
        hyperlinkUsernamePlayer = hyperlink;
        imageviewPlayer = img;
    }

    public void testHomepageController(){
        System.out.println("TEST OK....");
    }

    //Devuelve el nodo de la interfaz de una interfaz nosotros le pasamos un objeto cancion de la base de datos.
    Node getSong(Item item) throws IOException, URISyntaxException {
        String songName = item.getName();
        String author = item.getUsername();
        Image portada = new Image(new URL("https://cdn-icons-png.flaticon.com/512/13/13510.png").toURI().toURL().toExternalForm());


        HBox hbox = new HBox();
        hbox.setStyle("-fx-background-color: #eaeaea;");
        hbox.setAlignment(Pos.TOP_LEFT);

        VBox song = new VBox();
        song.setAlignment(Pos.TOP_LEFT);
        song.setPrefWidth(350);
        song.setMaxWidth(350);
        song.setMinWidth(350);


        //Label del título
        Label labelSongName = new Label();
        labelSongName.setMaxWidth(350);
        labelSongName.setMinWidth(350);
        labelSongName.setStyle( "" +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: black;" +
                "-fx-fill: black;" +
                "-fx-font-size: 40; " +
                "-fx-font-family: Sylfaen");

        //labelSongName.setMinWidth(100);
        //Hyperlink del autor
        Hyperlink hyperlinkAuthor = new Hyperlink();
        Label labelDescription = new Label();
        hyperlinkAuthor.setOnAction((event -> {
            loadProfile(author);
        }));


        hyperlinkAuthor.setFont(new Font(30));


        hyperlinkAuthor.setText(author);
        labelSongName.setText(songName);
        labelDescription.setText(item.description == null ? "Canción subida por "+item.getUsername() : "Descripción: "+item.description);
        labelDescription.setStyle(
                "-fx-text-fill: black; -fx-fill: black; -fx-font-family: Bahnschrift; -fx-font-weight: bold; -fx-font-size: 16");
        //hyperlinkAuthor.setMaxWidth(Double.MAX_VALUE);

        hyperlinkAuthor.setAlignment(Pos.TOP_LEFT);


        //Imagen
        ImageView imageView = new ImageView();
        imageView.setFitHeight(120);
        imageView.setFitWidth(120);

        //obtenemos la imagen de la canción
        Platform.runLater(()->{
            String url = CONSTANT.URL.getUrl()+"/download-cover";
            FileGetter fileGetter = null;
            try {
                fileGetter = new FileGetter(url);
                fileGetter.addParam("itemid", String.valueOf(item.getId()));
                fileGetter.addParam("token", new TokenManager().getToken());
                imageView.imageProperty().bind(fileGetter.getImage().imageProperty());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button buttonPlay;
        buttonPlay = new Button();
        //buttonPlay.setFont(new Font(100));
        buttonPlay.setText("▶");


        System.out.println(item.getId());

        buttonPlay.setOnAction((event)->{
            //Cuando se pulsa el botón de play label,hyperlink y el imgview de hellocontroller se establecen con los valores del item
            //además se añaden dos botones de play y pause de la canción
            try{
                player.stop_music();
            }catch (NullPointerException e) {
                e.printStackTrace();
            }
            player_thread = new Thread(()-> Platform.runLater(()->{
                String img = String.valueOf(item.getId());
                String url = CONSTANT.URL.getUrl()+"/download-item/"+img;
                player.setPlayer(url);

                labelSongNamePlayer.setText(item.getName());

                //Hyperlink del autor
                hyperlinkUsernamePlayer.setText(item.getUsername());

                try {
                    imageviewPlayer.setImage(new Image(requestProfileImage(new GeneralDecoder().getUserFromToken())));
                    imageviewPlayer.setFitWidth(70);
                    imageviewPlayer.setFitHeight(70);

                    vBoxPlayer.getChildren().clear();
                    vBoxPlayer.getChildren().addAll(player.getControl());
                    vBoxPlayer.setAlignment(Pos.CENTER);
                    //Aqui no hay que cargar la imagen de usuario sino el COVER!!!!!!!
                    //HAY QUE CREAR UN REQUESTCOVER(itemid)
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
            player_thread.start();

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
                        songFile = downloadAndStore(item.getId()).readAllBytes();
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
        labelCopyright.setText(item.copyright == 0 ? "Uso libre" : "Todos los derechos reservados");
        labelCopyright.setStyle(
                "-fx-text-fill: black; -fx-fill: black; -fx-font-family: Bahnschrift; -fx-font-weight: bold; -fx-font-size: 16");


        labelDescription.setPadding(new Insets(0,0,0,0));
        buttonPlay.getStyleClass().add("buttons-item");
        buttonDownload.getStyleClass().add("buttons-item");

        //sería recomendable añadir un progressIndicator para cuando la imagen tarda en llegar

        hbox.getChildren().addAll(song,buttonPlay,buttonDownload, imageView);
        hbox.setPadding(new Insets(5,5,5,5));
        hbox.setAlignment(Pos.CENTER);
        song.getChildren().addAll(labelSongName,hyperlinkAuthor,labelDescription,labelCopyright);
        song.setPadding(new Insets(5,5,5,5));
        song.setStyle("-fx-background-color: white");
        song.setAlignment(Pos.CENTER);
        hbox.setSpacing(15);


        return hbox;
    }

    static InputStream downloadAndStore(int id) throws IOException {

        String URL= "http://tux.iesmurgi.org:11230/download-item/"+id;
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
        params.put("username", username);                  //La petición no llega al servidor cuando le pongo parámetros
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
            if(items.length > 0) {
                ScrollPane itemBar = new ScrollPane();
                VBox petitionBox = new VBox();
                petitionBox.setPadding(new Insets(10, 10, 10, 10));
                itemBar.setContent(petitionBox);
                petitionBox.setSpacing(30);
                itemBar.setMinWidth(285);
                petitionBox.setAlignment(Pos.CENTER);

                petitionBox.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                        itemBar.getViewportBounds().getWidth(), itemBar.viewportBoundsProperty()));

                Arrays.stream(items).forEach(item ->{
                    VBox vb = new VBox();
                    vb.setStyle("-fx-background-color: whitesmoke;");
                    vb.setSpacing(0);
                    vb.setAlignment(Pos.TOP_LEFT);
                    vb.setPadding(new Insets(0, 0, 0, 0));
                    //vb.setStyle("-fx-border-color: white; -fx-border-width: 2");
                    //Label song_name = new Label(item.getName());
                    //song_name.setStyle("-fx-font-size: 16; -fx-font-weight: bold");
                    vb.getStyleClass().add("item-card");
                    try {
                        vb.getChildren().addAll(getSong(item));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    vb.setOnMouseEntered((event -> {
                        //Las transiciones quedan recortadas por el contenedor padre
                        vb.setStyle("-fx-effect: dropshadow(three-pass-box, white, 10, 0.8, 1.2, 1.2);-fx-background-color:whitesmoke;");
                        TranslateTransition t = new TranslateTransition();
                        t.setNode(vb);
                        t.setDuration(new Duration(60));
                        t.setToX(5);
                        t.play();
                    }));
                    vb.setOnMouseExited((event -> {
                        vb.setStyle("-fx-background-color: whitesmoke");
                        TranslateTransition t = new TranslateTransition();
                        t.setNode(vb);
                        t.setDuration(new Duration(60));
                        t.setToX(0);
                        t.play();
                    }));
                    petitionBox.getChildren().add(vb);
                });

                container.getChildren().add(itemBar);
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




    private void loadProfile(String username){

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(baseRoot);
        //((HBox) event.getTarget()).setTranslateY(-6);


        slide.setToX(6000);
        slide.play();
        slide.setOnFinished((event -> {

            baseRoot.setTranslateX(-6000);
            TranslateTransition slide2 = new TranslateTransition();
            slide2.setDuration(Duration.seconds(0.4));
            slide2.setNode(baseRoot);
            //((HBox) event.getTarget()).setTranslateY(-6);


            slide2.setToX(0);

            try {
                baseRoot.setAlignment(Pos.TOP_LEFT);
                baseRoot.getChildren().clear();
                FXMLLoader rootFxmlLoader=new FXMLLoader(
                        HelloApplication.class.getResource(
                                "profilepage.fxml"
                        )
                );
                Pane root = rootFxmlLoader.load();
                ProfilepageController profilepageController = rootFxmlLoader.getController();
                profilepageController.loadUserData(username);
                baseRoot.getChildren().add(root);

                //ProfilepageController profilepageController =rootFxmlLoader.getController();
                //profilepageController.loadUserData();
                ((Stage)root.getScene().getWindow()).setMinWidth(1000);
                ((Stage)root.getScene().getWindow()).setMinHeight(850);

            } catch (IOException e) {
                e.printStackTrace();
            }

            slide2.play();
            slide2.setOnFinished((event2)->{

            });

        }));
    }
}
