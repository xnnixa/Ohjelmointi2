class Fruit {
    public void printMe() {
        System.out.println("Fruit");
    }
}

class Apple extends Fruit{
    public void printMe() {
        System.out.println("Apple");
    }
}
    
class Orange extends Fruit {
    public void printMe() {
        System.out.println("Orange");
    }
}

    
    // DON'T TOUCH MAIN FUNCTION
    public class FruitMain {
        // DON'T TOUCH MAIN FUNCTION
            public static void main(String args[]) {
        // DON'T TOUCH MAIN FUNCTION
                Fruit f = new Fruit();// DON'T TOUCH MAIN FUNCTION
                f.printMe();
        // DON'T TOUCH MAIN FUNCTION
                Fruit a = new Apple();
                a.printMe();// DON'T TOUCH MAIN FUNCTION
        // DON'T TOUCH MAIN FUNCTION
                Fruit o = new Orange();// DON'T TOUCH MAIN FUNCTION
                o.printMe();
            }// DON'T TOUCH MAIN FUNCTION
        }// DON'T TOUCH MAIN FUNCTION
        // DON'T TOUCH MAIN FUNCTION

    
