package demo01;

public class ReSortSeqDemo {
    int a=0;
    boolean flag=false;

    public void method1(){
        a=5;        //1
        flag=true;  //2
    }

    public void method2(){
        if (flag){
            a=a+5;     //3
            System.out.println("");
        }
    }
}
