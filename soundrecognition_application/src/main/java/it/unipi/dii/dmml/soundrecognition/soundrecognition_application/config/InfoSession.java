package it.unipi.dii.dmml.soundrecognition.soundrecognition_application.config;

import it.unipi.dii.dmml.soundrecognition.soundrecognition_application.entities.User;

public class InfoSession {
    private static User loggedUser = null;
    private static boolean adminLogged = false;

    public static boolean setSession(User user_to_log){
        if(loggedUser != null || adminLogged)
            return false;
        loggedUser = user_to_log;
        return true;
    }

    public static User getSession(){
        return loggedUser;
    }

    public static boolean setAdmin(){
        if(loggedUser != null || adminLogged)
            return false;
        return adminLogged = true;
    }

    public static boolean unsetSession() {

        if (loggedUser == null)
            return false;

        loggedUser = null;
        return true;
    }

    public static boolean unsetAdmin() {

        if (!adminLogged)
            return false;

        adminLogged = false;
        return true;
    }

}
