package com.zouyu.designmode.visitor;

/**
 * Created by zouy-c on 2017/12/22.
 */
public class CommonEmployee extends Employee {
    //工作内容
    private String job;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
