package JUC.demo07;

import java.util.concurrent.*;

/**
 *第四种获得 / 使用 java 多线程的方式，线程池
 *
 * Array Arrays
 * Collection Collections
 * Executor Executors
 *
 *
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {
//        System.out.println("CPU核="+Runtime.getRuntime().availableProcessors());
//        ExecutorService threadPool = Executors.newFixedThreadPool(5);//只有 5 个窗口，所以thread 不可能超过五个，一个窗口可办理多个业务。
//        ExecutorService threadPool = Executors.newSingleThreadExecutor(); //只有 1 个窗口，所以thread 只能是一个，一个窗口可办理多个业务。
        ExecutorService threadPool = Executors.newCachedThreadPool(); //不定数 个窗口，所以thread 可以新增。

        //模拟 10 个用户来办理业务，一个用户是一个线程。
        try {
            for (int i = 0; i < 10 ; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t办理业务");
                });
                //newCachedThreadPool
                TimeUnit.SECONDS.sleep(2); //此时可能是 一个线程，因为他顾得过来，太快了就会新增。
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }
}
