import java.util.HashMap;
import java.util.function.BiConsumer;

class Car {
    private String make;
    private String model;

    public Car(String imake, String imodel) {
        make = imake;
        model = imodel;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

}

public class CarLambdaTest {

    public static void main(String args[]) {

        HashMap<String, Car> hmap = new HashMap<String, Car>();

        
        hmap.put("OY-1", new Car("Volvo", "S90"));
        hmap.put("ABC-131", new Car("Toyota", "Corolla"));
        hmap.put("UFO-999", new Car("Volkswagen", "Beetle"));

        hmap.forEach((key, car) -> {
            System.out.println(car.getMake() + " " + car.getModel());
        });
    }
}
