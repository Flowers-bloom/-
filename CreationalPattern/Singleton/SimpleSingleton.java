/**
 * 简单单例模式
 * 线程不安全
 */
// 懒汉式
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

// 饿汉式
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