package mediaPlayer;

import AddSong.Song;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;

import java.util.*;

import javafx.scene.Group;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import java.io.InputStream;
import java.io.File;
import java.io.IOException;
import java.net.StandardSocketOptions;

import static java.lang.Math.floor;
import static java.lang.String.format;
import static javafx.application.Platform.runLater;


public class MediaPlayerController
{
    public Label titleLabel;
    public Label albumNamelabel;
    private Label time;
    Duration duration;

    @FXML
    public ProgressBar songProgression;
    @FXML
    public Button backback;
    @FXML
    public Button forfor;
    @FXML
    public Button playButton;
    @FXML
    public Button pauseButton;
    @FXML
    public Button backButton;
    @FXML
    public Button forwardButton;
    @FXML
    public Button albumCoverButton;
    @FXML
    public TitledPane mediaPlayerPane;
    @FXML
    public MediaPlayer mediaPlayer;
    @FXML
    public Media media;
    @FXML
    public MediaView mediaView;
    @FXML
    public Label timeLabel;
    @FXML
    public ImageView albumArt;
    @FXML
    public Button shuffleSongs;
    @FXML
    public Button repeatSong;


    private boolean replay;

    private boolean isPlaying = true;
    public Thread thread;
    public Duration maxTime;
    public Duration minTime;
    public Duration currTime;

    private ConcreteModelMediaPlayer model;

    public MediaPlayerController() {
        model = new ConcreteModelMediaPlayer();
        model.setController(this);
        mediaView = new MediaView();
        albumArt = new ImageView();
        timeLabel = new Label();
    }

    public void initialize(){

    }

    public void pausedasong(MouseEvent e)
    {
        System.out.println("pausedasong");
        mediaPlayer.pause();
        System.out.println(formatTime(currTime, maxTime));
    }

    public void backdasong(MouseEvent e)
    {
        System.out.println("backdasong");
        mediaPlayer.seek(mediaPlayer.getStartTime());
        setCurrentIndex((model.getSongIndex()-1) % model.getSongList().size());
        // mediaPlayer.stop();
        //songProgression.setProgress(0);
    }

    public void forwarddasong(MouseEvent e)
    {
        System.out.println("forwarddasong");
        mediaPlayer.seek(mediaPlayer.getTotalDuration());
        //setCurrentIndex((model.getSongIndex()+1) % model.getSongList().size());
        // mediaPlayer.stop();
        //songProgression.setProgress(1);
    }

    public void For10(MouseEvent re)
    {
        System.out.println("For10");
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(10.0)));
        System.out.println(formatTime(currTime, maxTime));
    }

    public void Back10(MouseEvent e)
    {
        System.out.println("Back10");
        mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(Duration.seconds(10.0)));
        System.out.println(formatTime(currTime, maxTime));
    }

    private static String formatTime(Duration elapsed, Duration duration)
    {
        int intElapsed = (int) floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);

        if (elapsedHours > 0)
            intElapsed -= elapsedHours * 60 * 60;

        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO))
        {

            int intDuration = (int) floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);

            if (durationHours > 0)
                intDuration -= durationHours * 60 * 60;

            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;

            if (durationHours > 0)
                return format("%d:%02d:%02d/%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds, durationHours, durationMinutes, durationSeconds);

            else
                return format("%02d:%02d/%02d:%02d", elapsedMinutes, elapsedSeconds, durationMinutes, durationSeconds);
        }

        else
        {
            if (elapsedHours > 0)
                return format("%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);

            else
                return format("%02d:%02d", elapsedMinutes, elapsedSeconds);
        }
    }

    protected void updateValues()
    {
        int secondsProperty = 0;
        if (timeLabel != null)
        {
            runLater(() -> {
                currTime = mediaPlayer.getCurrentTime();
                timeLabel.setText(formatTime(currTime, maxTime));
            });
        }
    }

    public void repeat(MouseEvent e){
        if (!replay) {
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.seek(Duration.ZERO);
                }
            });
        } else {
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.stop();
                    setCurrentIndex((model.getSongIndex()+1) % model.getSongList().size());
                }
            });
        }
        mediaPlayer.play();
    }

    public void playdasong (MouseEvent e)
    {
        System.out.println("playdasong");
        if (mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            mediaPlayer.play();
        }
    }

    public void shuffle(MouseEvent e){
        Collections.shuffle(model.getSongList());
        setCurrentIndex(0);
    }

   public void setSongList(ArrayList<Song> songs) {
       model.setSongList(songs);
   }

   public void setCurrentIndex(int index) {
       System.out.println("MediaPlayerController.setCurrentIndex()");
       model.setSongIndex(index);
       traversePlaylist();
   }


    public void traversePlaylist(){
            replay = false;
            try {
                media = new Media(new File(model.getSongList().get(model.getSongIndex()).getSongPath()).toURI().toString());
                System.out.println("Success 1");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                albumArt.setImage(new Image(new File(model.getSongList().get(model.getSongIndex()).getImgPath()).toURI().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            titleLabel.setText(model.getSongList().get(model.getSongIndex()).getName());
            albumNamelabel.setText(model.getSongList().get(model.getSongIndex()).getAlbum());
            model.incrementTimesPlayed();

            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.setAutoPlay(false);
            mediaPlayer.currentTimeProperty().addListener((Observable ov) -> {
                updateValues();
            });

            mediaPlayer.setOnReady(() -> {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
                /*
                duration = mediaPlayer.getMedia().getDuration();
                updateValues();*/
            });


            minTime = mediaPlayer.getStartTime();
            maxTime = mediaPlayer.getTotalDuration();
            currTime = mediaPlayer.getCurrentTime();
            /*
            mediaPlayer.seek(Duration.ZERO);
            mediaPlayer.play();
            */

           mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    System.out.println("setOnEndOfMedia");
                    mediaPlayer.stop();
                   /* mediaPlayer.seek(Duration.ZERO);
                    mediaPlayer.play(); // makes song actually play but each media player overlaps*/
                    setCurrentIndex((model.getSongIndex()+1) % model.getSongList().size());
                }
            });

        }

    }
