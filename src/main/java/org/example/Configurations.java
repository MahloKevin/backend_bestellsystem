package org.example;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;

public class Configurations {

    public static void SQLConfig() {
        try {
            // Datei einlesen
            FileReader fileReader = new FileReader("configs/conf.json");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            String jsonString = stringBuilder.toString();

            // JSON analysieren
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray configurations = jsonObject.getJSONArray("configurations");

            for (int i = 0; i < configurations.length(); i++) {
                JSONObject config = configurations.getJSONObject(i);
                if (config.getString("name").equals("SQLConfig")) {
                    String ip = config.getString("ip");
                    String user = config.getString("user");
                    String password = config.getString("password");

                    System.out.println("IP: " + ip);
                    System.out.println("User: " + user);
                    System.out.println("Password: " + password);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
