package org.example;

import io.javalin.http.Context;

public final class test {

    private test(){}

    static String[] products = {"food1", "food2", "food3", "food4"};

    public static void getAllProducts(Context context){
        context.json(products);

    }

    public static void getSpecialProduct(Context context) {
        for(String special: products){
            if(special.contains(context.pathParam("special"))){
                context.result(special);
                return;
            }
        }
        context.result("Kein Produkt gefunden, Brudi :(");
    }
}
