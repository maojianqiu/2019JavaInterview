package JUC.demo04;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SpinLockDemo {
    static AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public static void mylock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"  come in  mylock");
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }

    public static void myunlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName()+"  invoke  myunlock");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(()->{
            spinLockDemo.mylock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) { e.printStackTrace(); }finally { }
            spinLockDemo.myunlock();
        },"AA").start();

        //保证 AA 能执行 mylock
        try {TimeUnit.SECONDS.sleep(1); } catch (Exception e) { e.printStackTrace(); }finally { }

        new Thread(()->{
            spinLockDemo.mylock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) { e.printStackTrace(); }finally { }
            spinLockDemo.myunlock();
        },"BB").start();
    }
}
