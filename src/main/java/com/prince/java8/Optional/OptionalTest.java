package com.prince.java8.Optional;

import java.util.Optional;

/**
 * Created by zouy-c on 2018/2/10.
 */
public class OptionalTest {

    public static void main(String args[]){

        OptionalTest optionalTest = new OptionalTest();
        Integer value1 = null;
        Integer value2 = new Integer(10);
        Integer value3 = new Integer(100);
        String value4 = "zouyu";
        String value5 = null;

        // Optional.ofNullable - 允许传递为 null 参数
        Optional<Integer> a = Optional.ofNullable(value1);

        // Optional.of - 如果传递的参数是 null，抛出异常 NullPointerException
        Optional<Integer> b = Optional.of(value2);
        System.out.println(optionalTest.sum(a,b));

        Optional<Integer> c = Optional.of(value3);
        System.out.println(c.toString());

        System.out.println(c.filter(o->o<3).toString());

        c.filter(o->o>3).ifPresent(o-> System.out.println(o+value2));

        Optional<String> d = Optional.ofNullable(value4);
        Optional<String> e = Optional.ofNullable(value5);
        System.out.println(d.orElse("baba"));
        System.out.println(e.orElse("mama"));
        System.out.println(e.toString());

    }

    public Integer sum(Optional<Integer> a, Optional<Integer> b){

        // Optional.isPresent - 判断值是否存在

        System.out.println("第一个参数值存在: " + a.isPresent());
        System.out.println("第二个参数值存在: " + b.isPresent());

        int defaultVal = 0;
        // Optional.orElse - 如果值存在，返回它，否则返回默认值
        Integer value1 = a.orElse(defaultVal);
        //Integer value1 = a.orElseGet(()->defaultVal);

        //Optional.get - 获取值，值需要存在
        Integer value2 = b.get();
        return value1 + value2;
    }
}
