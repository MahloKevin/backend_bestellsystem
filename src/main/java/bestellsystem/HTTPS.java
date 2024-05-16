/*
package bestellsystem;

import io.javalin.Javalin;
import io.javalin.community.ssl.SslPlugin;

public class HTTPS {
    /*public static void main(String[] args) {
        // Updated the sslPlugin configuration to include a password for the encrypted key.
        SslPlugin sslPlugin = new SslPlugin(ssl -> {
            // Add the password parameter after specifying the paths for cert and key.
            // Replace 'your_password_here' with the actual password for your key file.
            ssl.pemFromPath("configs/cert.pem", "configs/key.pem", "kevin");
            ssl.securePort = 443;
            ssl.insecurePort = 80;
            ssl.redirect = true;
        });

        Javalin app = Javalin.create(config -> {
            config.registerPlugin(sslPlugin);
        }).start("0.0.0.0", 443);

        app.get("/test/{parameter}", Service::countLines);
    }*/

