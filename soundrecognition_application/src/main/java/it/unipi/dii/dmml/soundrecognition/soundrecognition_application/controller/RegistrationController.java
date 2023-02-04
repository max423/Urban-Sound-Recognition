package it.unipi.dii.dmml.soundrecognition.soundrecognition_application.controller;

import it.unipi.dii.dmml.soundrecognition.soundrecognition_application.config.InfoSession;
import it.unipi.dii.dmml.soundrecognition.soundrecognition_application.entities.User;
import it.unipi.dii.dmml.soundrecognition.soundrecognition_application.utilities.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class RegistrationController {
    public Label errorMessage;
    @FXML private TextField full_name;
    @FXML private TextField username;
    @FXML private PasswordField password_confirm;
    @FXML private PasswordField password;
    @FXML private TextField email;
    @FXML private TextField email_confirm;
    private ActionEvent event;
    private final String regex = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    @FXML
    protected void submit(ActionEvent ae)  {
        this.event = ae;

        //Controls over the submission fields
        Pattern pattern = Pattern.compile(regex);

        if(!pattern.matcher(email.getText()).matches()){
            errorMessage.setText("Wrong email");
            return;
        }
        if(!email.getText().equals(email_confirm.getText())){
            errorMessage.setText("Email and confirmation don't match");
            return;
        }
        if(password.getText().length() < 8){
            errorMessage.setText("Password must be at least 8 characters");
            return;
        }
        if(!password.getText().equals(password_confirm.getText())){
            errorMessage.setText("Password and confirmation don't match");
            return;
        }
        if(full_name.getText().length() == 0){
            errorMessage.setText("Empty field");
            return;
        }
        //Add new user in DB
        User user = new User(full_name.getText(), email.getText(), password.getText(), username.getText());
        if(!user.addUser()){
            errorMessage.setText("Email or username already in use");
            return;
        }
        System.out.println("User " + user.getFullName() + " added.");

        //Login
        InfoSession.setSession(user);
        Utils.changeScene("main.fxml", ae);

    }

    public void goback(ActionEvent actionEvent) {
        Utils.goBackToMain(actionEvent);
    }
}
