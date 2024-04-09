
public class MyStack {

    private final static int STACKMAX = 3;
    private static String[] stack = new String[STACKMAX];
    private static int top = 0;

    static void push(String val) throws IndexOutOfBoundsException {
        stack[top] = val;
        top++;
    }

    // Your code below
    
    static boolean fillStack(String... vals) {
        for (String val : vals) {
                try {
                    push(val); 
                } catch (IndexOutOfBoundsException e) {
                    return false;
                }  
            }
        return true;
    }
    // Your code above this

    public static void main(String args[]){

        System.out.println(fillStack("no"));
        System.out.println(fillStack("whynot"));
        System.out.println(fillStack("Because"));
        System.out.println(fillStack("too much"));
        System.out.println(fillStack("too much"));
    }
}
