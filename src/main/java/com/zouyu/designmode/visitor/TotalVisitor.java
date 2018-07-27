package com.zouyu.designmode.visitor;

/**
 * Created by zouy-c on 2017/12/22.
 */
public class TotalVisitor implements ITotalVisitor {
    // 部门经理的工资系数是 5
    private final static int MANAGER_COEFFICIENT = 5;
    // 员工的工资系数是 2
    private final static int COMMONEMPLOYEE_COEFFICIENT = 2;
    // 普通员工的工资总和
    private int commonTotalSalary = 0;
    // 部门经理的工资总和
    private int managerTotalSalary =0;
    public int getTotalSalary() {
        return (this.commonTotalSalary + this.managerTotalSalary);
    }
    // 访问普通员工，计算工资总额
    public void visit(CommonEmployee commonEmployee) {
        this.commonTotalSalary = this.commonTotalSalary +
                commonEmployee.getSalary()*COMMONEMPLOYEE_COEFFICIENT;
    }
    // 访问部门经理，计算工资总额
    public void visit(Manager manager) {
        this.managerTotalSalary = this.managerTotalSalary + manager.getSalary()
                *MANAGER_COEFFICIENT ;
    }
}
