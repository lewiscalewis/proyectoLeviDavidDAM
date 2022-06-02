package org.iesmurgi.proyectolevidaviddam.Middleware;


import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.net.URL;

public class MusickPlayer {

    private Media media;
    private MediaPlayer player;
    private String url;
    private MediaView mediaView;

    public MusickPlayer(String url_mp3){
        this.url = url_mp3;
        this.media = new Media(url);
        this.player = new MediaPlayer(media);
        this.player.setAutoPlay(true);
    }

    public Node getControl(){
        player.stop();
        MediaControl mediaControl = new MediaControl(this.player);
        mediaControl.setStyle("-fx-background-color: #1c3787; -fx-max-width: 600; -fx-fill: white; -fx-min-width: 400; -fx-text-fill: white");
        return mediaControl;
    }
}
