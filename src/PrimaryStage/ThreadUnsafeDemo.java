package PrimaryStage;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: HAOYI
 * @date:2020-10-05 08:48
 **/
/**
 * 启动两个线程，同时操作一个变量 v = 0
 * 一个线程对该变量执行 N 次 v++
 * 另一个线程对该变量执行 N 次 v--
 * 问，当两个线程都执行结束时，v 的值是多少？
 * 期望值是多少？      0
 * 实际值是多少？      随机值(N 的值比较小时，出现随机值的情况较少，N 大时，出现情况较多）
 */
public class ThreadUnsafeDemo {
    private static final int N = 1_0000_0000;// 因为是静态属性，所以共享
    private static int v = 0;                // 因为 Add/Sub 线程都会修改 v
                                            // 所以才会产生线程安全问题，需要想办法解决
    /*static class Add extends Thread {
       @Override
        public void run() {
          for (int i = 0; i < N; i++) {
                 v++;
          }
       }
   }
     static class Add extends Thread {
       @Override
        public void run() {
          for (int i = 0; i < N; i++) {
                 v--;
          }
       }
   }*/
    //指向的不是同一个对象，线程不安全
    /*static class Add extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < N; i++) {
                synchronized (Sub.currentThread()) {
                    v++;
                }
            }
        }
    }
    static class Sub extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < N; i++) {
                synchronized (Sub.currentThread()) {
                    v--;
                }
            }
        }
    }*/
    //解决线程不安全      synchronized
    static class Add extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < N; i++) {
                synchronized (ThreadUnsafeDemo.class) {
                    v++;
                }
            }
        }
    }
       static class Sub extends Thread {
            @Override
            public void run() {
                for (int i = 0; i < N; i++) {
                    synchronized (ThreadUnsafeDemo.class) {
                        v--;
                    }
                }
            }
        }
    /*static class Add extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < N; i++) {
                synchronized (Sub.class) {
                    v++;
                }
            }
        }
    }
    static class Sub extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < N; i++) {
                synchronized (Sub.class) {
                    v--;
                }
            }
        }
    }*/
    public static void main(String[] args) throws InterruptedException {
        Add a = new Add();
        Sub b = new Sub();
        a.start();
        b.start();

        a.join();
        b.join();
        // 这里时，a 和 s 都结束了
        System.out.println(v);
    }
}
