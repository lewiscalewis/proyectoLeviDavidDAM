module org.iesmurgi.proyectolevidaviddam {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.google.gson;


    opens org.iesmurgi.proyectolevidaviddam to javafx.fxml,com.google.gson;
    exports org.iesmurgi.proyectolevidaviddam;
}