package PrimaryStage;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: HAOYI
 * @date:2020-10-04 10:06
 **/
public class PrintThreadFields {
    static class SubThread extends Thread{
        SubThread(){
            super("子线程");
        }
        @Override
        public void run() {
           printFields();
           /* try {
                Thread.sleep(100*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new SubThread();
        thread.start();//线程就绪
        printFields();
        //Thread.sleep(100*1000);
    }
    private static void printFields() {
        Thread t = Thread.currentThread();
        long id = t.getId();
        System.out.println("线程的名字"+id+"："+t.getName());
        System.out.println("线程的优先级"+id+"："+t.getPriority());
        System.out.println("线程的状态"+id+"："+t.getState());
        System.out.println("线程是否存活"+id+"："+t.isAlive());
        System.out.println("线程是否是后台线程"+id+"："+t.isDaemon());

    }
}
