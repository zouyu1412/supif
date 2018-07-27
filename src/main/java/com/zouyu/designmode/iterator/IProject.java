package com.zouyu.designmode.iterator;

/**
 * Created by zouy-c on 2017/12/20.
 */
public interface IProject {
    //添加项目
    public void add(String name,int num,int cost);

    //获取项目信息
    public String getProjectInfo();

    //获取迭代器
    public IProjectIterator iterator();
}
