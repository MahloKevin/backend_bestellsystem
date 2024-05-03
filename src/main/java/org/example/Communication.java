package org.example;

import io.javalin.http.Context;

import java.sql.*;


public class Communication {

    final private String url = "jdbc:mysql://192.168.10.45:3306/bestellsystem";
    final private String user = "bestellsystem";
    final private String password = "";
    final private Operations o = new Operations();

    //1 = products
    //2 = user
    //3 = group
    //4 = order
    //5 = sessíon
    //6 = menu
    //7 = user_group
    //8 = order_products
    //9 = menu_products

    public void countLines(Context context) {
        String parameter = String.valueOf(context);
        String query = "";

        // Es kann nur 1 oder 3 Parameter geben
        // 1 Parameter = Table
        // 2 Parameter = Spalte
        // 3 Parameter = Wert der in Spalte gesucht wird
        if (o.countSymbols(parameter, ";") == 2) {

            String[] parts = parameter.split(";");

            query = "SELECT COUNT(*) AS row FROM " + o.giveDatabase(Integer.parseInt(parts[0])) + " WHERE " + parts[1] + " = " + parts[2] + ";";

        } else if (o.countSymbols(parameter, ";") == 1) {

            parameter = o.giveDatabase(Integer.parseInt(parameter));
            query = "SELECT COUNT(*) AS row FROM " + parameter + ";";

        } else {

            context.result("Parameterangabe Fehlerhaft");

        }

        try {
            //Verbindung zu SQL Server
            Connection connection = DriverManager.getConnection(url, user, password);

            //Statement erstellen
            Statement statement = connection.createStatement();

            //Abrage absenden
            ResultSet resultSet = statement.executeQuery(query);

            //Ergebnis verarbeiten
            if (resultSet.next()) {
                int rowCount = resultSet.getInt("row");
                context.result(String.valueOf(rowCount));
            }

            //Verbindung beenden
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            context.result("Kein Ergebniss gefunden :(");
        }
    }


    public void updateObject(Context context) {
        String parameter = String.valueOf(context);
        String query;

        if (o.countSymbols(parameter, ";") == 4) {

            String[] parts = parameter.split(";");
            query = "UPDATE " + o.giveDatabase(Integer.parseInt(parts[0])) + " SET " + parts[1] + " = " + o.addQuotation(parts[2]) + " WHERE " + parts[3] + " = " + o.addQuotation(parts[4]) + ";";

        } else if (o.countSymbols(parameter, ";") == 2) {

            String[] parts = parameter.split(";");
            query = "UPDATE " + o.giveDatabase(Integer.parseInt(parts[0])) + " SET " + parts[1] + " = " + o.addQuotation(parts[2]) + ";";

        } else {

            context.result("Parameter sind fehlerhaft :(");
            return;

        }

        try {
            //Verbindung zu SQL Server
            Connection connection = DriverManager.getConnection(url, user, password);

            //Statement erstellen
            Statement statement = connection.createStatement();

            //Abrage absenden
            int rowCount = statement.executeUpdate(query);

            //Verbindung beenden
            statement.close();
            connection.close();


            context.result(String.valueOf(rowCount));

        } catch (SQLException e) {
            e.printStackTrace();
            context.result("Kein Ergebniss gefunden :(");
        }

    }

    public void deleteObject(Context context) {
        String parameter = String.valueOf(context);
        String query;

        if (o.countSymbols(parameter, ";") == 2) {

            String[] parts = parameter.split(";");
            query = "DELETE FROM " + o.giveDatabase(Integer.parseInt(parts[0])) + " WHERE " + parts[1] + " = " + o.addQuotation(parts[2]);


        } else {
            context.result("Parameterangabe falsch");
            return;
        }

        try {
            //Verbindung zu SQL Server
            Connection connection = DriverManager.getConnection(url, user, password);

            //Statement erstellen
            Statement statement = connection.createStatement();

            //Abrage absenden
            int rowCount = statement.executeUpdate(query);

            //Verbindung beenden
            statement.close();
            connection.close();


            context.result(String.valueOf(rowCount));

        } catch (SQLException e) {
            e.printStackTrace();
            context.result("Kein Ergebniss gefunden :(");
        }

    }


}
