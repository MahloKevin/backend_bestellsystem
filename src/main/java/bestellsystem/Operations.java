package bestellsystem;

public class Operations {

    public int countSymbols(String text, String zeichen) {
        int count = 0;
        char[] everything = text.toCharArray();

        for (int i = 0; i <= everything.length - 1; i = i + 1) {
            if (String.valueOf(everything[i]).equals(zeichen)) {
                count = count + 1;
            }
        }
        return count;
    }

    public String addQuotation(String text) {

        try {
            double number = Double.parseDouble(text);
            return text;
        } catch (NumberFormatException e) {

            text = "\"" + text + "\"";
            return text;
        }
    }

    public String getLeftPart(String text, String zeichen){
        int index = text.indexOf(zeichen);
        if(index != -1){
            return text.substring(0,index);
        }

        return "";
    }

    public String getRightPart(String text, String zeichen){
        int index = text.indexOf(zeichen);
        if(index != -1){
            return text.substring(index + 1);
        }
        return "";
    }

    public static String firstLetterUpperCase(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

}


