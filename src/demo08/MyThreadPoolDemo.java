package demo08;

import java.util.concurrent.*;

/**
 *
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {
        //1、CPU密集型：先看CPU是几核的
        System.out.println(Runtime.getRuntime().availableProcessors());


        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                1,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
//                new ThreadPoolExecutor.AbortPolicy());  // 5+3 = 8 当线程数超出 8 ，直接报异常！
                new ThreadPoolExecutor.CallerRunsPolicy());  // 5+3 = 8 当线程数超出 8 ，回退给main 线程执行

        try {
            for (int i = 0; i < 9 ; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t办理业务");
                });
                //newCachedThreadPool
//                TimeUnit.SECONDS.sleep(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }
}
