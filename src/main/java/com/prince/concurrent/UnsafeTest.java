//package com.prince.concurrent;
//
//import sun.misc.Unsafe;
//
//import java.lang.reflect.Field;
//import java.util.Arrays;
//
///**
// * Created by zouy-c on 2018/6/25.
// */
//public class UnsafeTest {
//
//    private static long byteArrayBaseOffset;
//
//    public static void main(String[] args)throws NoSuchFieldException,IllegalAccessException{
//        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
//        theUnsafe.setAccessible(true);
//        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
//        System.out.println(unsafe);
//        byte[] bytes = new byte[10];
//        byte[] bytes1 = new byte[12];
//        System.out.println(Arrays.toString(bytes));
//        byteArrayBaseOffset = unsafe.arrayBaseOffset(byte[].class);
//        unsafe.putByte(bytes,byteArrayBaseOffset,(byte)1);
//        unsafe.putByte(bytes,byteArrayBaseOffset+5,(byte)5);
//
//        unsafe.putByte(bytes1,byteArrayBaseOffset,(byte)1);
//        unsafe.putByte(bytes1,byteArrayBaseOffset+5,(byte)5);
//        System.out.println(Arrays.toString(bytes));
//        System.out.println(Arrays.toString(bytes1));
//    }
//
//}
