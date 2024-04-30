package org.example;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(42069);
        //app.get("/", ctx -> ctx.result("Hello Javalin"));

        app.get("/test", test::getAllProducts);


        app.get("/test/{special}", test::getSpecialProduct);

    }
}
