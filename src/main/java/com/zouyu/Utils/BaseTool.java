package com.zouyu.Utils;

import java.text.SimpleDateFormat;
import java.util.*;

public class BaseTool {
	
	public static void main(String[] args) {

		Map<String,Integer> map = new HashMap<>();
//		map.put("a",1);
//		map.put("b",2);
//		map.put("c",3);
		System.out.println(map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue)).limit(3).findFirst().get().getKey());
	}

	private static void printMap(Map map){
		map.keySet().forEach(o -> {
			System.out.println(o + ":"+ map.get(o));
		});
	}

	private static void longToDateToString(long tar){
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date date = new Date(tar);  
        String str = sdf.format(date);  
        System.out.println(str);
}
	
	private static void printBinaryNum(int n){
	    String num = Integer.toBinaryString(n);
	    if(num.length() == 32){
	        System.out.println(num);
	    }else{
	        StringBuilder sb = new StringBuilder("");
	        for(int i =0;i < 32 - num.length(); i ++){
	        sb.append("0");
	        }
	        System.out.println(sb.toString() + num);
	    }
	}
	
	/**
	 * 返回当前的时间戳
	 * 
	 * @return 时间戳
	 */
	public static String getCurrentTimeStamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
	
	public static void printArr(int[][] x){
		for(int i=0;i<x.length;i++){
			for(int j=0;j<x[0].length;j++){
				System.out.print(x[i][j]+" ");
			}
			System.out.println();
		}
	}
}
