package com.zouyu.Utils;

import java.text.SimpleDateFormat;
import java.util.*;

public class BaseTool {

	static class Pair{
		int x;
		int y;

		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		@Override
		public String toString() {
			return "Pair{" +
					"x=" + x +
					", y=" + y +
					'}';
		}
	}

	public static void main(String[] args) {
		List<Long> autoHomeTrimIds = new ArrayList<>();
		autoHomeTrimIds.add(1l);
		autoHomeTrimIds.add(3l);
		autoHomeTrimIds.add(66l);
		String s = Arrays.toString(autoHomeTrimIds.toArray());
		System.out.println(s);

//		List<BigDecimal> list = new ArrayList<>();
//		list.add(new BigDecimal("1.3"));
//		list.add(new BigDecimal("1.2"));
//		list.add(new BigDecimal("1.4"));
//		BigDecimal bigDecimal = list.stream().min(BigDecimal::compareTo).get();
//		BigDecimal bigDecimal2 = list.stream().max(BigDecimal::compareTo).get();
//		BigDecimal subtract = bigDecimal.divide(bigDecimal2,3,2);
//		System.out.println(subtract);
//		System.out.println(bigDecimal);
//		System.out.println(bigDecimal2);
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
