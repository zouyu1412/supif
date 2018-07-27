package com.zouyu.javaweb;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnProcessThread implements Runnable {

    public boolean isStop = false;
    public boolean readOK = false;
    private static HttpURLConnection reqConnection;
    public Thread readingThread;
    private int readLen;
    private String msg = null;
    private String reqMethod;
    private byte[] data;

    static {
        try {
            URL reqUrl = new URL("http://www.baidu.com");
            // 建立URLConnection连接
            reqConnection = (HttpURLConnection) reqUrl.openConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * ReadThread constructor comment.
     */
    public HttpConnProcessThread(HttpURLConnection reqConnection, String msg, String reqMethod) {
        super();
        this.reqConnection = reqConnection;
        this.msg = msg;
        this.reqMethod = reqMethod;
    }

    public void run() {
        InputStream input = null;
        OutputStream output = null;
        try {
            //reqConnection.connect();
            reqConnection.setDoOutput(true);
            output = reqConnection.getOutputStream();
            if ("post".equalsIgnoreCase(reqMethod) && msg != null && msg.length() > 0) {
                System.out.println("hiahia");
                output.write(msg.getBytes());
                output.close();
                output = null;
            }
            System.out.println("haha");
            // 处理HTTP响应的返回状态信息
            int responseCode = reqConnection.getResponseCode();// 响应的代码if( responseCode != 200 )
            System.out.println("connect failed! responseCode = " + responseCode + " msg=" + reqConnection.getResponseMessage());
            //System.out.println("connect failed! responseCode = " + responseCode);
            System.out.println("haha");
            input = reqConnection.getInputStream();
            int len;
            byte[] buf = new byte[2048];
            readLen = 0;
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            System.out.println("haha");
            // 读取inputStream
            while (!isStop) {
                len = input.read(buf);
                if (len <= 0) {
                    this.readOK = true;
                    input.close();
                    data = outStream.toByteArray();
                    break;
                }
                outStream.write(buf, 0, len);
                readLen += len;
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reqConnection.disconnect();
                if (input != null)
                    input.close();
                if (output != null)
                    output.close();
                //唤醒线程，跳出等待
                wakeUp();
            } catch (Exception e) {
            }
        }
    }

    public String getMessage() {
        if (!readOK) //超时
        {
            return "";
        }
        if (readLen <= 0) {
            return "";
        }
        return new String(data, 0, readLen);
    }
    public void startUp() {
        this.readingThread = new Thread(this);
        readingThread.start();
    }
    //唤醒线程，不再等待
    private synchronized void wakeUp() {
        notifyAll();
    }

    public synchronized void waitForData(int timeout) {
        try {
            //指定超时时间，等待connection响应
            wait(timeout);
        } catch (Exception e) {
        }
        if (!readOK) {
            isStop = true;
            try {
                //中断线程
                if (readingThread.isAlive())
                    System.out.println("线程终端了？");
                    readingThread.interrupt();
            } catch (Exception e) {

            }
        }
    }

    public static void main(String[] args) {
        String msg = "";
        try {

            HttpConnProcessThread rec = new HttpConnProcessThread(reqConnection, msg, "post");
            rec.startUp();
            // 如果顺利连接到并读完数据，则跳出等待，否则等待超时
            rec.waitForData(1000);

            String retMessage = rec.getMessage();
            System.out.println(retMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}