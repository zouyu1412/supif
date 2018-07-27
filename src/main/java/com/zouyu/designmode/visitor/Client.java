package com.zouyu.designmode.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouy-c on 2017/12/22.
 */
public class Client {
    //TODO 可以更完善
    public static void main(String[] args) {
        // 展示报表访问者
        IShowVisitor showVisitor = new ShowVisitor();
        // 汇总报表的访问者
        ITotalVisitor totalVisitor = new TotalVisitor();
        for(Employee emp:mockEmployee()){
            emp.accept(showVisitor); // 接受展示报表访问者
            emp.accept(totalVisitor);// 接受汇总表访问者

            // 展示报表
            showVisitor.report();
            // 汇总报表
            System.out.println(totalVisitor.getTotalSalary());
        }
        // 展示报表
        showVisitor.report();
        // 汇总报表
        System.out.println(totalVisitor.getTotalSalary());
    }

    private static List<Employee> mockEmployee() {
        List<Employee> empList = new ArrayList<Employee>();
        //生产员工
        CommonEmployee haha = new CommonEmployee();
        haha.setJob("java开发");
        haha.setName("哈哈");
        haha.setSalary(9000);
        haha.setSex(Employee.MALE);

        CommonEmployee heihei = new CommonEmployee();
        heihei.setJob("java高级开发");
        heihei.setName("嘿嘿");
        heihei.setSalary(19000);
        heihei.setSex(Employee.MALE);

        Manager hoho = new Manager();
        hoho.setPerformance("爱拼才会赢");
        hoho.setName("霍霍");
        hoho.setSalary(25000);
        hoho.setSex(Employee.FEMALE);

        empList.add(haha);
        empList.add(heihei);
        empList.add(hoho);

        return empList;
    }
}
