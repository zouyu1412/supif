package com.ML;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;

import java.io.File;
 
public class Main {
    public static void main(String[] args)throws Exception {
        Dataset data = FileHandler.loadDataset(new File("C:\\Users\\yuzou\\Desktop\\tem\\jdk相关\\Titanic\\train.csv"), -1, ",");
        Classifier knn = new KNearestNeighbors(5);
        knn.buildClassifier(data);
//        Dataset dataForClassification = FileHandler.loadDataset(new File("C:\\Users\\yuzou\\Desktop\\tem\\jdk相关\\Titanic\\train.csv"), -1, ",");
//        int correct = 0, wrong = 0;
//        for (Instance inst : dataForClassification) {
//            Object predictedClassValue = knn.classify(inst);
//            Object realClassValue = inst.classValue();
//            if (predictedClassValue != null && predictedClassValue.equals(realClassValue))
//                correct++;
//            else
//                wrong++;
//        }
//        System.out.println("Correct predictions  " + correct);
//        System.out.println("Wrong predictions " + wrong);

        Clusterer km = new KMeans();
        Dataset[] cluster = km.cluster(data);
        System.out.println("d");
    }
}