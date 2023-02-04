package it.unipi.dii.dmml.soundrecognition.soundrecognition_application.controller;

import it.unipi.dii.dmml.soundrecognition.soundrecognition_application.classification.Classifier;
import it.unipi.dii.dmml.soundrecognition.soundrecognition_application.utilities.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javafx.scene.input.DragEvent;

import javax.sound.sampled.*;

public class MainController {
    Classifier Classifier;

    @FXML private ImageView imageView;
    @FXML private Button Playbutton;
    @FXML private Button Execute;
    private Clip clip;
    private List<File> files;

    private File SendAudio;
    private String sketchPath = "src/main/resources/it/unipi/dii/dmml/soundrecognition/soundrecognition_application";


    public void exit(ActionEvent actionEvent) {
        Utils.changeScene("hello-view.fxml", actionEvent);
    }

    public void HandleDragOver(DragEvent event){
        if(event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void HandleDrop(DragEvent event) throws IOException, UnsupportedAudioFileException {
        files = event.getDragboard().getFiles();

        // sound image
        File file = new File("src/main/resources/it/unipi/dii/dmml/soundrecognition/soundrecognition_application/images/Sound-wave.png");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);

        // enable play and execute button
        Playbutton.setDisable(false);
        Execute.setDisable(false);

        AudioInputStream audio = AudioSystem.getAudioInputStream(files.get(0));
        AudioSystem.write(audio,AudioFileFormat.Type.WAVE, new  File(sketchPath + "/test.wav"));
    }

    public void classification(ActionEvent actionEvent) {
        Classifier cd = new Classifier();

        SendAudio = new File(sketchPath + "/test.wav");
        String audioClass = cd.getAudioClass(SendAudio);

        if (audioClass == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Service unavailable!");
            alert.showAndWait();
        }
        else {
            System.out.println(audioClass);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Audio Class: "+ audioClass);
            alert.showAndWait();

            clean();
        }
    }

    public void clean(){
        // change img
        imageView.setImage(null);

        // remove audio file
        SendAudio.delete();

        // disable play and execute button
        Playbutton.setDisable(true);
        Execute.setDisable(true);
    }


    public void play(ActionEvent actionEvent) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        // save audio and play sound
        clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(files.get(0)));
        clip.start();
    }
}
