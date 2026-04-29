package com.ranyar.castlefightgraphic1.controllers;

import com.ranyar.castlefightgraphic1.Databasemanager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class HistoryController {

    @FXML private TableView<ObservableList<String>> tableHistorique;
    @FXML private TableColumn<ObservableList<String>, String> colGagnant;
    @FXML private TableColumn<ObservableList<String>, String> colPerdant;
    @FXML private TableColumn<ObservableList<String>, String> colVie;
    @FXML private TableColumn<ObservableList<String>, String> colDate;
    @FXML private Button btnRetour;

    @FXML
    public void initialize() {
        // Lier chaque colonne à l'index de la liste
        colGagnant.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().get(0)));
        colPerdant.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().get(1)));
        colVie.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().get(2)));
        colDate.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(data.getValue().get(3)));

        chargerHistorique();

        btnRetour.setOnAction(e -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource(
                    "/com/ranyar/castlefightgraphic1/primary.fxml"));
                Stage stage = (Stage) btnRetour.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void chargerHistorique() {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        String sql = "SELECT nom_gagnant, nom_perdant, vie_restante_gagnant, date_combat " +
                     "FROM resultats_combats ORDER BY date_combat DESC";

        try (Connection conn = Databasemanager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(rs.getString("nom_gagnant"));
                row.add(rs.getString("nom_perdant"));
                row.add(String.valueOf(rs.getInt("vie_restante_gagnant")));
                row.add(rs.getTimestamp("date_combat").toString());
                data.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableHistorique.setItems(data);
    }
}