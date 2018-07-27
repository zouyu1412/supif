package com.zouyu.designmode.iterator;

/**
 * Created by zouy-c on 2017/12/20.
 */
public class Client {
    public static void main(String[] args) {
        IProject project = new Project();

        for(int i=0;i<100;i++){
            project.add("第"+i+"个项目",i*4,i*10000);
        }

        IProjectIterator projectIterator = project.iterator();
        while(projectIterator.hasNext()){
            IProject p = (IProject)projectIterator.next();
            System.out.println(p.getProjectInfo());
        }
    }
}
