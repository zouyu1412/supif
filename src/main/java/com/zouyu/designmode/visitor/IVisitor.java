package com.zouyu.designmode.visitor;

/**
 * 访问者，我要去访问人家的数据了
 * Created by zouy-c on 2017/12/22.
 */
public interface IVisitor {
    //首先定义我可以访问的普通员工
    public void visit(CommonEmployee commonEmployee);

    //其次定义 我还可以访问的部门经理
    public void visit(Manager manager);

}
