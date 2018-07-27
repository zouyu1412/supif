package com.zouyu.designmode.iterator;

import java.util.ArrayList;

/**
 * Created by zouy-c on 2017/12/20.
 */
public class ProjectIterator implements IProjectIterator {

    private ArrayList<IProject> projectList = new ArrayList<IProject>();

    private int currentItem = 0;

    public ProjectIterator(ArrayList<IProject> projectList) {
        this.projectList = projectList;
    }

    public boolean hasNext() {
        boolean b = true;
        if(this.currentItem>=projectList.size() || this.projectList.get(this.currentItem)==null){
            b = false;
        }
        return b;
    }

    public Object next() {
        return (IProject)this.projectList.get(this.currentItem++);
    }

    public void remove() {
        //TODO
    }
}
