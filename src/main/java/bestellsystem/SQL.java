package bestellsystem;

import io.javalin.http.Context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQL {

    public static String sendStatement(String query) {
        try {
            Configurations config = new Configurations();
            String[] parts = String.valueOf(config.SQLConfig()).split(";");

            //Verbindung zu SQL Server
            Connection c = DriverManager.getConnection(parts[0], parts[1], parts[2]);

            //Statement vorbereiten
            Statement s = c.createStatement();

            //Abfrage senden
            if (query.contains("SELECT") == true) {
                ResultSet result = s.executeQuery(query);
                if (result.next()) {
                    int rows = result.getInt("row");
                    return String.valueOf(rows);
                }
            } else if (query.contains("UPDATE") == true || query.contains("DELETE") == true || query.contains("INSERT") == true) {
                int result = s.executeUpdate(query);
                return String.valueOf(result);
            } else {
                return "Parameter falsch";
            }


        } catch (Exception e) {
            e.printStackTrace();
            return "Fehler: " + e;
        }

        return "Fehler: 2";
    }


    public static void countLines(Context context) {
        Operations o = new Operations();
        String parameter = context.pathParam("parameter");
        String query;

        if (o.countSymbols(parameter, ";") == 2) {

            String[] parts = parameter.split(";");
            query = "SELECT COUNT(*) AS row FROM " + parts[0] + " WHERE " + parts[1] + " = " + parts[2] + ";";
            context.result(sendStatement(query));

        } else if (o.countSymbols(parameter, ";") == 0) {

            query = "SELECT COUNT(*) AS row FROM " + parameter + ";";
            context.result(sendStatement(query));

        } else {
            context.result("countLines: Parameterangabe Fehlerhaft");
        }


    }

    public static void updateObject(Context context) {
        Operations o = new Operations();
        String parameter = context.pathParam("parameter");
        String query;

        if (o.countSymbols(parameter, ";") == 4) {

            String[] parts = parameter.split(";");
            query = "UPDATE " + parts[0] + " SET " + parts[1] + " = " + o.addQuotation(parts[2]) + " WHERE " + parts[3] + " = " + o.addQuotation(parts[4]) + ";";
            context.result(sendStatement(query));

        } else if (o.countSymbols(parameter, ";") == 2) {

            String[] parts = parameter.split(";");
            query = "UPDATE " + parts[0] + " SET " + parts[1] + " = " + o.addQuotation(parts[2]) + ";";
            context.result(sendStatement(query));

        } else {
            context.result("updateObject: Parameterangabe Fehlerhaft");
        }
    }


    public static void deleteObject(Context context) {
        Operations o = new Operations();
        String parameter = context.pathParam("parameter");
        String query;

        if (o.countSymbols(parameter, ";") == 2) {

            String[] parts = parameter.split(";");
            query = "DELETE FROM " + parts[0] + " WHERE " + parts[1] + " = " + o.addQuotation(parts[2]);
            context.result(sendStatement(query));

        } else {
            context.result("deletObject: Parameterangabe falsch");
        }


    }

    public static void newUser(Context context) {
        Operations o = new Operations();
        String parameter = context.pathParam("parameter");
        String query;

        if(o.countSymbols(parameter, ";") == 3){
            String[] parts = parameter.split(";");
            query = "INSERT INTO users (ID, name, department, groups_ID) VALUES (NULL, '"+ parts[0] + " " + parts[1] + "', '" + parts[2] + "', 3)";
            context.result(sendStatement(query));

        }else {
            context.result("newUser: Parameterangabe falsch");
        }




    }

}


