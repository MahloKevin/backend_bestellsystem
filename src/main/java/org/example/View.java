package org.example;

import java.sql.*;
import java.util.Scanner;

public class View {

    public static int viewer(String WebParam) {
        //Verbindung zur DB
        String url = "jdbc:mysql://192.168.10.45:3306/bestellsystem";
        String user = "bestellsystem";
        String password = "242K2005s";

        Scanner scan = new Scanner(System.in);
        String test = scan.nextLine(); //HIER MUSS ICH DANN IWIE DIE PARAMETER AUS DEM WEB FISCHEN;

        //SQL Abfrage (die im Web oder hier generiert wird????)
        String query = test;

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
                return rowCount;
            }

            // Verbindung schließen
            resultSet.close();
            statement.close();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
            int fail = -1;
            return fail;
            //WIE GEH ICH MIT FEHLERN UM???? WAS WILL ICH MACHEN


        }
    }
}
