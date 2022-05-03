package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.iesmurgi.proyectolevidaviddam.HelloApplication;
import org.iesmurgi.proyectolevidaviddam.Middleware.TokenManager;
import org.iesmurgi.proyectolevidaviddam.models.User;

import java.io.IOException;

public class HelloController {


    @FXML
    private VBox pageRoot;
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
    private VBox mainContainer;
    @FXML
    private GridPane gridRoot;
    @FXML
    private ImageView imageviewProfileImage;
    @FXML
    private Label tileSettings;
    @FXML
    private Label tileSettings3;
    @FXML
    private Label tileSettings2;
    @FXML
    private Label tileSettings1;
    @FXML
    private Label labelTopMenu1;
    @FXML
    private Label labelTopMenu3;
    @FXML
    private Label labelTopMenu2;
    @FXML
    private HBox hboxTopMenu;


    public void initialize() throws IOException {
        chatSlider.setTranslateX(265);


    }


    public void loadUserData(User user){
        hyperlinkUser.setText(user.getName());
        hyperlinkUser.setOnAction(event->{

        });

    }
    boolean chatOpen=true;
    @FXML
    public void loadHomePage() throws IOException {

//        try {
//            // EJEMPLO
//            Map<String, String> headers = new HashMap<>();
//            headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
//            FileGetter multipart = new FileGetter();
//            multipart.HttpPostMultipart(CONSTANT.URL.getUrl()+"/image", "utf-8", headers);
//            // Add form field
//            multipart.addFormField("username", new GeneralDecoder().getUserFromToken());
//            // Add file
//            multipart.addFilePart("image", new File("C:\\Users\\lewis\\IdeaProjects\\proyectoLeviDavidDAM\\src\\main\\resources\\org\\iesmurgi\\proyectolevidaviddam\\images\\putin.jpg"));
//            // Print result
//            String response = multipart.finish();
//            System.out.println(response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(pageRoot);
        //((HBox) event.getTarget()).setTranslateY(-6);


        slide.setToX(6000);
        slide.play();
        slide.setOnFinished((event -> {

            pageRoot.setTranslateX(-6000);
            TranslateTransition slide2 = new TranslateTransition();
            slide2.setDuration(Duration.seconds(0.4));
            slide2.setNode(pageRoot);
            //((HBox) event.getTarget()).setTranslateY(-6);


            slide2.setToX(0);

            try {
                pageRoot.setAlignment(Pos.TOP_LEFT);
                pageRoot.getChildren().clear();
                FXMLLoader rootFxmlLoader=new FXMLLoader(
                        HelloApplication.class.getResource(
                                "homepage.fxml"
                        )
                );
                Pane root = rootFxmlLoader.load();
                pageRoot.getChildren().add(root);


                ((Stage)root.getScene().getWindow()).setMinWidth(900);
                ((Stage)root.getScene().getWindow()).setMinHeight(500);

            } catch (IOException e) {
                e.printStackTrace();
            }


            slide2.play();
            slide2.setOnFinished((event2)->{

            });

        }));

    }



    public void firstLoadHomePage()  {


        try {
            pageRoot.setAlignment(Pos.TOP_LEFT);
            pageRoot.getChildren().clear();
            FXMLLoader rootFxmlLoader=new FXMLLoader(
                    HelloApplication.class.getResource(
                            "homepage.fxml"
                    )
            );
            Pane root = rootFxmlLoader.load();
            pageRoot.getChildren().add(root);


            ((Stage)pageRoot.getScene().getWindow()).setMinWidth(900);
            ((Stage)pageRoot.getScene().getWindow()).setMinHeight(500);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadProfilePage()  {

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(pageRoot);
        //((HBox) event.getTarget()).setTranslateY(-6);


        slide.setToX(6000);
        slide.play();
        slide.setOnFinished((event -> {

            pageRoot.setTranslateX(-6000);
            TranslateTransition slide2 = new TranslateTransition();
            slide2.setDuration(Duration.seconds(0.4));
            slide2.setNode(pageRoot);
            //((HBox) event.getTarget()).setTranslateY(-6);


            slide2.setToX(0);

            try {
                pageRoot.setAlignment(Pos.TOP_LEFT);
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

            } catch (IOException e) {
                e.printStackTrace();
            }

            slide2.play();
            slide2.setOnFinished((event2)->{

            });

        }));

    }

    @FXML
    public void loadUploadPage()  {

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(pageRoot);
        //((HBox) event.getTarget()).setTranslateY(-6);


        slide.setToX(6000);
        slide.play();
        slide.setOnFinished((event -> {

            pageRoot.setTranslateX(-6000);
            TranslateTransition slide2 = new TranslateTransition();
            slide2.setDuration(Duration.seconds(0.4));
            slide2.setNode(pageRoot);
            //((HBox) event.getTarget()).setTranslateY(-6);


            slide2.setToX(0);

            try {
                pageRoot.setAlignment(Pos.TOP_LEFT);
                pageRoot.getChildren().clear();
                FXMLLoader rootFxmlLoader=new FXMLLoader(
                        HelloApplication.class.getResource(
                                "uploadpage.fxml"
                        )
                );
                Pane root = rootFxmlLoader.load();
                pageRoot.getChildren().add(root);


                ((Stage)root.getScene().getWindow()).setMinWidth(900);
                ((Stage)root.getScene().getWindow()).setMinHeight(500);

            } catch (IOException e) {
                e.printStackTrace();
            }

            slide2.play();
            slide2.setOnFinished((event2)->{

            });

        }));

    }


    @FXML
    public void logout(Event e) throws IOException {
        TokenManager tk = new TokenManager();
        tk.deleteToken();
        try {
            mainContainer.setAlignment(Pos.CENTER);


            //ANIMACION DE TRANSICION DE VENTANA
            Platform.runLater(()->{
                TranslateTransition slide = new TranslateTransition();
                slide.setDuration(Duration.seconds(1));
                slide.setNode(pageRoot);
                //((HBox) event.getTarget()).setTranslateY(-6);


                slide.setToY(-5000);

                slide.setOnFinished((event)->{
                    mainContainer.getChildren().clear();
                    FXMLLoader rootFxmlLoader=new FXMLLoader(
                            HelloApplication.class.getResource(
                                    "log_in.fxml"
                            )
                    );
                    Pane root = null;
                    try {
                        root = rootFxmlLoader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    mainContainer.getChildren().add(root);
                    pageRoot.setTranslateY(0);
                });
                slide.play();
                hboxTopMenu.setVisible(false);



            });

        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    @FXML
    public void loadContactsPage() {

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(pageRoot);
        //((HBox) event.getTarget()).setTranslateY(-6);


        slide.setToX(6000);
        slide.play();
        slide.setOnFinished((event -> {

            pageRoot.setTranslateX(-6000);
            TranslateTransition slide2 = new TranslateTransition();
            slide2.setDuration(Duration.seconds(0.4));
            slide2.setNode(pageRoot);
            //((HBox) event.getTarget()).setTranslateY(-6);


            slide2.setToX(0);

            try {
                pageRoot.setAlignment(Pos.TOP_LEFT);
                pageRoot.getChildren().clear();
                FXMLLoader rootFxmlLoader=new FXMLLoader(
                        HelloApplication.class.getResource(
                                "contactspage.fxml"
                        )
                );
                Pane root = rootFxmlLoader.load();
                pageRoot.getChildren().add(root);


                ((Stage)root.getScene().getWindow()).setMinWidth(900);
                ((Stage)root.getScene().getWindow()).setMinHeight(500);

            } catch (IOException e) {
                e.printStackTrace();
            }
            slide2.play();
            slide2.setOnFinished((event2)->{
            });
        }));
    }

    private void animateTransition() {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.8));
        slide.setNode(pageRoot);
        //((HBox) event.getTarget()).setTranslateY(-6);


        slide.setToX(6000);
        slide.play();
        slide.setOnFinished((event -> {
            pageRoot.setTranslateX(0);
        }));
    }

    @FXML
    public void slideChatSlider(Event event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode((VBox) event.getTarget());
        //((HBox) event.getTarget()).setTranslateY(-6);
        slide.setToX(0);
        slide.play();
    }

    @FXML
    public void slideChatSliderExited(Event event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode((VBox) event.getTarget());
        slide.setToX(265);
        slide.play();
    }

    @FXML
    public void onSlideHoverExited(Event event) {
        ((HBox) event.getTarget()).setStyle("-fx-background-color:#1c3787; ");
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.1));
        slide.setNode((HBox) event.getTarget());
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
        ((HBox) event.getTarget()).setStyle("-fx-background-color: #1c3787;");
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