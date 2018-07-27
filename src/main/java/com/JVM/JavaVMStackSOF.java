package com.JVM;

/**
 * Created by zouy-c on 2018/4/16.
 */
public class JavaVMStackSOF {

    private int stackLength =1;

    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) throws Throwable{
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        }catch (Throwable e){
            System.out.println("stack length:"+ oom.stackLength);
            throw e;
        }
    }
}

// JVM arg:  -Xss128k

//    Exception in thread "main" java.lang.StackOverflowError
//        stack length:983
//        at com.JVM.JavaVMStackSOF.stackLeak(JavaVMStackSOF.java:11)
//        at com.JVM.JavaVMStackSOF.stackLeak(JavaVMStackSOF.java:12)
//        at com.JVM.JavaVMStackSOF.stackLeak(JavaVMStackSOF.java:12)
//        at com.JVM.JavaVMStackSOF.stackLeak(JavaVMStackSOF.java:12)   .............
