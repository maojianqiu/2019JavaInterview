package JUC.demo06;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronized 和 lock 区别？用新的 lock 有什么好处？
 * <p>
 * 1、原始构成
 * synchronized 关键字属于JVM层面：
 * monitorenter（底层是通过 monitor对象完成的，其实wait/notify等方法也依赖monitor对象，只有在同步块或方法中才可以用wait/notify）
 * monitorexit：正常退出/异常退出
 * lock：是具体类（java.util.concurrent.locks.Lock）是api层面的锁。
 * 2、使用方法
 * syn：不需要手动释放锁
 * lock：需要手动释放锁
 * 3、等待是否可中断
 * syn：不可中断，除非抛异常或者正常运行
 * lock：可中断，
 * 1.设置超时 tryLock()
 * 2.LockInterruptibly()放代码块中，调用interrupt()方法可中断。
 * 4、加锁是否公平
 * syn：非公平
 * lock：默认非公平，可以设置公平
 * 5、绑定多个条件Condition
 * syn：根本没有
 * lock：用来实现分组唤醒需要唤醒的线程们，可以精准唤醒，而不是像syn要么随机唤醒要么唤醒全部。
 * <p>
 * ======================================================
 * 题目：
 * 多线程之间桉顺序调用，A->B->C
 * AA 打印5次，BB 打印10次，CC 打印15次，
 */
class ShareResource {
    private int num = 1; //A=1,B=2,C=3
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            //判断
            while (num != 1) {
                c1.await();
            }
            //干活
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            //通知
            num = 2;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {
        lock.lock();
        try {
            while (num != 2) {
                c2.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            num = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15() {
        lock.lock();
        try {
            while (num != 3) {
                c3.await();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            num = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class SynAndReentrantLockDemo {
    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();

        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                shareResource.print5();
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                shareResource.print10();
            }
        }, "BB").start();
        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                shareResource.print15();
            }
        }, "CC").start();
    }
}
