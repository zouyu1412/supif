package com.ML;

import net.sf.javaml.core.*;
import net.sf.javaml.tools.InstanceTools;

import java.io.IOException;

public class TutorialDataset {
 
   public static void main(String[]args) throws IOException {
	    //dataset
	    //创建一个空的dataset，并随机赋值
        Dataset data = new DefaultDataset();
        for (int i = 0; i < 20; i++) {
            Instance tmpInstance = InstanceTools.randomGaussianInstance(3);
            tmpInstance.setClassValue("标记");
            data.add(tmpInstance);
        }//创建一个5行3列的矩阵
       for(int i=0;i<20;i++) {
           Instance instance = data.instance(i);
           System.out.println(instance); //打印dataset的第一行

       }
        /*案例2*/
        //从文件中导入形成一个dataset，前4列是特征值，最后1列是标记，列分隔符是逗号
//        Dataset dataFile = FileHandler.loadDataset(new File("D:\\tmp\\iris.data"), 4, ",");
//        for(Instance inst:dataFile){
//        	System.out.println(inst.classValue());//显示标记
//        	System.out.println(inst.values());//显示特征值
//        }
//        //instance
        double[] values = new double[] { 0.1, 2, 3 };/* values of the attributes. */
        Instance instance = new DenseInstance(values,"标记");
//        instance.setClassValue("标记");
        data.add(instance);
        System.out.println("Instance with only values set: ");
        System.out.println(instance);

//        /* Create instance with 10 attributes */
        Instance instancesparse = new SparseInstance();
        /* Set the values for particular attributes */
        instancesparse.put(1, 1.0);
        instancesparse.put(2, 2.0);
        instancesparse.put(3, 4.0);
        instancesparse.setClassValue("标记");
        System.out.println(instancesparse);
   }	
}

