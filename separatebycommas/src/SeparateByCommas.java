import java.util.Vector;

public class SeparateByCommas {

    public static void main(String args[]) {

        Vector<String> v = new Vector<String>();
        v.add("Cat");
        v.add("Dog");
        v.add("Mouse");
        printVector(v);

        Vector<Integer> i = new Vector<Integer>();
        i.add(6);
        i.add(7);
        i.add(8);
        i.add(9);
        printVector(i);
    }

    public static <T> void printVector(Vector<T> v) {

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < v.size(); i++) {
            str.append(v.get(i));
            if(i < v.size() - 1) {
                str.append(",");
            }
        }
        System.out.println(str.toString());
    }
}
