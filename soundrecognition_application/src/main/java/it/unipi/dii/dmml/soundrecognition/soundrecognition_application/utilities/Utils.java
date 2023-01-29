package it.unipi.dii.dmml.soundrecognition.soundrecognition_application.utilities;

import it.unipi.dii.dmml.soundrecognition.soundrecognition_application.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Utils {
    static public void changeScene(String fxmlFile, ActionEvent event){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 900);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static public void goBackToMain(ActionEvent event){

        changeScene("hello-view.fxml",event);
        //changeScene(last,event);
    }

    /**
     * This function is used to read the config.xml file
     * @return  ConfigurationParameters instance
     */
    /*public static Properties readConfigurationParameters(){
        try{
            FileInputStream fis = new FileInputStream(Utils.class.getResource("src/main/resources/config/config.properties").toURI().getPath());
            Properties prop = new Properties();
            prop.load(fis);
            return prop;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }*/
}
