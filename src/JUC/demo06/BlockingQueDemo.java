package JUC.demo06;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 1 队列
 *
 * 2 阻塞队列
 *      2.1 阻塞队列有没有好的一面
 *
 *      2.2 不得不阻塞，你如何管理？
 */
public class BlockingQueDemo {
    public static void main(String[] args) throws InterruptedException {
//        List list = null;
        /**
         * 超时：offer（e,time,unit） \ poll（e,time,unit） \ 不可用
         */
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue(3);
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a", 2L, TimeUnit.SECONDS)); // 等2秒，如果还是不能插入则放弃。
    }


    public void throwException(){
        /**
         * 抛出异常：add \ remove \ element
         */
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue(3);
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
//        System.out.println(blockingQueue.add("a")); //抛异常的添加  -- java.lang.IllegalStateException: Queue full

        System.out.println(blockingQueue.element());    //查看当前元素，为空抛异常  -- java.util.NoSuchElementException
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove()); ////抛异常的取出 --  java.util.NoSuchElementException
    }
    public void returnSpecial(){
        /**
         * 返回特殊值：offer \ poll \ peek
         */
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue(3);
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
//        System.out.println(blockingQueue.offer("c")); // 失败 直接返回 false

        System.out.println(blockingQueue.peek()); // 查看

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());  // 失败 返回 null
    }
    public void blocking() throws InterruptedException {
        /**
         * 阻塞：put \ take \ 不可用
         */
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue(3);
        blockingQueue.put("q");
        blockingQueue.put("q");
        blockingQueue.put("q");
//        blockingQueue.put("q");// 当前线程会阻塞

        blockingQueue.take();
        blockingQueue.take();
        blockingQueue.take();
//        blockingQueue.take(); //// 当前线程会阻塞
    }
}
