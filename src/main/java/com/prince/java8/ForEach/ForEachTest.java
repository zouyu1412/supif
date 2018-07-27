package com.prince.java8.ForEach;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zouy-c on 2018/1/17.
 */
public class ForEachTest {
    public static void main(String[] args) {
        Map<String, Integer> items = new HashMap<>();
        items.put("A", 10);
        items.put("B", 20);
        items.put("C", 30);
        items.put("D", 40);
        items.put("E", 50);
        items.put("F", 60);
        items.forEach((k, v) -> {
            System.out.println("Item : " + k + " Count : " + v);
            if ("E".equals(k)) {
                System.out.println("Hello E");
            }
        });

        List<String> arrayList = new ArrayList<>();
        arrayList.add("A");
        arrayList.add("B");
        arrayList.add("C");
        arrayList.add("D");
        arrayList.add("E");
        arrayList.forEach(item -> System.out.println(item));

        arrayList.forEach(System.out::println);

        arrayList.forEach(item -> {
            if ("C".equals(item)) {
                System.out.println(item);
            }
        });

        arrayList.stream()
                .filter(s -> s.contains("B") || s.contains("C"))
                .forEach(System.out::println);

        arrayList.stream()
                .filter(s -> s.contains("E"))
                .findFirst().ifPresent(s -> System.out.println(s));


        List<String> arr = new ArrayList<>();
        System.out.println(arr.stream().filter(i->i.equals("dd")).collect(Collectors.toList()).isEmpty()+"  dasdsa");
        System.out.println(arr.isEmpty());
    }
}
