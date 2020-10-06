package PrimaryStage;

import java.util.concurrent.TimeUnit;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: HAOYI
 * @date:2020-10-04 20:40
 **/
public class ThreadStateTransfer {
    static class SubThread extends Thread {
        @Override
        public void run() {
            /*
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();         // 阻塞在这里，输入任何内容，该方法返回
            System.out.println("子线程即将退出");
            */

            /*
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            */

            try {
                for (int i = 0; i < 50; i++) {
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }   // 子线程走完这里了，就终止了
    }

    public static void main(String[] args) throws InterruptedException {
        Thread p = new SubThread();
        System.out.println(p.getState());
        p.start();
        System.out.println(p.getState());
        while (p.isAlive()) {
            System.out.println(p.getState());   // 看到的是 RUNNABLE 还是 TIMED_WAITING
            // 理论上是能看到 RUNNABLE 的
            // 但实际运行中，基本是看不到 RUNNABLE 的
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(p.getState());
    }
}
