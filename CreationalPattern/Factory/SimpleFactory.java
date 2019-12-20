/**
 * 简单工厂模式
 * 一个工厂类，里面一个静态方法，根据传入不同的参数，返回不同的派生自同一个父类（或实现自同一个接口）的实例对象
 * 其中，LanZhouNoodle 和 HuangMenChicken 都继承自 Food类
 */
public class FoodFactory {

    public static Food makeFood(String name) {
        if (name.equals("noodle")) {
            Food noodle = new LanZhouNoodle();
            noodle.addSpicy("more");
            return noodle;
        } else if (name.equals("chicken")) {
            Food chicken = new HuangMenChicken();
            chicken.addCondiment("potato");
            return chicken;
        } else {
            return null;
        }
    }
}