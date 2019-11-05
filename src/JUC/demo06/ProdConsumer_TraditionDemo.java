package JUC.demo06;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData{//资源类
    private int num = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws InterruptedException {
        lock.lock();
       try {
           //1 判断
           while (num != 0){
               //等待，不能生产
               condition.await();  // = this.wait();  -- syv
           }
           //2 生产
           num++;
           System.out.println(Thread.currentThread().getName()+"\t生产");
           //3 通知唤醒
           condition.signal();  // = this.notify();  -- syv
       } catch (Exception e) { e.printStackTrace(); }finally {
           lock.unlock();
       }

    }
    public void decrement() throws InterruptedException {
        lock.lock();
        try {
            //1 判断
            while (num != 1){
                //等待，不能生产
                condition.await();  // = this.wait();  -- syv
            }
            //2 生产
            num--;
            System.out.println(Thread.currentThread().getName()+"\t消费");
            //3 通知唤醒
            condition.signal();  // = this.notify();  -- syv
        } catch (Exception e) { e.printStackTrace(); }finally {
            lock.unlock();
        }

    }


}
/**
 * 题目：一个初始值为零的变量，；i个线程对其交替操作，一个加1一个减1，进行五轮。
 *
 * 1 线程 操作方法 资源类
 * 2 判断 干活 通知
 * 3 防止虚假唤醒机制（多线程 判断用 while ）
 */
public class ProdConsumer_TraditionDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"AA").start();
        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"BB").start();

    }
}
