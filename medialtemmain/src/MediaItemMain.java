
interface MediaItem {
    public String getType();
    public void printInfo();
}


class Book implements MediaItem{
    private String title;
    private int pagecount;

    public Book(String title, int pagecount){
        this.title = title;
        this.pagecount = pagecount; 
    }

    public String getType(){
        return "Book";
    }
    public void printInfo(){
        System.out.println(title + ": " + pagecount);
    }
}

class Disc implements MediaItem{
    private String title;
    private int length;

    public Disc(String title, int length){
        this.title = title;
        this.length = length;
    }

    public String getType(){
        return "Disc";
    }
    public void printInfo(){
        System.out.println(title + ": " + length);
    }
}

// DON'T TOUCH
public class MediaItemMain {// DON'T TOUCH
// DON'T TOUCH
    public static void main(String args[]) {// DON'T TOUCH
// DON'T TOUCH
    (new Disc("Trainspotting", 95)).printInfo();// DON'T TOUCH
// DON'T TOUCH
    (new Book("Introduction to algorithms", 1312)).printInfo();// DON'T TOUCH
// DON'T TOUCH
    System.out.println((new Book("Introduction to algorithms", 1312)).getType());// DON'T TOUCH
// DON'T TOUCH
    System.out.println((new Disc("Trainspotting", 95)).getType());// DON'T TOUCH
    }// DON'T TOUCH
}// DON'T TOUCH
