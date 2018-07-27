package com.JVM;

/**
 * Created by zouy-c on 2018/5/30.
 */
public class Test {
    public static void main(String[] args) {
        {
            byte[] placeholder = new byte[64 * 1024 * 1024];
        }
        //打开下面注释 placeholder被回收 局部变量表被清空
        //int a=0;
        System.gc();
    }

    //vm options: -verbose:gc
}
