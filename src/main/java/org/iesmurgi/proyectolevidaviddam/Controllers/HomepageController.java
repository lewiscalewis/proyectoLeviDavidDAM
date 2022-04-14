package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.layout.*;
import javafx.util.Duration;


//Dentro de contentRoot es donde se supone que va el contenido de nuestra página. Es para que el chatSlider se superponga encima de esta vista.
public class HomepageController {
    @javafx.fxml.FXML
    private StackPane baseRoot;

    boolean chatOpen=true;
    public void initialize(){/*
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
            });
        });*/

        //Esto es porque para expandirse a todoo lo que ocupe la ventana, necesita indicarselo al padre del gridRoot, que en este caso
        //es el AnchorPane del hello-view.fxml. con fxid pageRoot
        ((AnchorPane)baseRoot.getParent()).setLeftAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setTopAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setRightAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot   .getParent()).setBottomAnchor(baseRoot,0.0);



    }




}
