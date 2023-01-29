package it.unipi.dii.dmml.soundrecognition.soundrecognition_application.controller;

import it.unipi.dii.dmml.soundrecognition.soundrecognition_application.config.InfoSession;
import it.unipi.dii.dmml.soundrecognition.soundrecognition_application.utilities.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;
    private ActionEvent event;

    @FXML
    protected void onButtonForm(ActionEvent event) throws IOException{

        this.event = event;
        /*if(InfoSession.getSession() != null)
            Utils.changeScene("answers.fxml",event);
        else
            Utils.changeScene("form.fxml",event);*/
        Utils.changeScene("registration.fxml",event);
    }
    @FXML
    protected void onButtonLogin(ActionEvent event) throws IOException {

        this.event = event;

        /*if(InfoSession.getSession() != null){

            if(InfoSession.getSession().getClusterId() >= 0)
                Utils.changeScene("chat.fxml",event);
            else
                Utils.changeScene("waiting.fxml",event);
        }
        else
         */   Utils.changeScene("login.fxml",event);

    }
}
