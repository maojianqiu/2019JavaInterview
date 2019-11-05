package JUC.demo09;


class HoldLockThread implements Runnable{
    private String lockA;
    private String lockB;

    public HoldLockThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println("自己持有："+ lockA + "尝试获取 另一个" + lockB);
            synchronized (lockB){
                System.out.println("获取 另一个" + lockA);
            }

        }
    }
}
public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new HoldLockThread(lockA,lockB),"AAA").start();
        new Thread(new HoldLockThread(lockB,lockA),"BBB").start();


        /**
         * Linux ps -ef|grep XXX
         * windows 下的Java运行程序，也有类似 ps 的查看进程的命令，但是目前我们需要查看的 java
         *  jps = java ps       jps -l
         *
         *  jstack 进程号
         */

    }
}
