class App {

    public static void main (String [] args) {
        
        TheInt iw1 = new TheInt();

        TheInt iw2 = new TheInt();
        
        for(int i = 0; i <= 5; i++) {
        
            iw2.dec(1);
        
            iw1.inc(TheInt.val);
        
            }
        
        iw2.dec(-10);
        System.out.println(iw2.val);
    }

}
