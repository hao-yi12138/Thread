package PrimaryStage;

import java.util.Scanner;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: HAOYI
 * @date:2020-10-04 14:52
 **/
/**
 * 主线程中创建新的线程（B）
 * 主线程负责通知让（B）停止运行
 */
public class AboutThreadStop {
    static class B extends Thread {
        B() {
            super("B");
        }

        @Override
        public void run() {
            Thread t = Thread.currentThread();
            try {
                while (true) {
                    System.out.println("我正在工作");
                    Thread.sleep(1000);
                    boolean interrupted = Thread.interrupted();//测试中断状态是否被设置
                    //boolean interrupted = t.isInterrupted();
                    System.out.println("是否有人让我停止运行: " + interrupted);//没有在sleep，中断状态被设置
                    if (interrupted == true) {
                        // 干很多事情
                        break;  // break、return 都可以
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("有人让我停止运行，但因为我正在 sleep，所以我收到了这个异常");//在sleep，不设置中断状态，但会收到异常
                // 结束后跳出循环了，所以 run 方法正常结束
               //return;
            }
        }
    }
    //1. Thread.interrupted() 判断当前线程的中断标志被设置，清除中断标志
    //2. Thread.currentThread().isInterrupted() 判断指定线程的中断标志被设置，不清除中断标志

    public static void main(String[] args) {
        B b = new B();
        b.start();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        System.out.println("准备通知 B 线程停止运行");
        b.interrupt();  // 通知 B 停止运行
        System.out.println("已经通知 B 线程停止运行");
    }
    /**
     * 等待一个线程
     */
    /*static class B extends Thread {
        @Override
        public void run() {
            long r = 0;
            for (long i = 0; i < 100_0000_0000L; i++) {
                r += i;
            }
            System.out.println("B 停止运行");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JoinDemo.B b = new JoinDemo.B();
        b.start();
        System.out.println("我不走了，什么时候 b 停止，我才继续");
        b.join();   // main 线程会放弃 CPU，直到 B 停止，才重新进入就绪队列
        System.out.println("B 结束了，我就继续");
    }*/
}
