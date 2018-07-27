package com.prince.java8.Suppliers;


//Suppliers接口产生一个给定类型的结果，需要类有无参构造函数

import java.util.function.Supplier;

class Person {
    private String name;
    private int age;

    public Person() {// 必须有无参构造函数
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "名字：" + name + "年龄：" + age;
    }
}

public class Test {
    public static void main(String[] args) {
        Supplier<Person> pSupplier = Person::new;
        Person person = pSupplier.get();

        System.out.println(person.toString());
    }
}