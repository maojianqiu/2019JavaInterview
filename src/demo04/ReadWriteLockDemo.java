package demo04;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyCache
{
    private volatile Map<String,Object> map = new HashMap();
    //--- 加入读写锁之后
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void put(String k,Object v)
    {
        rwLock.writeLock().lock();
        System.out.println(Thread.currentThread().getName()+"正在写入"+k);
        try {
            TimeUnit.SECONDS.sleep(3);
            map.put(k,v);
            System.out.println(Thread.currentThread().getName()+"写入完成");
        } catch (Exception e) { e.printStackTrace(); }
        finally {
            rwLock.writeLock().unlock();
        }
    }

    public void get(String k)
    {
        rwLock.readLock().lock();
        System.out.println(Thread.currentThread().getName()+"正在读取"+k);
        try {
            TimeUnit.SECONDS.sleep(3);
            map.get(k);
            System.out.println(Thread.currentThread().getName()+"读取完成");
        } catch (Exception e) { e.printStackTrace(); }
        finally {
            rwLock.readLock().unlock();
        }
    }

}
/**
 *
 * 读 - 读能共存
 * 读 - 写不能共存
 * 写 - 读不能共存
 *
 * 写 - 原子+独占，整个过程必须是完整的统一体
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 0; i < 5; i++) {
            final int tempInt = i;      //由于 lambda 表达式，所以需要 final
            new Thread(()->{
                myCache.put(tempInt+"",tempInt+"");
            },String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            final int tempInt = i;      //由于 lambda 表达式，所以需要 final
            new Thread(()->{
                myCache.get(tempInt+"");
            },String.valueOf(i)).start();
        }
    }
}
