package com.ranyar.castlefightgraphic1;

import java.sql.*;
import java.nio.file.*;

public class Databasemanager {

    private static final String URL = "jdbc:mysql://109.234.166.210:3306/kuda8918_ranyacastle";
    private static final String USER = "kuda8918_ranyafight";
    private static final String PASSWORD = "Q&lzcQ6!4P[q0}$J";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    private static void initialiserBDD() {
        String sql = """
            CREATE TABLE IF NOT EXISTS resultats_combats (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom_gagnant TEXT NOT NULL,
                nom_perdant TEXT NOT NULL,
                vie_restante_gagnant INTEGER NOT NULL,
                date_combat TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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