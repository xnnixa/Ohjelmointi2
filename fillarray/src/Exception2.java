public class Exception2 {
   
    public enum ReturnCode {
        SUCCESS, NAN, OUTOFBOUNDS
    };

    private static final int ARRMAX = 10;
    private static String[] store = new String[ARRMAX];

    public static ReturnCode insert(String pos, String val) {
        
        // Your code below this
        int i;
        
        i = Integer.parseInt(pos);
        store[i] = val;
        
        return ReturnCode.SUCCESS;
        
        // Your code above this
    }

    public static void main(String args[]){

        String val = "Milk";
        String pos = "2";
        String fpos = "12";   
        String ffpos = "asd";
        ReturnCode code;

        code = insert(pos, val);
        System.out.println(code);

        code = insert(fpos, val);
        System.out.println(code);

        code = insert(ffpos, val);
        System.out.println(code);
    }
}
