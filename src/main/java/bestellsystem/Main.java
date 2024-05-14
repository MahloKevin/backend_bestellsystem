package bestellsystem;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.jetty.defaultHost = "0.0.0.0"; // Setze die gewünschte IP-Adresse
            config.jetty.defaultPort = 42069; // Setze den gewünschten Port
        }).start();

        app.get("/countLines/{parameter}", SQL::countLines);
        app.get("/updateObject/{parameter}", SQL::updateObject);
        app.get("/deleteObject/{parameter}", SQL::deleteObject);
        app.get("/newUser/{parameter}", SQL::newUser);
        app.get("/selectToJSON/{parameter}", SQL::selectToJSON);

        //app.get("login/{parameter}", LDAPService::login);*/

        //SQL s = new SQL();
        //s.selectToJSON("test");
    }
}
