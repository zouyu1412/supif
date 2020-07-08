package com.kwai.test;

/**
 * @author zouyu <zouyu@kuaishou.com>
 * Created on 2020-06-28
 */
public class JITTest {

    /**
     * 内联分析  -XX:+PrintCompilation -XX:+UnlockDiagnosticVMOptions -XX:+PrintInlining
     * @param args
     */

    public static void main(String[] args) {

       JITTest jitTest = new JITTest();
       jitTest.test1();
       jitTest.test2();

    }

    private void test1(){
        NQueens1 nQueens1 = new NQueens1();
        for(int i=0;i<1000;i++){
            long s = System.currentTimeMillis();
            nQueens1.solveNQueens(8);
            long e = System.currentTimeMillis();
            System.out.println("test1 i:"+i+" cost time"+(e-s)+"ms");
        }
    }

    private void test2(){
        NQueens2 nQueens2 = new NQueens2();
        for(int i=0;i<1000;i++){
            long s = System.currentTimeMillis();
            nQueens2.solveNQueens(8);
            long e = System.currentTimeMillis();
            System.out.println("test2 i:"+i+" cost time"+(e-s)+"ms");
        }
    }

}
