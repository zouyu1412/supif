package com.zouyu.designmode.visitor;

/**
 * Created by zouy-c on 2017/12/22.
 */
public interface IShowVisitor extends IVisitor{
    //子接口  定义具体的访问者的任务和责任  展示报表
    public void report();
}
