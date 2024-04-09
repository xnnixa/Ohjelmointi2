class Product {
    private int price;

    public Product(int iprice) {
        price = iprice;
    }

    public int getPrice() {
        return price;
    }
}

class Book extends Product
{
int pagecount;
public Book(int iprice, int ipagecount)
{
    super(iprice);
    pagecount = ipagecount;
}
public int getPageCount()
{
    return pagecount;
}
}


// DON'T TOUCH THIS CLASS
public class ProductMain {
// DON'T TOUCH THIS CLASS
public static void main(String args[]){
// DON'T TOUCH THIS CLASS
    Book potter = new Book(20, 130);
// DON'T TOUCH THIS CLASS
    System.out.println(potter.getPrice() + " " + potter.getPageCount() );
}// DON'T TOUCH THIS CLASS
}// DON'T TOUCH THIS CLASS