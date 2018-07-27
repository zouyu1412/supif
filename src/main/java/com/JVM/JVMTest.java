package com.JVM;

/**
 * Created by zouy-c on 2018/4/16.
 */
public class JVMTest {
    public static void main(String[] args) {
        String str1 = new StringBuilder("计算机").append("哈哈").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }
}

// result  :    true  false
// intern实现不再像1.6那样把首次遇到的字符串实例复制到永久代中(返回的也是永久代中这个字符串实例的引用）
//      而是在常量池中记录首次出现的实例引用,不会再复制实例    所以第一个判断为true
//    因为“java”字符串在执行StringBuilder.toString()之前已经出现过，常量池已经有它的引用，不符合“首次出现”的原则
