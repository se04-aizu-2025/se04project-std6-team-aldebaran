package com.aldebaran.stellasort.component;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

public class PlayButton {
    private static final String PLAY_ICON_FILE = "/com/aldebaran/stellasort/images/play.png";
    private static final String PAUSE_ICON_FILE = "/com/aldebaran/stellasort/images/pause.png";

    private final Image playIcon;
    private final Image pauseIcon;

    private final Button button;
    private final ImageView buttonIcon;

    private boolean isPlaying = false;

    public PlayButton(Button button) {
        this.button = button;

        playIcon = loadIcon(PLAY_ICON_FILE);
        pauseIcon = loadIcon(PAUSE_ICON_FILE);

        Node graphic = button.getGraphic();
        if (graphic instanceof ImageView iv) {
            System.out.println("Button already has an icon.");
            this.buttonIcon = iv;
        } else {
            this.buttonIcon = new ImageView();
            this.button.setGraphic(this.buttonIcon);
        }

        this.buttonIcon.setPreserveRatio(true);
        this.buttonIcon.setFitWidth(16);
        this.buttonIcon.setFitHeight(16);

        buttonIcon.setImage(playIcon);
    }

    public void togglePlayPause() {
        System.out.println("Play button clicked.");
        isPlaying = !isPlaying;
        buttonIcon.setImage((isPlaying) ? pauseIcon : playIcon);
    }

    private static Image loadIcon(String resourcePath) {
        URL url = PlayButton.class.getResource(resourcePath);
        if (url == null) {
            System.out.println("Icon resource not found on classpath: " + resourcePath);
            throw new IllegalStateException("Icon resource not found on classpath: " + resourcePath);
        }
        System.out.println("Icon resource found on classpath: " + resourcePath);
        return new Image(url.toExternalForm());
    }
}
