package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.scene.layout.*;


//Dentro de contentRoot es donde se supone que va el contenido de nuestra página. Es para que el chatSlider se superponga encima de esta vista.
public class UploadpageController {
    @javafx.fxml.FXML
    private StackPane baseRoot;

    public void initialize(){

        //Esto es porque para expandirse a todoo lo que ocupe la ventana, necesita indicarselo al padre del gridRoot, que en este caso
        //es el AnchorPane del hello-view.fxml. con fxid pageRoot
        ((AnchorPane)baseRoot.getParent()).setLeftAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setTopAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setRightAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot   .getParent()).setBottomAnchor(baseRoot,0.0);



    }


}
