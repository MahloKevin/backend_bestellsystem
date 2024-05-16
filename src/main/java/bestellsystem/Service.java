package bestellsystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.List;

public class Service {

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
                    int value = result.getInt("result");
                    return String.valueOf(value);
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

        return "sendStatement: Kein Ergebniss gefunden";
    }

    public static JSONArray convertSelectToJSON(String query) {
        JSONArray json = new JSONArray();
        try {
            Configurations config = new Configurations();
            String[] parts = String.valueOf(config.SQLConfig()).split(";");

            // Verbindung zu SQL Server
            Connection c = DriverManager.getConnection(parts[0], parts[1], parts[2]);

            // Statement vorbereiten
            Statement s = c.createStatement();

            // Abfrage senden
            //System.out.println(query);
            ResultSet result = s.executeQuery(query);
            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (result.next()) {
                JSONObject jsonObject = new JSONObject();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = result.getObject(i);
                    jsonObject.put(columnName, value);
                }
                json.put(jsonObject);
            }

            // Verbindung schließen
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Im Falle eines Fehlers wird null zurückgegeben
            return json = null;
        }
        return json;
    }

    public static String getID(String parameter) {
        Operations o = new Operations();
        String query;

        if (o.countSymbols(parameter, ";") == 2) {
            String[] parts = parameter.split(";");
            query = "SELECT ID AS result FROM " + (parts[0]) + " WHERE " + parts[1] + " = " + o.addQuotation(parts[2]) + ";";
            return sendStatement(query);

        } else {
            return "getID: Parameterangabe Falsch";
        }


    }


    public static void countLines(Context context) {
        Operations o = new Operations();
        String parameter = context.pathParam("parameter");
        String query;

        if (o.countSymbols(parameter, ";") == 2) {

            String[] parts = parameter.split(";");
            query = "SELECT COUNT(*) AS result FROM " + parts[0] + " WHERE " + parts[1] + " = " + parts[2] + ";";
            context.result(sendStatement(query));

        } else if (o.countSymbols(parameter, ";") == 0) {

            query = "SELECT COUNT(*) AS result FROM " + parameter + ";";
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

    public static String newUser(String mail, String firstName, String lastName) {
        Operations o = new Operations();
        String query;

        query = "INSERT INTO users (ID, mail, name, picture, groups_ID) VALUES (NULL, " + o.addQuotation(mail) + ", " + o.addQuotation(firstName + " " + lastName) + ", NULL, 3)";

        return sendStatement(query);

    }

    public static void selectToJSON(Context context) throws JsonProcessingException {
        Operations o = new Operations();
        String parameter = context.pathParam("parameter");
        String query;

        if (o.countSymbols(parameter, ",") == 0) {
            query = "SELECT * FROM " + parameter + ";";
            //return String.valueOf(convertSelectToJSON(query));
            String jsonString = String.valueOf(convertSelectToJSON(query));
            ObjectMapper mapper = new ObjectMapper();
            List<Object> jsonArray = mapper.readValue(jsonString, new TypeReference<List<Object>>() {
            });

            context.json(jsonArray);


        } else {
            context.result("selectTOJSON: Parameterangabe falsch");
        }
    }

    public static void login(Context context) {
        Operations o = new Operations();
        LDAP l = new LDAP();
        String[] parts = context.pathParam("parameter").split(";");

        boolean registraition = l.authenticateAndFetchUserInfo(parts[0], parts[1]);

        if (registraition == true) {
            String check = "users;mail;" + parts[0];
            if (getID(check) != "sendStatement: Kein Ergebniss gefunden") {
                context.result(parts[0] + ";" + getID(check));

            } else {

                String firstName = (o.firstLetterUpperCase(o.getLeftPart(parts[0], ".")));
                String lastName = (o.firstLetterUpperCase(o.getRightPart(o.getLeftPart(parts[0], "@"), ".")));
                newUser(parts[0], firstName, lastName);
                context.result(parts[0] + ";" + getID(check));

            }

        } else {
            context.result("Anmeldung nicht möglich");
        }


    }


}


