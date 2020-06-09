package com.zouyu.examination;

import java.util.Arrays;

/**
 * @Author:zouyu
 * @Date:2020/5/21 20:39
 */
public class HeadSortDemo {

    public static void main(String[] args) {
        int[] ar = new int[]{1,4,-1,5,0,2,111,-114,43};
        sort(ar);
        System.out.println(Arrays.toString(ar));
    }

    public static void sort(int[] arr){
        for(int i=arr.length/2-1;i>=0;i--){
            adjust(arr, i, arr.length);
        }
        for(int i=arr.length-1;i>0;i--){
            arr[0] = arr[0]+arr[i] - (arr[i]=arr[0]);
            adjust(arr, 0, i);
        }
    }

    private static void adjust(int[] arr, int i, int length) {
        int tem = arr[i];
        for(int k=i*2+1;k<length;k=k*2+1){
            if(k+1<length && arr[k] <arr[k+1]){
                k++;
            }
            if(arr[k]>tem){
                arr[i] = arr[k];
                i = k;
            }else{
                break;
            }
        }
        arr[i] = tem;
    }


}
