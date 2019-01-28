package concurrent.util.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Operation {
    private Lock lock;

    int value = 1;

    public Operation(){
        this.lock = new ReentrantLock(false);
    }

    public void print1(){
        lock.lock();
        value = value +1;
        System.err.println(value);
        lock.unlock();
    }
    public void print2(){
        lock.lock();
        value = value +2;
        System.err.println(value);
        lock.unlock();
    }
}
