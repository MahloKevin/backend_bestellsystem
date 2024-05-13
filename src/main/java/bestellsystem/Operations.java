package bestellsystem;

public class Operations {

    public int countSymbols(String text, String symbol) {
        int count = 0;
        char[] everything = text.toCharArray();

        for (int i = 0; i <= everything.length - 1; i = i + 1) {
            if (String.valueOf(everything[i]).equals(symbol)) {
                count = count + 1;
            }
        }
        return count;
    }

    /*public String giveDatabase(int i) {

        String result;

        if (i == 1) {
            result = "products";
            return result;
        } else if (i == 2) {
            result = "users";
            return result;
        } else if (i == 3) {
            result = "groups";
            return result;
        } else if (i == 4) {
            result = "orders";
            return result;
        } else if (i == 5) {
            result = "sessions";
            return result;
        } else if (i == 6) {
            result = "menus";
            return result;
        } else if (i == 7) {
            result = "groups_users";
            return result;
        } else if (i == 8) {
            result = "orders_products";
            return result;
        } else if (i == 9) {
            result = "menu_products";
            return result;
        } else {
            result = "error";
            return result;
        }

    }
     */

    public String addQuotation(String text) {

        try {
            double number = Double.parseDouble(text);
            return text;
        } catch (NumberFormatException e) {

            text = "\"" + text + "\"";
            return text;
        }
    }

}


