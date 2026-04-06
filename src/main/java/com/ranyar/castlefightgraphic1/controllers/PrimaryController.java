package com.ranyar.castlefightgraphic1.controllers;

import com.ranyar.castlefightgraphic1.App;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class PrimaryController {

    @FXML
    private Button btnJouer;
    
    @FXML 
    protected void entrer(ActionEvent a) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/com/ranyar/castlefightgraphic1/secondary.fxml"));
        Stage stage = (Stage) ((Node) a.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
