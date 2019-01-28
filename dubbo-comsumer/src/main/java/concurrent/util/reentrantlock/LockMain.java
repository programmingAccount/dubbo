package concurrent.util.reentrantlock;

/**
 * ReentrantLock是一个比较常用的锁，它是一个互斥锁，互斥锁的含义就是只能由某个线程进行操作，
 * 其他线程等到释放锁资源之后才能竞争锁；同时它又是可重入的，意思是它可以被单个线程多次获取。
 * ReentrantLock可以获取公平锁和非公平锁，其区别的体现是获取锁的机制上是否公平，
 * ReentrantLock在同一个时间点只能被一个线程获取(当某线程获取到“锁”时，其它线程就必须等待)；
 * ReentraantLock是通过一个FIFO的等待队列来管理获取该锁所有线程的。在“公平锁”的机制下，线程依次排队获取锁；
 * 而“非公平锁”在锁是可获取状态时，不管自己是不是在队列的开头都会获取锁。
 *
 * 简单来说ReentrantLock提供了两个方法：
 *
 * （1）lock()：获取锁
 *
 * public void lock() {
 *         sync.lock();
 *     }
 *
 *
 * （2）unlock：释放锁
 *
 * public void unlock() {
 *         sync.release(1);
 *     }
 *
 *
 *
 * ReentrantLock实现机制：首先提供了一个volatile修饰的state，当state=0时表明没有线程获取锁，
 * 一个线程想要获取锁时，如果state=0，则将state=1，此时这个线程获取到了锁，
 * 其他线程来获取锁时会判断state，此时state不等于0，将此线程添加到一个FIFO队列中，并将线程阻塞，
 * 当一个线程释放锁时会通知阻塞的线程，使阻塞线程变成可执行线程来竞争锁（判断state是否是0），
 * 当一个线程已经获得锁并再次获取锁时，虽然此时state=1，此时只需要将state++就好，
 * 这样这个线程就获取了多次锁，实现机制在AbstractQueuedSynchronizer中接下来我们会对源码简单分析。
 *
 * 简单来说锁的实现机制是通过一个volatile变量state和线程FIFO来实现的，state为0时锁可获取，线程执行；
 * 当state不为0且线程不是当前可执行线程时将线程放到FIFO队列中并阻塞，线程执行完之后再唤醒阻塞线程来进行竞争锁。
 */
public class LockMain {


	public static void main(String[] args) {
		
		final Operation operation = new Operation();
		Runnable runnable = new Runnable() {
			public void run() {
					operation.print1();
				
			}
		};
		Runnable runnable2 = new Runnable() {
			public void run() {
				operation.print2();
			}
		};
		Runnable runnable3 = new Runnable() {
			public void run() {
					operation.print1();
				
			}
		};
		
		Thread thread = new Thread(runnable);
		Thread thread2 = new Thread(runnable2);
		Thread thread3 = new Thread(runnable3);
		
		thread.start();
		thread2.start();
		thread3.start();
	}

}