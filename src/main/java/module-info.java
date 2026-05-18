module com.ranyar.castlefightgraphic1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;      
    opens com.ranyar.castlefightgraphic1 to javafx.fxml;
    exports com.ranyar.castlefightgraphic1;
    opens com.ranyar.castlefightgraphic1.controllers to javafx.fxml;
    requires java.naming;
}