package com.zouyu.designmode.visitor;

/**
 * Created by zouy-c on 2017/12/22.
 */
public interface ITotalVisitor extends IVisitor{
    //子接口  定义具体的访问者的任务和责任  统计所有员工工资总和
    public int getTotalSalary();
}
