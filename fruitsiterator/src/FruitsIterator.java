import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

abstract class Fruit {
    protected int weight;

    public Fruit(int iweight) {
        weight = iweight;
    }

    public int getWeight() {
        return weight;
    }

    abstract public String toString();
}

class Apple extends Fruit {
    public Apple(int iweigth) {
        super(iweigth);
    }

    public String toString() {
        return "Apple";
    }
}

class Orange extends Fruit {
    public Orange(int iweigth) {
        super(iweigth);
    }

    public String toString() {
        return "Orange";
    }
}

public class FruitsIterator {

    public static void main(String args[]) {
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Orange(100)); fruits.add(new Apple(150)); fruits.add(new Orange(200));
        Iterator<Fruit> it = fruits.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
        
}
