/**
 * 抽象工厂模式
 */
public class Vehicle {
    public Component getComponent() {}; // 父类限制获取组件方法
    public void package() {}; // 父类限制组装方法
    public abstract void shape();  // 开放造型设置给子类
}
public class Car extends Vehicle {}
public class Truck extends Vehicle {}

public abstract class VehicleFactory
{
    public abstract Vehicle createVehicle(String type) {} // 委托子类实现
}

// 子类自定义生成产品
public class CarFactory extends VehicleFactory
{
    @Override
    protected Vehicle createVehicle(String type) {
        if (size.equals("small"))
            return new smallCar;
        else if (size.equals("big"))
            return new bigCar();
        return null;
    }
}

public class TruckFactory extends VehicleFactory
{
    @Override
    protected Vehicle createVehicle(String type) {
        if (size.equals("small"))
            return new smallTruck;
        else
            return new bigTruck();
        return null;
    }
}