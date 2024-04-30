package org.example;

import io.javalin.http.Context;

public class Main {
    public static void main(String[] args) {
        /*Javalin app = Javalin.create().start(42069);


        app.get("/test", test::getAllProducts);
        app.get("/test/{special}", test::getSpecialProduct);*/

        String update = "1;name;durstloescher;ID;1";
        Communication c = new Communication();
        c.updateObject(update);

    }
}
