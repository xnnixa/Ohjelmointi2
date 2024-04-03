public class DoubleException {

    static double convertToDouble(String str) {
        
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    public static void main (String args[]){

        String s = "3.3";
        String e = "e";
        double d;

        d = convertToDouble(s);
        System.out.println(d);
    
        d = convertToDouble(e);
        System.out.println(d);
    }
}