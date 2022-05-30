package org.iesmurgi.proyectolevidaviddam.Controllers;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;


//Dentro de contentRoot es donde se supone que va el contenido de nuestra página. Es para que el chatSlider se superponga encima de esta vista.
public class UploadpageController {
    @javafx.fxml.FXML
    private StackPane baseRoot;


    FileChooser fileChooser;
    @javafx.fxml.FXML
    private ChoiceBox choiceboxLicenseType;
    @javafx.fxml.FXML
    private Button buttonChooseFile;
    @javafx.fxml.FXML
    private VBox vboxForm;
    @javafx.fxml.FXML
    private Button buttonTryUpload;
    @javafx.fxml.FXML
    private TextArea textareaDescription;
    @javafx.fxml.FXML
    private ImageView imageviewSongCover;

    public void initialize(){
        fileChooser=new FileChooser();

        buttonChooseFile.setOnAction((event)->{
            openFileChooser();
        });

        //Esto es porque para expandirse a todoo lo que ocupe la ventana, necesita indicarselo al padre del gridRoot, que en este caso
        //es el AnchorPane del hello-view.fxml. con fxid pageRoot
        ((AnchorPane)baseRoot.getParent()).setLeftAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setTopAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot.getParent()).setRightAnchor(baseRoot,0.0);
        ((AnchorPane)baseRoot   .getParent()).setBottomAnchor(baseRoot,0.0);



    }

    void openFileChooser(){
        fileChooser.showOpenDialog(baseRoot.getScene().getWindow());
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("png"));
    }

}
