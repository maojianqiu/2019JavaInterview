package JUC.demo07;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

class MyThread implements Callable<Integer>
{

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName()+"===");
        TimeUnit.SECONDS.sleep(2);
        return 1024;
    }
}
/**
 *为什么用 Callable，因为它可以返回值。如果100个线程其中98个成功，2个不成功，可以通过值来判断。
 *
 * new Thread();       //只能穿 Runnable 接口，不能传 Callable ,那该怎么办？？？靠中间人
 *  FutureTask 实现了 runnable，而他本身的构造方法又可以传入 Callable
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        Thread tq = new Thread();       //只能穿 Runnable 接口，不能传 Callable ,那该怎么办？？？
        FutureTask<Integer> futureTask = new FutureTask(new MyThread());
        new Thread(futureTask,"AA").start();
        new Thread(futureTask,"AA").start();//new Thread 只进入一次call,因为复用了，但是可以重新 new FutureTask()进入.

        System.out.println(Thread.currentThread().getName()+"----");
        int r1 = 100; //主线程的计算值

        while(!futureTask.isDone()){//等待 callable 计算完

        }
        int r2 = futureTask.get();//callable 的计算值（比如计算量特别大），如果没有计算完，会阻塞
        int r3 = futureTask.get();//callable 的计算值（比如计算量特别大），如果没有计算完，会阻塞
        System.out.println("++++++++"+(r1+r2));    //计算结果
    }
}
