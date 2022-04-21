package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import org.iesmurgi.proyectolevidaviddam.HelloApplication;

import java.io.IOException;


//Dentro de contentRoot es donde se supone que va el contenido de nuestra página. Es para que el chatSlider se superponga encima de esta vista.
public class HomepageController {
    @javafx.fxml.FXML
    private StackPane baseRoot;

    boolean chatOpen=true;
    public void initialize(){

        //Esto es porque para expandirse a todoo lo que ocupe la ventana, necesita indicarselo al padre del gridRoot, que en este caso
        //es el AnchorPane del hello-view.fxml. con fxid pageRoot
        ((AnchorPane)baseRoot.getParent()).setLeftAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setTopAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setRightAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot   .getParent()).setBottomAnchor(baseRoot,0.0);

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

    }




}
