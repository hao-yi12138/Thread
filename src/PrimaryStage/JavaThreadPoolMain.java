package PrimaryStage;

import java.util.concurrent.*;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: HAOYI
 * @date:2020-10-10 14:50
 **/
public class JavaThreadPoolMain {
    //线程工厂 创建线程时有机会调用我们的方法
    static class MyThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "名字");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(3);

        // 创建线程池
        ExecutorService threadPool = new ThreadPoolExecutor(
                10,       //核心线程数         正式工
                10,      //线程池所能容纳的最大线程数   正式加临时
                0,           //线程闲置时长   回收非核心线程  最多空闲时间
                TimeUnit.SECONDS,         //keepAliveTime参数时间单位
                queue,
                //new ArrayBlockingQueue<>(3)       //任务队列
                new MyThreadFactory(),
                //new ThreadPoolExecutor.AbortPolicy()
                // new ThreadPoolExecutor.DiscardPolicy()
                 new ThreadPoolExecutor.CallerRunsPolicy()   //拒绝策略

        );

        for (int i = 0; true; i++) {
            // 创建让线程池执行的任务
            Runnable target = new Runnable() {
                @Override
                public void run() {
                    try {
                        TimeUnit.MINUTES.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            // 等同于把任务提交给线程池
            // 线程池内部会选择一个空闲的线程去执行该任务
            System.out.println("提交第 " + i + " 个任务");
            System.out.println(queue.size());
            threadPool.execute(target);
            TimeUnit.SECONDS.sleep(1);
        }
        //threadPool.shutdown(); // 设置线程池的状态为SHUTDOWN，然后中断所有没有正在执行任务的线程
        //threadPool.shutdownNow(); // 设置线程池的状态为 STOP，然后尝试停止所有的正在执行或暂停任务的线程，并返回等待执行任务的列表

    }
}
