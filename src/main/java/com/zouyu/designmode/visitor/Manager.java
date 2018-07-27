package com.zouyu.designmode.visitor;

/**
 * Created by zouy-c on 2017/12/22.
 */
public class Manager extends Employee {
    //业绩很重要
    private String performance;

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
