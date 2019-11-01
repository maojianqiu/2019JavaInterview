package demo03;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 集合不安全的问题
 */
public class ContainerNotSafe {
    public static void main(String[] args) {

        Map<String,String> map = new ConcurrentHashMap<>();//new Collections.synchronizedMap();//new HashMap<>();

        for (int i = 0; i < 30;i++){
            new Thread(()->{
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0,8));
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }

    public static void setNoSafe(){
        /**
         * HashSet 的底层是 HashMap , add 中调用的 map.put，K是set放进去的值，而 V 全部是默认的一个值 private static final Object PRESENT = new Object();
         *  public boolean add(E e) {
         *         return map.put(e, PRESENT)==null;
         *  }
         */
        Set<String> set = new CopyOnWriteArraySet<>(); //Collections.// new HashSet();

        for (int i = 0; i < 30;i++){
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(set);
            },String.valueOf(i)).start();
        }
    }

    public static void listNoSafe(){
        /**
         * 集合不安全的问题
         *
         * 1、故障现象
         *      java.util.ConcurrentModificationException
         *
         * 2、导致原因
         *      并发争抢修改导致，参考花名册签名情况：
         *      一个人正在写入，另外一个同学过来抢夺，导致数据不一致异常，并发修改异常。
         *
         * 3、解决方案
         *      3.1 new Vector();
         *      3.2 Collections.synchronizedList(new ArrayList<>());     Map\Set
         *      3.3 new CopyOnWriteArrayList();   -------j.u.c
         *          写时复制，读写分离
         *
         * 4、优化建议（同样的错误不犯 2 次）
         *
         *
         *
         * 笔记：读写分离，可以并发读不用加锁，并发写加锁，看  --->  3.2 juc.add代码分析.png
         *
         *  public boolean add(E e) {
         *         final ReentrantLock lock = this.lock;
         *         lock.lock();
         *         try {
         *             Object[] elements = getArray();
         *             int len = elements.length;
         *             Object[] newElements = Arrays.copyOf(elements, len + 1);
         *             newElements[len] = e;
         *             setArray(newElements);
         *             return true;
         *         } finally {
         *             lock.unlock();
         *         }
         *     }
         */
//        List<String> list = new ArrayList<>();
//        List<String> list = Collections.synchronizedList(new ArrayList());
        List<String> list = new CopyOnWriteArrayList();

        for (int i = 0; i < 30;i++){
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
        //java.util.ConcurrentModificationException
    }

}
