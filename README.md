# 设计模式
> 学习自：https://github.com/h2pl/Java-Tutorial#Java%E8%BF%9B%E9%98%B6
## 创建型模式
提供友好的创建对象的方式
### 单例模式
* 简单单例模式
    * 懒汉式
    ```java
    public class Singleton
    {
        private static Singleton instance = null;
        private Singleton() {} // 私有化
    
        // 公开全局访问权限
        public static Singleton getInstance() {
            if (instance == null)
                instance = new Singleton();
            return instance;
        }
    
        public void doSomething() {
            System.out.println("doing something.");
        }
    }
    ```
    * 饿汉式
    ```java
    public class Singleton
    {
        private static Singleton instance = new Singleton();
        private Singleton() {} // 私有化
    
        // 公开全局访问权限
        public static Singleton getInstance() {
            return instance;
        }
    
        public void doSomething() {
            System.out.println("doing something.");
        }
    }
    ```
* 同步锁单例模式
    * 双重校验机制同步
    ```java
    public class SyncSingleton
    {
        private static volatile SyncSingleton instance = null; // volatile减少读操作带来的开销
        private SyncSingleton() {} // 私有化
    
        // 公开全局访问权限
        public static SyncSingleton getInstance() {
            // 双重校验
            // 提高了单例已被创建的情况下获取单例的效率
            if (instance == null) {
                synchronized (SyncSingleton.class) {
                    if (instance == null)
                        instance = new SyncSingleton();
                }
            }
            return instance;
        }
    
        public void doSomething() {
            System.out.println("doing something.");
        }
    }              
    ```
* 无锁单例模式
    ```java
    // 使用嵌套类，封装性和可读性更高
    public class LockFreeSingleton
    {
        private LockFreeSingleton() {}
    
        // 静态嵌套类 可以访问外部的静态变量和静态方法
        private static class Holder {
            private static LockFreeSingleton instance = new LockFreeSingleton();
        }
    
        public static LockFreeSingleton getInstance() {
            return Holder.instance;
        }
    
        public void doSomething() {
            System.out.println("doing something.");
        }
    }
    ```
饿汉式的单例模式是线程安全的；而懒汉式的单例模式，如果不加入锁机制，那么是线程不安全的。
### 工厂模式
* 简单工厂模式
```java
/**
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
```
* 工厂方法模式
```java
/**
 * 核心在于第一步，选好我们需要的工厂，然后第二步和简单工厂一样
 * 其中，ChineseFoodA、ChineseFoodB、AmericanFoodA、AmericanFoodB 都派生自 Food类
 */
public interface FoodFactory {
    Food makeFood(String name);
}

public class ChineseFoodFactory implements FoodFactory {

    @Override
    public Food makeFood(String name) {
        if (name.equals("A")) {
            return new ChineseFoodA();
        } else if (name.equals("B")) {
            return new ChineseFoodB();
        } else {
            return null;
        }
    }
}

public class AmericanFoodFactory implements FoodFactory {

    @Override
    public Food makeFood(String name) {
        if (name.equals("A")) {
            return new AmericanFoodA();
        } else if (name.equals("B")) {
            return new AmericanFoodB();
        } else {
            return null;
        }
    }
}

public class APP {
    public static void main(String[] args) {
        // 先选择一个具体的工厂
        FoodFactory factory = new ChineseFoodFactory();
        // 由第一步的工厂产生具体的对象，不同的工厂造出不一样的对象
        Food food = factory.makeFood("A");
    }
}
```
* 抽象工厂模式
```java
/**
 * 当涉及产品族时，需要引入抽象工厂模式
 * <p>
 * 比如我们要造一台电脑
 * 如果我们继续采用工厂模式，那么我们会有CPU工厂（派生intelCpu，amdCpu），主板工厂（派生intelBoard，amdBoard）......
 * 然后客户端再将这些构件组装在一起。
 * 如此方法，优点：如果要增加构件，易于扩展
 * 缺点：由于客户端的自定义组装，可能出现如CPU与Board不兼容使用的情况
 * <p>
 * 如果采用抽象工厂模式，我们会抽象一个Computer工厂（派生不同品牌电脑interComputer，amdComputer），在工厂内定义电脑的构件搭配
 * 如此方法，优点：解决构件不兼容的情况
 * 缺点：如果要增加构件，需要修改每个工厂，给每种品牌的电脑都加上该新增构件的方法，不易于扩展，有点违背对修改关闭，对扩展开放的原则
 */
public interface Computer {
    CPU makeCPU();

    MainBoard makeMainBoard();
}

public class IntelComputer implements Computer {
    @Override
    public CPU makeCPU() {
        return new CPU();
    }

    @Override
    public MainBoard makeMainBoard() {
        return new MainBoard();
    }
}

public class AmdComputer implements Computer {
    @Override
    public CPU makeCPU() {
        return new CPU();
    }

    @Override
    public MainBoard makeMainBoard() {
        return new MainBoard();
    }
}

public class APP {
    public static void main(String[] args) {
        // 第一步就要选定一个“大厂”
        ComputerFactory cf = new AmdFactory();
        // 从这个大厂造 CPU
        CPU cpu = cf.makeCPU();
        // 从这个大厂造主板
        MainBoard board = cf.makeMainBoard();

        // 将同一个厂子出来的 CPU、主板、硬盘组装在一起
        Computer result = new Computer(cpu, board);
    }
}
```
### 建造者模式
```java
/**
 * 这个模式写法吸引人，但是写了很多无用的代码，感觉没有太高性价比
 * 不过，当属性很多时，且一些选填，一些必填时，这个模式会使得代码清晰很多
 * 而且，builder()方法中校验各个字段参数看起来代码很优雅
 *
 * 此外，这个模式还提供给大家一个链式写法的思路
 * User的getter方法不变，但setter方法return this
 * User user = new User().setName("bud").setPassword("123456").setAge(20);
 */
class User {
    // 下面是“一堆”的属性
    private String name;
    private String password;
    private String nickName;
    private int age;

    // 构造方法私有化，不然客户端就会直接调用构造方法了
    private User(String name, String password, String nickName, int age) {
        this.name = name;
        this.password = password;
        this.nickName = nickName;
        this.age = age;
    }
    // 静态方法，用于生成一个 Builder，这个不一定要有，不过写这个方法是一个很好的习惯，
    // 有些代码要求别人写 new User.UserBuilder().a()...build() 看上去就没那么好
    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        // 下面是和 User 一模一样的一堆属性
        private String  name;
        private String password;
        private String nickName;
        private int age;

        private UserBuilder() {
        }

        // 链式调用设置各个属性值，返回 this，即 UserBuilder
        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder nickName(String nickName) {
            this.nickName = nickName;
            return this;
        }

        public UserBuilder age(int age) {
            this.age = age;
            return this;
        }

        // build() 方法负责将 UserBuilder 中设置好的属性“复制”到 User 中。
        // 当然，可以在 “复制” 之前做点检验
        public User build() {
            if (name == null || password == null) {
                throw new RuntimeException("用户名和密码必填");
            }
            if (age <= 0 || age >= 150) {
                throw new RuntimeException("年龄不合法");
            }
            // 还可以做赋予”默认值“的功能
            if (nickName == null) {
                nickName = name;
            }
            return new User(name, password, nickName, age);
        }
    }
}

public class Singleton {
    public static void main(String[] args) {
        User d = User.builder()
                .name("foo")
                .password("pAss12345")
                .age(25)
                .build();
    }
}
```
### 原型模式（略）
简而言之，就是对象的浅拷贝和深拷贝

## 结构型模式
通过改变代码结构来达到解耦的目的，使得代码更加容易维护和扩展
### 代理模式
```java
/**
 * 最常使用的模式之一，用一个代理来隐藏具体的实现细节，通常还在真实实现的前后
 * 添加一部分逻辑
 * 说白了，代理模式就是“方法包装”和“方法增强”，将具体实现细节包装在代理之中，
 * 并且在调用具体实现的前后添加一部分逻辑，增加方法
 * 使用场景：Spring的AOP（面向切面编程），就是动态代理过程的过程
 */
public interface FoodService {
    Food makeChicken();
    Food makeNoodle();
}

public class FoodServiceImpl implements FoodService {
    public Food makeChicken() {
          Food f = new Chicken();
        f.setChicken("1kg");
          f.setSpicy("1g");
          f.setSalt("3g");
        return f;
    }
    public Food makeNoodle() {
        Food f = new Noodle();
        f.setNoodle("500g");
        f.setSalt("5g");
        return f;
    }
}

// 代理要表现得“就像是”真实实现类，所以需要实现 FoodService
public class FoodServiceProxy implements FoodService {

    // 内部一定要有一个真实的实现类，当然也可以通过构造方法注入
    private FoodService foodService = new FoodServiceImpl();

    public Food makeChicken() {
        System.out.println("我们马上要开始制作鸡肉了");

        // 如果我们定义这句为核心代码的话，那么，核心代码是真实实现类做的，
        // 代理只是在核心代码前后做些“无足轻重”的事情
        Food food = foodService.makeChicken();

        System.out.println("鸡肉制作完成啦，加点胡椒粉"); // 增强
          food.addCondiment("pepper");

        return food;
    }
    public Food makeNoodle() {
        System.out.println("准备制作拉面~");
        Food food = foodService.makeNoodle();
        System.out.println("制作完成啦");
        return food;
    }
}

public class App
{
    public static void main(String[] args) {
         // 这里用代理类来实例化
         FoodService foodService = new FoodServiceProxy();
         foodService.makeChicken();
    }
}
```
### 适配器模式
* 对象适配
```java
// 将具体对象注入适配器中，然后适配器实现指定接口，从而实现将对象适配成需要的对象
public interface Duck {
    public void quack(); // 鸭的呱呱叫
      public void fly(); // 飞
}

public interface Cock {
    public void gobble(); // 鸡的咕咕叫
      public void fly(); // 飞
}

public class WildCock implements Cock {
    public void gobble() {
        System.out.println("咕咕叫");
    }
      public void fly() {
        System.out.println("鸡也会飞哦");
    }
}

// 毫无疑问，首先，这个适配器肯定需要 implements Duck，这样才能当做鸭来用
public class CockAdapter implements Duck {

    Cock cock;
    // 构造方法中需要一个鸡的实例，此类就是将这只鸡适配成鸭来用
      public CockAdapter(Cock cock) {
        this.cock = cock;
    }

    // 实现鸭的呱呱叫方法
      @Override
      public void quack() {
        // 内部其实是一只鸡的咕咕叫
        cock.gobble();
    }

      @Override
      public void fly() {
        cock.fly();
    }
}

public class APP 
{
    public static void main(String[] args) {
        // 有一只野鸡
        Cock wildCock = new WildCock();
        // 成功将野鸡适配成鸭
        Duck duck = new CockAdapter(wildCock);
    }
}
```
* 类适配
适配器继承要适配的类，然后适配器再实现接口，从而将适配类适配成目标类
* 适配器模式和代理模式的区别
适配器模式和代理模式虽然代码结构相似，但是两者的目的的区别是很明显的，
适配器目的在于将类或对象适配成目标形式，而代理模式目的在于对原方法的代理以及增强
### 桥梁模式
```java
// 桥梁，解耦的关键
public interface DrawAPI {
   public void draw(int radius, int x, int y);
}

public class RedPen implements DrawAPI {
   @Override
   public void draw(int radius, int x, int y) {
      System.out.println("用红色笔画图，radius:" + radius + ", x:" + x + ", y:" + y);
   }
}
public class GreenPen implements DrawAPI {
   @Override
   public void draw(int radius, int x, int y) {
      System.out.println("用绿色笔画图，radius:" + radius + ", x:" + x + ", y:" + y);
   }
}
public class BluePen implements DrawAPI {
   @Override
   public void draw(int radius, int x, int y) {
      System.out.println("用蓝色笔画图，radius:" + radius + ", x:" + x + ", y:" + y);
   }
}

// 抽象，使用桥梁联系
public abstract class Shape {
   protected DrawAPI drawAPI;

   protected Shape(DrawAPI drawAPI){
      this.drawAPI = drawAPI;
   }
   public abstract void draw();    
}

// 圆形
public class Circle extends Shape {
   private int radius;

   public Circle(int radius, DrawAPI drawAPI) {
      super(drawAPI);
      this.radius = radius;
   }

   public void draw() {
      drawAPI.draw(radius, 0, 0);
   }
}
// 长方形
public class Rectangle extends Shape {
    private int x;
      private int y;

      public Rectangle(int x, int y, DrawAPI drawAPI) {
        super(drawAPI);
          this.x = x;
          this.y = y;
    }
      public void draw() {
      drawAPI.draw(0, x, y);
   }
}

public class APP
{
    public static void main(String[] args) {
        Shape greenCircle = new Circle(10, new GreenPen());
          Shape redRectangle = new Rectangle(4, 8, new RedPen());
    
          greenCircle.draw();
          redRectangle.draw();
    }
}
```
通过抽象父类派生出不同的子类，而父类使用了桥梁接口，使得派生子类可以调用实现了桥梁接口的具体类的方法，
即通过实现了桥梁接口的类赋予了派生子类特定的属性或行为，从而*实现了对象属性或行为的解耦*，增强了代码的可扩展性。
### 装饰模式
```java
// 定义饮料基类
public abstract class Beverage {
      // 返回描述
      public abstract String getDescription();
      // 返回价格
      public abstract double cost();
}

// 基础饮料实现
public class BlackTea extends Beverage {
      public String getDescription() {
        return "红茶";
    }
      public double cost() {
        return 10;
    }
}
public class GreenTea extends Beverage {
    public String getDescription() {
        return "绿茶";
    }
      public double cost() {
        return 11;
    }
}

// 调料（装饰器的基类）
public abstract class Condiment extends Beverage {

}

public class Lemon extends Condiment {
    private Beverage bevarage;
      // 这里很关键，需要传入具体的饮料，如需要传入没有被装饰的红茶或绿茶，
      // 当然也可以传入已经装饰好的芒果绿茶，这样可以做芒果柠檬绿茶
      public Lemon(Beverage bevarage) {
        this.bevarage = bevarage;
    }
      public String getDescription() {
        // 装饰
        return bevarage.getDescription() + ", 加柠檬";
    }
      public double cost() {
          // 装饰
        return beverage.cost() + 2; // 加柠檬需要 2 元
    }
}
public class Mango extends Condiment {
    private Beverage bevarage;
      public Mango(Beverage bevarage) {
        this.bevarage = bevarage;
    }
      public String getDescription() {
        return bevarage.getDescription() + ", 加芒果";
    }
      public double cost() {
        return beverage.cost() + 3; // 加芒果需要 3 元
    }
}

public class APP
{
    public static void main(String[] args) {
          // 首先，我们需要一个基础饮料，红茶、绿茶或咖啡
        Beverage beverage = new GreenTea();
          // 开始装饰
          beverage = new Lemon(beverage); // 先加一份柠檬
          beverage = new Mongo(beverage); // 再加一份芒果
    
          System.out.println(beverage.getDescription() + " 价格：￥" + beverage.cost());
          //"绿茶, 加柠檬, 加芒果 价格：￥16"
    }
}
```
首先，定义一个抽象组件基类，让一些具体组件类和装饰器抽象基类继承该基类；然后，就可以定义多个具体的装饰器；
在具体的装饰器类中依赖抽象组件基类，从而可以调用具体组件类方法，并且加上“装饰”。
### 门面模式（外观模式 Facade Pattern）
```java
// 不使用门面模式
public class APP
{
    public static void main(String[] args) {
        // 画一个圆形
          Shape circle = new Circle();
          circle.draw();
    
          // 画一个长方形
          Shape rectangle = new Rectangle();
          rectangle.draw();
    }
}
```
```java
// 门面模式调用
public class ShapeMaker {
   private Shape circle;
   private Shape rectangle;
   private Shape square;

   public ShapeMaker() {
      circle = new Circle();
      rectangle = new Rectangle();
      square = new Square();
   }

  /**
   * 下面定义一堆方法，具体应该调用什么方法，由这个门面来决定
   */
   public void drawCircle(){
      circle.draw();
   }
   public void drawRectangle(){
      rectangle.draw();
   }
   public void drawSquare(){
      square.draw();
   }
}

public class APP
{
    public static void main(String[] args) {
      ShapeMaker shapeMaker = new ShapeMaker();
    
      // 客户端调用现在更加清晰了
      shapeMaker.drawCircle();
      shapeMaker.drawRectangle();
      shapeMaker.drawSquare();        
    }
}
```
门面模式的优势在于使得客户端不需要再关注实例化时应该使用哪个类，而是直接调用门面提供的方法就可以，因为门面模式
提供的方法名对于客户端来说十分友好 
### 组合模式
组合模式用于表示具有层次结构的数据，使得我们对单个对象和组合对象的访问具有一致性。
```java
public class Employee {
   private String name;
   private String dept;
   private int salary;
   private List<Employee> subordinates; // 下属

   public Employee(String name,String dept, int sal) {
      this.name = name;
      this.dept = dept;
      this.salary = sal;
      subordinates = new ArrayList<Employee>();
   }

   public void add(Employee e) {
      subordinates.add(e);
   }

   public void remove(Employee e) {
      subordinates.remove(e);
   }

   public List<Employee> getSubordinates(){
     return subordinates;
   }

   public String toString(){
      return ("Employee :[ Name : " + name + ", dept : " + dept + ", salary :" + salary+" ]");
   }   
}
``` 
通常，这种类需要定义 add(node)、remove(node)、getChildren() 这些方法。
### 享元模式（轻量级 Flyweight Pattern）  
Flyweight 是轻量级的意思，享元分开来说就是 共享 元器件，也就是复用已经生成的对象，这种做法当然也就是轻量级的了。
复用对象最简单的方式是，用一个 HashMap 来存放每次新生成的对象。每次需要一个对象的时候，先到 HashMap 中看看有没有，
如果没有，再生成新的对象，然后将这个对象放入 HashMap 中。
## 行为型模式
### 观察者模式 
* java自带观察者实现工具类  
```
观察者 <--消息推送----订阅服务-->  主题  
主题    extends   java.util.Observable  
观察者   implements   java.util.Observer
```
