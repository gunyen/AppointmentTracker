package com.nguyen.c195.controller;/*
C195
Created by: John Nguyen
Creation Date: 6/6/2023
Creation Time: 1:03 PM
*/

import com.nguyen.c195.Scheduler;
import com.nguyen.c195.helper.Translate;
import com.nguyen.c195.helper.VerifyUser;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.util.Locale.ENGLISH;
import static java.util.Locale.FRENCH;

public class LoginController implements Initializable {

    public TextField userNameText;
    public PasswordField passwordText;
    public Button submitLogin;
    public Label userZone;
    public Button translateEng;
    public Button translateFr;
    public Label userNameLabel;
    public Label passwordLabel;
    public Label loginLabel;
    public Label userZoneLabel;
    public Label verifyLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale.setDefault(ENGLISH);
        userZone.setText(ZoneId.systemDefault().toString());
    }

    public void onSubmit(ActionEvent actionEvent) {
        try{
        if (VerifyUser.getUserPassword(userNameText.getText(),passwordText.getText())){
            Scheduler.navigate(actionEvent, "Appointments.fxml");
        } else {
            ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
            verifyLabel.setText(rb.getString("LoginError"));
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void transEng(ActionEvent actionEvent) {
        Locale.setDefault(ENGLISH);
        Translate.trans(loginLabel,userNameLabel,passwordLabel,userZoneLabel,translateFr,translateEng,submitLogin,verifyLabel);
    }

    public void transFr(ActionEvent actionEvent) {
        Locale.setDefault(FRENCH);
        Translate.trans(loginLabel,userNameLabel,passwordLabel,userZoneLabel,translateFr,translateEng,submitLogin,verifyLabel);
    }
}
