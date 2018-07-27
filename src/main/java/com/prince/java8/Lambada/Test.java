package com.prince.java8.Lambada;


/*
每一个lambda都能通过一个特定的接口，与一个给定的类型进行匹配，即所谓函数式接口必须要有且
仅有一个抽象方法声明，每个与之对应的lambda表达式必须与抽象方法声明相匹配(即传入的参数类型
，和返回类型相一致)。由于默认方法不是抽象的，所以在函数式接口里你可以添加任意的默认方法。
任意一个包含一个抽象方法的接口，都可以做成lambda表达式，为了让你定义的接口满足要求
，应当在接口前面加上@FunctionalInterface，编译器便会注意到此标注，如果接口中定义了第
二个抽象方法，编译器会报错。
 */

// 标注此interface为函数式接口,最好写上
@FunctionalInterface
interface A {
    public String fLambada(String str);// 函数式接口有且仅有一个抽象方法

    default public void sayInt(int i) {// 函数式接口可以声明多个默认方法
        System.out.println("sayInt--->" + i);
    }

    default public void sayString(String str) {
        System.out.println("sayInt--->" + str);
    }
}

class B {
    public void invoke(A a) {
        System.out.println(a.fLambada("senssic"));
    }
}

public class Test {
    public static void main(String[] args) {
        B b = new B();
        // 以下三中lambada均可，都是通过lambada来省略代码直接实现接口抽象方法
        b.invoke((String str) -> {
            System.out.println(str);// 使用大括号写实现代码
            return "hello " + str;
        });
        b.invoke((String str) -> "hello " + str);// 直接返回
        b.invoke(str -> "hello " + str);// 省略类型直接返回
    }
}