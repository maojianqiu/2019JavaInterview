package demo01;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData{
    volatile int number=0;

    void addTo60(){
        this.number=60;
    }

    //原子性,number 前面添加volatile。volatile 并不保证原子性。可以加 synchronized
    void addplusplus(){
        this.number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    void addMyAtomic(){
        this.atomicInteger.getAndIncrement();
    }
}

/**
 * 1 验证 volatile 可见性
 * 2 验证原子性
 * 3 why D1源码
 * 4 如何解决？
 *  1、加synchronized
 *  2、使用 Atonmic
 */
public class VolatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();

        for (int i = 0;i < 20 ; i++){
            new Thread(()->{
                for (int j = 0; j < 1000 ; j++) {
                    myData.addplusplus();
                    myData.addMyAtomic();
                }
            },String.valueOf(i)).start();
        }

        //需要等待 20 个线程完成后，取最后的结果值看是多少？
        while(Thread.activeCount()>2){
            Thread.yield();
        }

        //数据少于20000，出现了数据丢失现象
        System.out.println(Thread.currentThread().getName()+"\t finally value:"+myData.number);
        System.out.println(Thread.currentThread().getName()+"\t finally value:"+myData.atomicInteger);
    }

    //volatile 保证可见性，及时通知其他线程，主物理内存的值已经发生改变。
    public static void seekByVolatile(){
        MyData myData = new MyData();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t come in");
            try{
                TimeUnit.SECONDS.sleep(3);
                myData.addTo60();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"\t update num value:"+myData.number);
        },"AAA").start();

        while(myData.number == 0){
            //main线程一直等待循环，知道 number 不为零
        }
        System.out.println(Thread.currentThread().getName()+"\t mission is over:");
    }
}
