/**
 * 同步锁单例模式
 * 线程安全，但同步锁导致效率较低
 */
// 整个实例方法加同步锁，效率低
public class SyncSyncSingleton
{
    private static SyncSyncSingleton instance = null;
    private SyncSingleton() {} // 私有化

    // 公开全局访问权限
    public synchronized static SyncSingleton getInstance() {
        if (instance == null)
            instance = new SyncSingleton();
        return instance;
    }

    public void doSomething() {
        System.out.println("doing something.");
    }
}

// 仅判断时加同步锁，效率相对于整个方法加锁更高
public class SyncSingleton
{
    private static SyncSingleton instance = null;
    private SyncSingleton() {} // 私有化

    // 公开全局访问权限
    public static SyncSingleton getInstance() {
        synchronized (SyncSingleton.class) {
            if (instance == null)
                instance = new SyncSingleton();
        }
        return instance;
    }

    public void doSomething() {
        System.out.println("doing something.");
    }
}

// 双重校验锁机制的同步锁单例模式
public class SyncSingleton
{
    private static SyncSingleton instance = null;
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