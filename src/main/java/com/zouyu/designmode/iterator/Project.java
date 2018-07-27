package com.zouyu.designmode.iterator;

import java.util.ArrayList;

/**
 * Created by zouy-c on 2017/12/20.
 */

@SuppressWarnings("all")
public class Project implements IProject {

    private ArrayList<IProject> projectList = new ArrayList<IProject>();

    private String name ="";

    private int num = 0;

    private int cost = 0;

    public Project(){

    }

    public Project(String name, int num, int cost) {
        this.name = name;
        this.num = num;
        this.cost = cost;
    }

    public void add(String name, int num, int cost) {
        this.projectList.add(new Project(name,num,cost));
    }

    public String getProjectInfo() {
        String info = "项目名："+this.name+"\t项目人数："+this.num+"\t项目费用："+this.cost;
        return info;
    }

    public IProjectIterator iterator() {
        return new ProjectIterator(this.projectList);
    }
}
