import java.util.Vector;

public class FruitsVector {
    
    public static void main(String args[]) {
        Vector<Fruit> fruits = new Vector<Fruit>(); 
        fruits.add(new Orange(100)); fruits.add(new Apple(150)); fruits.add(new Orange(200));
        for (Fruit fruit : fruits){
            System.out.println(fruit);
        }
    }
}
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