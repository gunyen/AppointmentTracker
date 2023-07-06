package com.nguyen.c195.helper;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.Locale.ENGLISH;
import static java.util.Locale.FRENCH;

/**
 * Holds methods for translating languages
 */
public abstract class Translate {

    /**
     * <p>Translates text within login.fxml to a designated language of either French or English</p>
     *
     * @param login the login to set
     * @param username the username to set
     * @param password the password to set
     * @param userZone the userZone to set
     * @param transFr the transFr to set
     * @param transEng the transEng to set
     * @param submit the submit to set
     * @param verify the verify to set
     */
    public static void trans(Label login, Label username, Label password, Label userZone, Button transFr, Button transEng, Button submit, Label verify) {
        ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
        if (Locale.getDefault().equals(FRENCH)) {
            login.setText(rb.getString("Login"));
            username.setText(rb.getString("Username"));
            password.setText(rb.getString("Password"));
            userZone.setText(rb.getString("Time") + " " + rb.getString("Zone") + ":");
            transFr.setText(rb.getString("French"));
            transEng.setText(rb.getString("English"));
            submit.setText(rb.getString("Submit"));
            if (!verify.getText().equals("")) {
                verify.setText(rb.getString("LoginError"));
            }
        } else if (Locale.getDefault().equals(ENGLISH)) {
            login.setText(rb.getString("Connexion"));
            username.setText(rb.getString("Nom d'utilisateur"));
            password.setText(rb.getString("Mot de passe"));
            userZone.setText(rb.getString("Temps") + " " + rb.getString("Zone") + ":");
            transFr.setText(rb.getString("Français"));
            transEng.setText(rb.getString("Anglais"));
            submit.setText(rb.getString("Soumettre"));
            if (!verify.getText().equals("")) {
                verify.setText(rb.getString("LoginError"));
            }
        }
    }
}
