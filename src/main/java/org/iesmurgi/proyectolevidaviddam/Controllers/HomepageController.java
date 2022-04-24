package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import org.iesmurgi.proyectolevidaviddam.HelloApplication;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;


//Dentro de contentRoot es donde se supone que va el contenido de nuestra página. Es para que el chatSlider se superponga encima de esta vista.
public class HomepageController {
    @javafx.fxml.FXML
    private StackPane baseRoot;

    boolean chatOpen=true;
    @javafx.fxml.FXML
    private VBox vboxMusic;

    public void initialize() throws MalformedURLException, FileNotFoundException, URISyntaxException {

        //Esto es porque para expandirse a todoo lo que ocupe la ventana, necesita indicarselo al padre del gridRoot, que en este caso
        //es el AnchorPane del hello-view.fxml. con fxid pageRoot
        ((AnchorPane)baseRoot.getParent()).setLeftAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setTopAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setRightAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot .getParent()).setBottomAnchor(baseRoot,0.0);

        Button buttonGoToSettings =new Button("Go to Settings");
        buttonGoToSettings.setOnAction(actionEvent -> {

            FXMLLoader fxmlLoader =new FXMLLoader(HelloApplication.class.getResource("profilepage.fxml"));

            //HomepageController homepageController = fxmlLoader.getController();

            baseRoot.getChildren().clear();
            Pane root = null;
            try {
                root = (fxmlLoader.load());


                baseRoot.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        baseRoot.getChildren().add(buttonGoToSettings);


        ///////////////////////7


        vboxMusic.setPadding(new Insets(30,30,30,30));
        vboxMusic.setSpacing(10);

        vboxMusic.getChildren().addAll(getSong(),getSong(),getSong(),getSong(),getSong());
        vboxMusic.setAlignment(Pos.CENTER);




    }

    //Devuelve el nodo de la interfaz de una interfaz nosotros le pasamos un objeto cancion de la base de datos.
    Node getSong() throws MalformedURLException, FileNotFoundException, URISyntaxException {

        String songName="*sONG NAME*";
        String author="ldubi01";
        Image portada=new Image(new URL("https://images.ecestaticos.com/9Sfa715zzc8Efc_taTLIbLkKeJ4=/0x0:2272x1501/1200x900/filters:fill(white):format(jpg)/f.elconfidencial.com%2Foriginal%2F38a%2F667%2Fee7%2F38a667ee7dbc85c2f8935eeff36be807.jpg").toURI().toURL().toExternalForm());;


        HBox hbox= new HBox();
        hbox.setAlignment(Pos.CENTER);




        VBox song=new VBox();
        song.setStyle("-fx-background-color: white;");
        song.setAlignment(Pos.TOP_LEFT);
        song.setPrefWidth(600);
        song.setMaxWidth(600);
        song.setMinWidth(600);


        //Label del título
        Label labelSongName = new Label();
        labelSongName.setMaxWidth(Double.MAX_VALUE);
        labelSongName.setText(songName);
        labelSongName.setFont(new Font(50d));

        //Hyperlink del autor
        Hyperlink hyperlinkAuthor = new Hyperlink();
        hyperlinkAuthor.setText(author);
        //hyperlinkAuthor.setMaxWidth(Double.MAX_VALUE);
        hyperlinkAuthor.setAlignment(Pos.TOP_LEFT);


        //Imagen
        ImageView imageView = new ImageView();
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        imageView.setImage(portada);






        song.getChildren().addAll(labelSongName,hyperlinkAuthor,imageView);

        hbox.getChildren().addAll(song,imageView);


        return hbox;
    }




}
