package bestellsystem;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.jetty.defaultHost = "0.0.0.0"; // Setze die gewünschte IP-Adresse
            config.jetty.defaultPort = 42069; // Setze den gewünschten Port
        }).start();

        app.get("/countLines/{parameter}", Service::countLines);
        app.get("/updateObject/{parameter}", Service::updateObject);
        app.get("/deleteObject/{parameter}", Service::deleteObject);
        app.get("/selectToJSON/{parameter}", Service::selectToJSON);
        app.get("/login/{parameter}", Service::login);
    }
}
