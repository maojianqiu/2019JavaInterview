package demo04;

/**
 * 可重入锁
 *
 * 指的是同一个线程外层函数获得锁之后，内层函数仍然能获得该锁的代码，
 * 在同一个线程在外层获取锁的时候，在进入内部方法会自动获取锁。
 */
class Phone{

    public synchronized void sendSMS()throws Exception{
        System.out.println(Thread.currentThread().getName()+"--sendSMA");
        sendSEmail();
    }

    public synchronized void sendSEmail()throws Exception{
        System.out.println(Thread.currentThread().getName()+"--sendSEmail");
    }

}
public class ReentrantLockDemo {

    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t1").start();

        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t2").start();
    }

}
