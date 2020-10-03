package PrimaryStage;

import java.util.Scanner;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: HAOYI
 * @date:2020-10-03 15:47
 **/
public class BlockSceneDemo {
    // 非常大的时间复杂度
    private static long fib(int n) {
        if (n < 2) {
            return n;
        }

        return fib(n - 1) + fib(n - 2);
    }

    private static class FibThread extends Thread {
        private final int n;

        FibThread(int n) {
            this.n = n;
        }

        @Override
        public void run() {
            System.out.printf("fib(%d) = %d%n", n, fib(n));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // 主线程只负责接待客人
            int n = scanner.nextInt();
            //System.out.printf("fib(%d) = %d%n", n, fib(n));
            new FibThread(n).start();   // 每次计算交给一个新人去处理
        }
    }
}
