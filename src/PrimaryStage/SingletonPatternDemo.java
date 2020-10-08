package PrimaryStage;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: HAOYI
 * @date:2020-10-08 15:52
 **/
/**
 * 饿汉模式的单例
 * 天生线程安全
 */
// 通过 final 修饰类，必然有人定义该类的子类来构造对象（有时）
final class SingletonStarve {
    // 1. 我需要保存我仅有的一个对象的引用
    // 了解：final 修饰了，保证了原子性了
    private static final SingletonStarve instance;

    // 2. 饿汉模式，也就是一开始就初始化
    //    静态代码块 or 直接初始化都可以
    static {
        // 只发生在 SingletonStarve 被加载时执行
        // 而 SingletonStarve 的加载，只发生一次
        instance = new SingletonStarve();
    }

    // 3. 如果需要该类的对象，只能使用这个 instance 指向的对象
    //    所以，需要提供方法，把对方返回
    public static SingletonStarve getInstance() {
        // 这个方法被调用了 100W 次，但返回的都是指向同一个对象的引用
        return instance;
    }

    // 4. 尽可能的避免有人无意中构造出该类的对象
    //    所以，把类的构造方法标记有 private，减少误用可能
    private SingletonStarve() {
    }
}
//懒汉模式单例
class SingletonLazy {
    // 1. 需要保存该类唯一对象的引用
    private static SingletonLazy instance = null;

    public synchronized static SingletonLazy getInstance() {
        if (instance == null) {
            instance = new SingletonLazy();
        }

        return instance;
    }

    public static SingletonLazy getInstanceUnsafe() {
        // getInstance 被调用的时候，代表有人需要
        // 该类的对象了
        // 所以应该实例出对象出来了 —— 按需加载
        /*
        if (instance == null) {
            // 代表对象还没有被初始化
            // 需要先初始化
            instance = new SingletonLazy();
            return instance;
        } else {
            // 代表 instance 已经指向了一个对象了
            // 所以我们不需要实例化对象了
            // 直接返回该对象的引用即可
            return instance;
        }
        */
        if (instance == null) { // 判断的时候 instance 还是 null
            // 到这里实例化的时候，instance 有可能已经不是 null 了
            instance = new SingletonLazy();
        }

        return instance;
    }

    private SingletonLazy() {}
}
class SingletonLazyTwice {
    private final String name;
    private final int age;
    private final String gender;

    private static volatile SingletonLazyTwice instance = null;

    // 以下写法是线程安全的么？
    // 不是
    // 这把锁加的毫无意义
    public static SingletonLazyTwice getInstanceUnsafe() {
        if (instance == null) {
            synchronized (SingletonLazyTwice.class) {
                instance = new SingletonLazyTwice();
            }
        }

        return instance;
    }

    // 通过二次判断，既保证了线程安全，又减少了抢锁的次数
    // 这个版本中仍然存在着一个小错误
    public static SingletonLazyTwice getInstanceWrong() {
        if (instance == null) {
            synchronized (SingletonLazyTwice.class) {
                // instance 可能是 null，也可能不是 null
                if (instance == null) {
                    // 这里因为锁的存在，只会执行一次
                    // 保证了单例
                    instance = new SingletonLazyTwice();
                } else {
                    // 抢锁之前，instance 是 null
                    // 但是从抢锁到抢锁成功这段期间
                    // instance 已经不是 null
                    // 代表已经被之前抢到锁的线程实例化好了
                    // 也就什么都不需要做了
                }
            }
        }

        return instance;
    }

    public static SingletonLazyTwice getInstance() {
        if (instance == null) {
            // instance == null，然后抢锁
            // 如果 instance != null，需要抢锁么？   不需要
            // 一个线程持有锁，能干扰另一个不抢锁的线程么？
            synchronized (SingletonLazyTwice.class) {
                if (instance == null) {
                    // 下面的代码可能会被重排序
                    instance = new SingletonLazyTwice();
                }
            }
        }

        return instance;
    }

    private SingletonLazyTwice() {
        name = "yi";
        age = 14;
        gender = "男";
    }
}
public class SingletonPatternDemo {
    static class SubThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                {
                    SingletonStarve instance = SingletonStarve.getInstance();
                    System.out.println(instance);
                }

                {
                    SingletonLazy instance = SingletonLazy.getInstance();
                    System.out.println(instance);
                }
            }
        }
    }
    // 由于 SingletonStarve 构造方法是 private 的
    // 所以，会有语法错误
    // SingletonStarve mistake = new SingletonStarve();

    // 如果要用到该类的对象
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new SubThread().start();
        }
        SingletonStarve right = SingletonStarve.getInstance();

        SingletonLazy rightLazy = SingletonLazy.getInstance();
    }
}
