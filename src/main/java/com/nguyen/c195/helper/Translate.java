package com.nguyen.c195.helper;/*
C195
Created by: John Nguyen
Creation Date: 6/7/2023
Creation Time: 4:01 PM
*/

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.Locale.ENGLISH;
import static java.util.Locale.FRENCH;

public abstract class Translate {

    public static void trans(Label login, Label username, Label password, Label userZone, Button transFr, Button transEng, Button submit, Label verify){
        ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
        if(Locale.getDefault().equals(FRENCH)){
            login.setText(rb.getString("Login"));
            username.setText(rb.getString("Username"));
            password.setText(rb.getString("Password"));
            userZone.setText(rb.getString("Time") + " " + rb.getString("Zone") + ":");
            transFr.setText(rb.getString("French"));
            transEng.setText(rb.getString("English"));
            submit.setText(rb.getString("Submit"));
            if(!verify.getText().equals("")){
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
            if(!verify.getText().equals("")){
                verify.setText(rb.getString("LoginError"));
            }
        }
    }
}
