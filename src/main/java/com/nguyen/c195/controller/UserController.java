package com.nguyen.c195.controller;

import com.nguyen.c195.DAO.UserDaoImpl;
import com.nguyen.c195.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserController implements Initializable {
    @FXML
    private TableView<User> UserTable;
    @FXML
    private TableColumn<?, ?> ID;
    @FXML
    private TableColumn<?, ?> UserName;
    @FXML
    private TableColumn<?, ?> Password;

    ObservableList<User> Users= FXCollections.observableArrayList();
    /**
     * Initializes the com.nguyen.c195.controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ID.setCellValueFactory(new PropertyValueFactory<>("userId"));
        // CustomerName.setCellValueFactory(new PropertyValueFactory<>("address"));
        UserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
//       CustomerAddress2.setCellValueFactory(new PropertyValueFactory<>("customerAddress2"));
        Password.setCellValueFactory(new PropertyValueFactory<>("password"));


        try {
            Users.addAll(UserDaoImpl.getAllUsers());


        } catch (Exception ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        UserTable.setItems(Users);
        //Using Lambda for efficient selection off a tableview

    }
}

