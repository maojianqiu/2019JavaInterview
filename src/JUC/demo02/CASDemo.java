package JUC.demo02;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

class User {
    String name;
    int age;

    User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

/**
 * 1 CAS是什么？ == compareAndSwap
 * 2、对象 CAS
 * 3、ABA解决
 */
public class CASDemo {

    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);

    public static void main(String[] args) {
        /* 1、
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5, 2019)+"\tupdate ="+atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 1024)+"\tupdate ="+atomicInteger.get());
*/

       /* 2、
        User u1 = new User("张三",12);
        User u12 = new User("李四",21);
        AtomicReference<User> a = new AtomicReference(u1);

        System.out.println(a.compareAndSet(u1,u12));
        System.out.println(a.compareAndSet(u1,u12));

*/
        System.out.println("========================= 以下是ABA产生问题");
//        new Thread(() -> {
//            atomicReference.compareAndSet(100, 101);
//            atomicReference.compareAndSet(101, 100);
//        }, "T1").start();
//
//        new Thread(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            System.out.println(atomicReference.compareAndSet(100, 2019)+"\t"+atomicReference.get());
//        }, "T2").start();

        System.out.println("========================= 以下是ABA解决问题");
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"t3第1次版本号："+stamp);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"t3第2次版本号："+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"t3第3次版本号："+atomicStampedReference.getStamp());

        }, "T3").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"t4第1次版本号："+stamp);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("是否修改成功="+atomicStampedReference.compareAndSet(100, 2019, stamp, stamp + 1));
            System.out.println(Thread.currentThread().getName()+"t4当前版本号："+atomicStampedReference.getStamp());
        }, "T4").start();
    }
}
