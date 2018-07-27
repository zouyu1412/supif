package com.prince.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by zouy-c on 2018/7/13.
 */
public class FileChannelTest {

    public static void main(String[] args) throws Exception{
        System.out.println("进来了？");
        RandomAccessFile randomAccessFile = new RandomAccessFile("temmdsadm.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(64);

        int bytes = fileChannel.read(buf);
        while(bytes != -1){
            System.out.println("read: "+bytes);

            buf.flip();

            while(buf.hasRemaining()){
                System.out.print((char)buf.get());
            }
            buf.clear();
            bytes = fileChannel.read(buf);
        }
        randomAccessFile.close();


    }

}
