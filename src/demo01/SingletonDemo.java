package demo01;

public class SingletonDemo {

    private static volatile SingletonDemo instance = null;

    /**
     * synchronized (SingletonDemo.class) {
     * DCL 双端加锁机制，并不一定安全，原因有指令重拍的存在，加入volatile可以禁止指令重排；
     *
     * 原因在于某个线程执行到第一次检测，读取到的 instance 不为 null 时，instance 的引用对象可能没有完成初始化；
     * instance = new SingletonDemo(); 可以分为3个步骤：
     * memory = allocate(); //1、分配对象内存空间
     * instance(memory);    //2、初始化对象
     * instance = memory;   //3、设置 instance 指向刚分配的内存地址，此时 instance!=null
     *
     * 2、3步骤 不存在数据依赖关系，所以可以 重排优化；
     * memory = allocate(); //1、分配对象内存空间
     * instance = memory;   //3、设置 instance 指向刚分配的内存地址，此时 instance!=null ，对象没有初始化完成！！！
     * nstance(memory);    //2、初始化对象
     */
    public static SingletonDemo getInstance() {
        if (instance == null) {
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }
    public static void main(String[] args) {

    }
}
