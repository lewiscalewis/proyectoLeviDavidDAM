package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class HomepageController {
    @javafx.fxml.FXML
    private GridPane gridRoot;
    @javafx.fxml.FXML
    private ColumnConstraints columnConstraints3;
    @javafx.fxml.FXML
    private VBox chatSlider;

    public VBox getChatSlider() {
        return chatSlider;
    }

    boolean chatOpen=true;
    public void initialize(){
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

        //Esto es porque para expandirse a todoo lo que ocupe la ventana, necesita indicarselo al padre del gridRoot, que en este caso
        //es el AnchorPane del hello-view.fxml. con fxid pageRoot
        ((AnchorPane)gridRoot.getParent()).setLeftAnchor(gridRoot,0.0);
        ((AnchorPane)gridRoot.getParent()).setTopAnchor(gridRoot,0.0);
        ((AnchorPane)gridRoot.getParent()).setRightAnchor(gridRoot,0.0);
        ((AnchorPane)gridRoot.getParent()).setBottomAnchor(gridRoot,0.0);



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

}
