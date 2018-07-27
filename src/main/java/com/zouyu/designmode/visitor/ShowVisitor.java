package com.zouyu.designmode.visitor;

/**
 * Created by zouy-c on 2017/12/22.
 */
public class ShowVisitor implements IShowVisitor {

    private String info = "";

    //打印出报表
    public void report() {
        System.out.println(this.info);
    }

    public void visit(CommonEmployee commonEmployee) {
        this.info = getCommonEmployee(commonEmployee);
    }

    public void visit(Manager manager) {
        this.info = getManagerInfo(manager);
    }
    //组装出基本信息
    private String getBasicInfo(Employee employee){
        String info = "姓名:"+employee.getName()+"\t";
        info+= "性别:"+(employee.getSex()==Employee.FEMALE?"女":"男")+"\t";
        info+="薪水:"+employee.getSalary()+"\t";
        return info;
    }

    //组装出部门经理的信息
    private String getManagerInfo(Manager manager){
        String basicInfo = this.getBasicInfo(manager);
        String otherInfo = "业绩:"+manager.getPerformance()+"\t";
        return basicInfo+otherInfo;
    }

    //组装普通员工的信息
    private String getCommonEmployee(CommonEmployee commonEmployee){
        String basicInfo = this.getBasicInfo(commonEmployee);
        String otherInfo = "工作:"+commonEmployee.getJob()+"\t";
        return basicInfo+otherInfo;
    }

}
