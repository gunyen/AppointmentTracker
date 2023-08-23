module com.nguyen.c195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.nguyen.capstonecrm to javafx.fxml;
    exports com.nguyen.capstonecrm;
    exports com.nguyen.capstonecrm.controller;
    opens com.nguyen.capstonecrm.controller to javafx.fxml;
    exports com.nguyen.capstonecrm.model;
    opens com.nguyen.capstonecrm.model to javafx.fxml;
    opens com.nguyen.capstonecrm.DAO to javafx.fxml;
    exports com.nguyen.capstonecrm.DAO;
}