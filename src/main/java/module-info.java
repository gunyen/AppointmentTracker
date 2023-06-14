module com.nguyen.c195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.nguyen.c195 to javafx.fxml;
    exports com.nguyen.c195;
    exports com.nguyen.c195.controller;
    opens com.nguyen.c195.controller to javafx.fxml;
    exports com.nguyen.c195.model;
    opens com.nguyen.c195.model to javafx.fxml;
    opens com.nguyen.c195.DAO to javafx.fxml;
    exports com.nguyen.c195.DAO;
}