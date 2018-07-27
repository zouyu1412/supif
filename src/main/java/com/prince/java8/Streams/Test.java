package com.prince.java8.Streams;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
java.util.Stream表示了某一种元素的序列，在这些元素上可以进行各种操作。Stream 操作可以是中
间操作，也可以是完结操作。完 结操作会返回一个某种类型的值，而中间操作会返回流对象本身，并且你
可以通过多次调用 StringBuffer 的append方法一样）。Stream是在一个源的基础上创建出来的，
例如java.util.Collection中的list或者 set
（map不能作为Stream的源）。Stream 操作往往可以通过顺序或者并行两种方式来执行
 */

public class Test {
    public static void main(String[] args) {
        List<String> sList = new ArrayList<String>();
        sList.add("a1");
        sList.add("b2");
        sList.add("ab");
        sList.add("acc");
        sList.add("bc");
        sList.add("cf6");
        sList.add("a7");
        sList.add("17");
        sList.add("236");
        sList.add("589");
        sList.add("5898955");
        // Filter 流过滤,中间操作返回流本身
        sList.stream().filter(str -> str.length() == 3)
                .filter(str -> str.contains("c")).forEach(System.out::println);// acc
                                                                                // cf6
        // sorted 流排序,中间操作返回流本身
        sList.stream().filter(str -> str.contains("c"))
                .sorted((str1, str2) -> {
                    if (str1.length() == str2.length()) {
                        return 0;
                    } else if (str1.length() > str2.length()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }).forEach(System.out::println);// bc acc cf6
        // map 流转换对象,中间操作返回流本身
        sList.stream().filter(str -> str.matches("\\d+"))
                .map(str -> Integer.parseInt(str)).forEach(System.out::println);// 12
                                                                                // 236
                                                                                // 589
        // match 流匹配,终结操作
        System.out.println(sList.stream().allMatch(str -> str.length() == 3));// false
        System.out.println(sList.stream().anyMatch(str -> str.length() > 5));// true
        // count 返回当前流数量,终结操作
        long l = sList.stream().filter(str -> str.length() > 3).count();
        System.out.println(l);// 1
        // reduce 减少数量合并流对象，终结操作
        Optional<String> reOptional = sList.stream()
                .filter(str -> str.length() == 3)
                .reduce((str, str2) -> str + "-->" + str2);
        reOptional.ifPresent(System.out::println);// acc-->cf6-->236-->589
        // parallelStream 并行排序，效率更快
        Optional<String> reOptiona = sList.parallelStream()
                .filter(str -> str.length() == 3)
                .reduce((str, str2) -> str + "-->" + str2);
        reOptiona.ifPresent(System.out::println);// acc-->cf6-->236-->589
    }

}