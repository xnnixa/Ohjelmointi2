import java.util.HashMap;

class Car
{
    private String make;
    private String model;
    public Car(String imake, String imodel)
    {
        make = imake;
        model = imodel;
    }
    public String getMake()
    {
        return make;
    }
    public String getModel()
    {
        return model;
    }
    
    @Override
    public String toString() {
        return make + " " + model;
    }

}


public class CarHashMap {
    
    public static void main(String args[]){

        HashMap<String, Car> hmap = new HashMap<String, Car>();

        hmap.put("ABC-131", new Car("Toyota", "Corolla"));
        hmap.put("UFO-999", new Car("Volkswagen", "Beetle"));
        hmap.put("OY-1", new Car("Volvo", "S90"));

        System.out.println(hmap.toString());
    }
}
