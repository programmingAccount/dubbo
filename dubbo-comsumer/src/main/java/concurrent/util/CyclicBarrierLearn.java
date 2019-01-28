package concurrent.util;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier是一个同步辅助类，允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)。
 * 因为该 barrier 在释放等待线程后可以重用，所以称它为循环 的 barrier。
 *
 * CyclicBarrier的实现机制。
 * 首先在CyclicBarrier中没有设置volatile修饰的state锁标识位，而是定义了一个普通int变量count，
 * 但是对count的修改操作是通过ReentrantLock独占锁来实现的，和我们经常见到的volatile修改的state的功能类似。
 * 线程的阻塞与唤起是使用Condition完成的，参考博客并发编程--并发编程包Condition条件
 *
 * CyclicBarrier是包含了"ReentrantLock对象lock"和"Condition对象trip"，它是通过独占锁实现的。
 * CyclicBarrier和CountDownLatch的区别是：
 * (1) CountDownLatch的作用是允许1或N个线程等待其他线程完成执行；而CyclicBarrier则是允许N个线程相互等待。
 * (2) CountDownLatch的计数器无法被重置；CyclicBarrier的计数器可以被重置后使用，因此它被称为是循环的barrier。
 */
public class CyclicBarrierLearn {
	
	private static int SIZE = 5;
	private static CyclicBarrier cb;
	
	public static void main(String[] args) {

		cb = new CyclicBarrier(SIZE);
		// 新建5个任务
		for(int i=0; i<SIZE; i++)
				new InnerThread().start();
		}

	static class InnerThread extends Thread{
		public void run() {
			try {
					System.out.println(Thread.currentThread().getName() + " wait for CyclicBarrier.");
					// 将cb的参与者数量加1
					cb.await();
					// cb的参与者数量等于5时，才继续往后执行
					System.out.println(Thread.currentThread().getName() + " continued.");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}