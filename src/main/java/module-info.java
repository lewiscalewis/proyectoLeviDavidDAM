module org.iesmurgi.proyectolevidaviddam {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;
    requires javaee.api;
    requires com.google.gson;
    requires org.kordamp.bootstrapfx.core;
    requires socket.io.client;
    requires engine.io.client;


    opens org.iesmurgi.proyectolevidaviddam.models to com.google.gson;
    opens org.iesmurgi.proyectolevidaviddam to javafx.fxml,com.google.gson;
    exports org.iesmurgi.proyectolevidaviddam;
    exports org.iesmurgi.proyectolevidaviddam.Controllers;
    opens org.iesmurgi.proyectolevidaviddam.Controllers to com.google.gson, javafx.fxml,javafx.base;
    opens org.iesmurgi.proyectolevidaviddam.Middleware to com.google.gson;
}