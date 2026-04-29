package com.ranyar.castlefightgraphic1;

import java.sql.*;

public class Databasemanager {

    private static final String URL = "jdbc:mysql://localhost:3306/castlefight";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // ton mdp XAMPP

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    public static void sauvegarderCombat(String nomGagnant, String nomPerdant, int vieRestante) {
        String sql = "INSERT INTO resultats_combats (nom_gagnant, nom_perdant, vie_restante_gagnant) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomGagnant);
            stmt.setString(2, nomPerdant);
            stmt.setInt(3, vieRestante);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}