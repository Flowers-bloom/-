/**
 * 简单工厂模式
 */
public class Vehicle {}
public class Car extends Vehicle {}
public class Truck extends Vehicle {}

public class VehicleFactory
{
    public Vehicle createVehicle(String type)
    {
        if (type == "car")
            return new Car();
        else if (type == "truck")
            return new Truck();
    }
}