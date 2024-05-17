package bestellsystem;

import io.javalin.Javalin;
import io.javalin.community.ssl.SslPlugin;

public class Main {
    public static void main(String[] args) {
        SslPlugin sslPlugin = new SslPlugin(conf -> {
            conf.pemFromPath("configs/cert.pem", "configs/key.pem", "kevin");
        });

        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.registerPlugin(sslPlugin);
        }).start(7000);

        app.get("/", ctx -> ctx.result("Hello, World!"));
        app.get("countLines/{parameter}", Service::countLines);
    }
}
