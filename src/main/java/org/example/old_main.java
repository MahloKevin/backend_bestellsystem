package org.example;

import java.sql.*;
import io.javalin.Javalin;

public class old_main {
    public static void main(String[] args) {
        // Verbindungsinformationen zur Datenbank
        String url = "jdbc:mysql://192.168.10.45:3306/bestellsystem";
        String user = "bestellsystem";
        String password = "";

        // SQL-Abfrage, um die Anzahl der Zeilen in einer Tabelle zu erhalten
        String query = "SELECT COUNT(*) AS zeilen FROM bestellung WHERE ID = 1";

        try {
            // Verbindung zur Datenbank herstellen
            Connection connection = DriverManager.getConnection(url, user, password);

            // Statement erstellen
            Statement statement = connection.createStatement();

            // Abfrage ausführen
            ResultSet resultSet = statement.executeQuery(query);

            // Ergebnis verarbeiten
            if (resultSet.next()) {
                int rowCount = resultSet.getInt("zeilen");
                System.out.println("Es sind " + rowCount + " Zeilen in der Datenbank die auf deine Suche passen");
            }

            // Verbindung schließen
            resultSet.close();
            statement.close();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
