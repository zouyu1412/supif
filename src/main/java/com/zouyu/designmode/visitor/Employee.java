package com.zouyu.designmode.visitor;

/**
 * Created by zouy-c on 2017/12/22.
 */
public abstract class Employee {
    public final static int MALE = 0; //0代表男性
    public final static int FEMALE = 1; //1代表女性

    private String name;

    private int salary;

    private int sex;

    //我允许一个访问者过来访问
    public abstract void accept(IVisitor visitor);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
