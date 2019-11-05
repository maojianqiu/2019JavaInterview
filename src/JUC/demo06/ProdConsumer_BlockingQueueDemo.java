package JUC.demo06;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyResource{//资源类
    private volatile boolean FLAG = true;//默认开启，进行生产+消费。
    private AtomicInteger atomicInteger = new AtomicInteger();

    BlockingQueue<String> blockingQueue = null; //只要是子类就可以使用此类进行业务，确保可以供多种子类使用。记住要有构造函数构造对象！

    public MyResource(BlockingQueue<String> blockingQueue) {//传接口不传类。
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());//测试传的是什么类。
    }

    public void myProd() throws InterruptedException {
        String data = null;
        boolean retValue;
        while(FLAG){ //true 开始生产，false 停止
            data = atomicInteger.incrementAndGet()+"";
            retValue = blockingQueue.offer(data,2L,TimeUnit.SECONDS);data = atomicInteger.incrementAndGet()+"";
            retValue = blockingQueue.offer(data,2L,TimeUnit.SECONDS);data = atomicInteger.incrementAndGet()+"";
            retValue = blockingQueue.offer(data,2L,TimeUnit.SECONDS);data = atomicInteger.incrementAndGet()+"";
            retValue = blockingQueue.offer(data,2L,TimeUnit.SECONDS);data = atomicInteger.incrementAndGet()+"";
            retValue = blockingQueue.offer(data,2L,TimeUnit.SECONDS);data = atomicInteger.incrementAndGet()+"";
            retValue = blockingQueue.offer(data,2L,TimeUnit.SECONDS);
            if (retValue)
            {
                System.out.println(Thread.currentThread().getName()+"\t插入队列"+data+"成功");
            }else
            {
                System.out.println(Thread.currentThread().getName()+"\t插入队列"+data+"失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"\tFLAG = false");
    }

    public void myConsume() throws InterruptedException {
        String result = null;
        while(FLAG){ //true 开始消费，false 停止
            result = blockingQueue.poll(2L,TimeUnit.SECONDS);
            if (null == result || result.equalsIgnoreCase(""))
            {
                FLAG = false;
                System.out.println(Thread.currentThread().getName()+"\t超过2秒钟没有取到，退出");
                return;
            }
            System.out.println(Thread.currentThread().getName()+"\t"+result+"\t成功");
        }
    }

    public void stop()
    {
        this.FLAG = false;
    }
}

/**
 * 题目：一个初始值为零的变量，；i个线程对其交替操作，一个加1一个减1，进行五轮。
 *
 * 1 线程 操作方法 资源类
 * 2 判断 干活 通知
 * 3 防止虚假唤醒机制（多线程 判断用 while ）
 */
public class ProdConsumer_BlockingQueueDemo {
    public static void main(String[] args) {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(2));

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t生产线程启动");
            try {
                myResource.myProd();
            } catch (Exception e) { e.printStackTrace(); }finally { }
        },"Prod").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t生产线程启动");
            try {
                myResource.myConsume();
            } catch (Exception e) { e.printStackTrace(); }finally { }
        },"Consume").start();

        try { TimeUnit.SECONDS.sleep(5);} catch (Exception e) { e.printStackTrace(); }finally { }
        System.out.println("5秒钟已过去，暂停");

        myResource.stop();
    }

}
