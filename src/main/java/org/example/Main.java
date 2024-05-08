package org.example;

public class Main {
    public static void main(String[] args) {
        /*Javalin app = Javalin.create().start(42069);


        app.get("/test", test::getAllProducts);
        app.get("/test/{special}", test::getSpecialProduct);*/

        Configurations c = new Configurations();
        c.SQLConfig();



    }
}
