package JUC.demo06;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * put 一个 take 一个 ,才会继续 put 下一个
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();
        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+"\tput 1");
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName()+"\tput 2");
                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName()+"\tput 3");
                blockingQueue.put("3");

            } catch (Exception e) { e.printStackTrace(); }finally { }
        },"AAA").start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+"\ttake 1");
                blockingQueue.take();
            } catch (Exception e) { e.printStackTrace(); }finally { }
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+"\ttake 2");
                blockingQueue.take();
            } catch (Exception e) { e.printStackTrace(); }finally { }
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName()+"\ttake 3");
                blockingQueue.take();
            } catch (Exception e) { e.printStackTrace(); }finally { }

        },"BBB").start();
    }
}
