package it.unipi.dii.dmml.soundrecognition.soundrecognition_application.controller;

import it.unipi.dii.dmml.soundrecognition.soundrecognition_application.config.ParameterConfig;
import it.unipi.dii.dmml.soundrecognition.soundrecognition_application.config.InfoSession;
import it.unipi.dii.dmml.soundrecognition.soundrecognition_application.entities.User;
import it.unipi.dii.dmml.soundrecognition.soundrecognition_application.utilities.DatabaseUtils;
import it.unipi.dii.dmml.soundrecognition.soundrecognition_application.utilities.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

public class LoginController {
    public Label errorField;
    @FXML TextField username;
    @FXML PasswordField password;
    private ActionEvent event;

    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    @FXML
    private void login(ActionEvent ae){
        this.event = ae;

        String username = this.username.getText();
        String password = this.password.getText();

        //Null Check
        if(username.length() == 0)
            return;
        if(password.length() == 0)
            return;

        //Admin
        if(username.equals(ParameterConfig.getAdminUsername()) && password.equals(ParameterConfig.getAdminPassword())){
            InfoSession.setAdmin();
            Utils.changeScene("admin.fxml",ae);
            return;
        }

        //Normal User
        User logged = DatabaseUtils.getUser(username);
        if(logged != null){
            if(!password.equals(logged.getPassword())){
                errorField.setText("Wrong password");
                return;
            }
            // ok
            InfoSession.setSession(logged);

            Utils.changeScene("main.fxml",ae);

        }
        else
            errorField.setText("User not found");
    }

    public void goback(ActionEvent actionEvent) {
        Utils.goBackToMain(actionEvent);
    }


}
