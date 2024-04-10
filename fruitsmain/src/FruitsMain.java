import java.util.ArrayList;
import java.util.List;

abstract class Fruit
{
    protected int weight;
    public Fruit(int iweight)
    {
        weight = iweight;
    }
    public int getWeight()
    {
        return weight;
    }
    abstract public String toString();
}

class Apple extends Fruit
{
    public Apple(int iweigth)
    {
        super(iweigth);
    }
    public String toString()
    {
        return "Apple";
    }
}

class Orange extends Fruit
{
    public Orange(int iweigth)
    {
        super(iweigth);
    }
    public String toString()
    {
        return "Orange";
    }
}

public class FruitsMain {
    
    public static void main(String args[]){

        List<Fruit> fruits = new ArrayList<>();

        Fruit apple = new Apple(0);
        Fruit orange = new Orange(0);
        Fruit apple2 = new Apple(0);

        fruits.add(apple);
        fruits.add(orange);
        fruits.add(apple2);

        for(Fruit f : fruits) System.out.println(f.toString()); // Don't touch
    }
}
