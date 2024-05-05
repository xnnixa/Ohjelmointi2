public class GenericWrapper {

    public static void main(String args[]) {

        Wrapper<String> w = new Wrapper<String>("Hello Mary!");
        System.out.println(w.getVal());
        w.setVal("Hey Joe!");
        System.out.println(w.getVal());

        Wrapper<Integer> v = new Wrapper<Integer>(99);
        System.out.println(v.getVal());
        v.setVal(999);
        System.out.println(v.getVal());
    }
}


class Wrapper<T> {
    private T val;

    public Wrapper(T val) {
        this.val = val;
    }

    public T getVal() {
        return val;
    }

    public void setVal(T val){
        this.val = val;
    }
}
