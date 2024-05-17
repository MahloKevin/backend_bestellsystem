package bestellsystem;

import io.javalin.Javalin;
import io.javalin.community.ssl.SslPlugin;

public class entwurf_main {
    /*public static void main(String[] args) {
        SslPlugin sslPlugin = new SslPlugin(conf -> {
            conf.pemFromPath("configs/cert.pem", "configs/key.pem", "kevin");
        });

        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.registerPlugin(sslPlugin);
        }).start("0.0.0.0", 69420);

        app.get("/", ctx -> ctx.result("Hello, World!"));
        app.get("/countLines/{parameter}", Service::countLines);
        app.get("/updateObject/{parameter}", Service::updateObject);
        app.get("/deleteObject/{parameter}", Service::deleteObject);
        app.get("/login/{parameter}", Service::login);
    }*/
}
