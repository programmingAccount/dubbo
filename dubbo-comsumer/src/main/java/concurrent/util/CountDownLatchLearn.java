package concurrent.util;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch是一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
 * CountDownLatch的一个非常典型的应用场景是：有一个任务想要往下执行，但必须要等到其他的任务执行完毕后
 * 才可以继续往下执行。假如我们这个想要继续往下执行的任务调用一个CountDownLatch对象的await()方法，
 * 其他的任务执行完自己的任务后调用同一个CountDownLatch对象上的countDown()方法，
 * 这个调用await()方法的任务将一直阻塞等待，直到这个CountDownLatch对象的计数值减到0为止。
 */
public class CountDownLatchLearn {
    private static int LATCH_SIZE = 5;
    private static CountDownLatch doneSignal;
    public static void main(String[] args) {
        try {
            doneSignal = new CountDownLatch(LATCH_SIZE);
            // 新建5个任务
            for(int i=0; i<LATCH_SIZE; i++)
                new InnerThread().start();

            System.out.println("main await begin.");
            // "主线程"等待线程池中5个任务的完成
            doneSignal.await();

            System.out.println("main await finished.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    static class InnerThread extends Thread{
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " sleep 1000ms.");
                // 将CountDownLatch的数值减1
                doneSignal.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
