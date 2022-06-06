package org.iesmurgi.proyectolevidaviddam;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import java.io.IOException;

@ExtendWith(ApplicationExtension.class)

class GeneralTest {
    VBox mainroot;
    Stage mainstage;

    @Start
    public void start(Stage stage) throws IOException {
        mainroot = FXMLLoader.load(getClass().getResource("log_in.fxml"));
        mainstage = stage;
        stage.setScene(new Scene(mainroot, 800, 800));
        stage.show();
        stage.toFront();
    }


    @Test
    void labels_botonesExisten(FxRobot robot) {

    }

}