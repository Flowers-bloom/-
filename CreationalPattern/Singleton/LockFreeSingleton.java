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