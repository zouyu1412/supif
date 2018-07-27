package com.zouyu.designmode.composite;

/**
 * 组合模式  公司人员抽象类
 * Created by zouy-c on 2017/12/28.
 */
@SuppressWarnings("all")
public abstract class Corp {
    //名字
    private String name = "";
    //岗位
    private String position = "";
    //薪水
    private int salary = 0;
    //父节点
    private Corp parent = null;

    public Corp(String name, String position, int salary) {
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public String getInfo(){
        String info = "姓名："+this.name+"\t职位："+this.position+"\t薪水："+this.salary;
        return info;
    }

    public Corp getParent() {
        return parent;
    }

    public void setParent(Corp parent) {
        this.parent = parent;
    }
}
