package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import org.iesmurgi.proyectolevidaviddam.Enviroment.CONSTANT;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javafx.scene.web.WebView;
import org.iesmurgi.proyectolevidaviddam.Middleware.Requester;
import org.iesmurgi.proyectolevidaviddam.Middleware.TokenManager;
import org.iesmurgi.proyectolevidaviddam.models.FriendRequest;
import org.iesmurgi.proyectolevidaviddam.models.Item;

//Dentro de contentRoot es donde se supone que va el contenido de nuestra página. Es para que el chatSlider se superponga encima de esta vista.
public class HomepageController {

    @javafx.fxml.FXML
    private StackPane baseRoot;

    @FXML
    private FlowPane container;

    @FXML
    private ScrollPane scrollPane;

    static VBox vboxPlayer;
    static WebView webviewPlayer;
    static WebEngine webEngine;


    public void initialize() throws IOException, URISyntaxException {

        //Esto es porque para expandirse a todoo lo que ocupe la ventana, necesita indicarselo al padre del gridRoot, que en este caso
        //es el AnchorPane del hello-view.fxml. con fxid pageRoot
        ((AnchorPane) baseRoot.getParent()).setLeftAnchor(baseRoot, 0.0);
        ((AnchorPane) baseRoot.getParent()).setTopAnchor(baseRoot, 0.0);
        ((AnchorPane) baseRoot.getParent()).setRightAnchor(baseRoot, 0.0);
        ((AnchorPane) baseRoot.getParent()).setBottomAnchor(baseRoot, 0.0);

        container.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));

        loadItems();

    }

    public void setWebViewPlayer(WebView webviewPlayer, WebEngine webEngine, VBox vboxPlayer1){
        HomepageController.webviewPlayer =webviewPlayer;
        HomepageController.webEngine =webEngine;
        vboxPlayer=vboxPlayer1;
    }


    public static void play(String itemid){
        webEngine.load(null);   //STOP MUSIC BEFORE STARTING AGAIN
        webEngine.load(CONSTANT.URL.getUrl()+"/download-item/"+itemid);
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
        vboxPlayer.setStyle("-fx-background-color:black;");
        vboxPlayer.setTranslateZ(-1);
        vboxPlayer.setAlignment(Pos.TOP_CENTER);

        webviewPlayer.setMaxWidth(500);
        webviewPlayer.setMaxHeight(65);
        webviewPlayer.setMinHeight(65);
        webviewPlayer.setTranslateY(56);
        webviewPlayer.setScaleX(2);
        webviewPlayer.setScaleY(2);


    }

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


        VBox song = new VBox();
        song.setAlignment(Pos.TOP_LEFT);
        song.setPrefWidth(600);
        song.setMaxWidth(600);
        song.setMinWidth(600);


        //Label del título
        Label labelSongName = new Label();
        labelSongName.setMaxWidth(Double.MAX_VALUE);
        labelSongName.setStyle( "-fx-font-weight: bold; " +
                                "-fx-text-fill: black;" +
                                "-fx-fill: black;" +
                                "-fx-font-size: 16");

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
        buttonPlay = new Button("❤❤❤❤");


        System.out.println(item.getId());

        buttonPlay.setOnAction((event)->play(String.valueOf(item.getId())));


        song.getChildren().addAll(labelSongName,hyperlinkAuthor,imageView,buttonPlay);
        hbox.getChildren().addAll(song,imageView);


        return hbox;
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
