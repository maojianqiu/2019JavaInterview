package demo01;

public class D1 {
    volatile int number=0;

    //原子性,number 前面添加volatile。volatile 并不保证原子性。可以加 synchronized
    void addplusplus(){
        this.number++;
    }
}
