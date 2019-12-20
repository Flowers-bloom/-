/**
 * 无锁的线程安全单例模式
 * 效率比含有同步锁情况下高
 */
public class LockFreeSingleton
{
    private static final LockFreeSingleton instance = new LockFreeSingleton();
    private LockFreeSingleton() {} // 私有化

    // 公开全局访问权限
    public static LockFreeSingleton getInstance() {
        return instance;
    }

    public void doSomething() {
        System.out.println("doing something.");
    }
}

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