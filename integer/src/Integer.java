public class Integer {

    static class IntegerValue {
        private int num;

        public IntegerValue(int inum) {
            this.num = inum;
        }

        public int getInt() {
            return num;
        }

        public IntegerValue add(IntegerValue other) {

        }

    }

    static class IntegerPoint {
        private IntegerValue x;
        private IntegerValue y;

        public IntegerPoint(IntegerValue x, IntegerValue y) {
            this.x = x;
            this.y = y;
        }

        public IntegerValue getX() {
            return x;
        }

        public IntegerValue getY() {
            return y;
        }

        public IntegerPoint add(IntegerValue other) {
            
        }
    }

    public static void main(String args[]) {

        IntegerValue intv1 = new IntegerValue(1);
        IntegerValue intv2 = new IntegerValue(2);
        IntegerValue intv3 = new IntegerValue(3);
        IntegerValue intv4 = new IntegerValue(4);
        IntegerPoint intp = new IntegerPoint(intv1, intv2);
        IntegerPoint intp2 = new IntegerPoint(intv3, intv4);

        intp = intp.add(intp2);

        System.out.println(intp.x.num + " " + intp.y.num);
    }
}
