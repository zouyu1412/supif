package com.prince.RPC;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RPCServer {
      
        private ExecutorService threadPool;
        private static final int DEFAULT_THREAD_NUM = 10;  
          
        public RPCServer(){  
            threadPool = Executors.newFixedThreadPool(DEFAULT_THREAD_NUM);
        }  
          
        public void register(Object service, int port){  
            try {  
                System.out.println("server starts...");  
                ServerSocket server = new ServerSocket(port);
                Socket socket = null;
                while((socket = server.accept()) != null){  
                    System.out.println("client connected...");  
                    threadPool.execute(new Processor(socket, service));  
                }
            } catch (IOException e) {
                e.printStackTrace();  
            }  
        }  
          
        class Processor implements Runnable{  
            Socket socket;  
            Object service;  
              
            public Processor(Socket socket, Object service){  
                this.socket = socket;  
                this.service = service;  
            }  
            public void process(){  
                  
            }  
            @Override  
            public void run() {  
                try {  
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    String methodName = in.readUTF();
                    System.out.println(methodName);
                    Class<?>[] parameterTypes = (Class<?>[]) in.readObject();
                    for(Class o:parameterTypes){
                        System.out.println(o.getName());
                    }
                    Object[] parameters = (Object[]) in.readObject();
                    for(Object o:parameters){
                        System.out.println(o.toString());
                    }
                    Method method = service.getClass().getMethod(methodName, parameterTypes);
                    try {  
                        Object result = method.invoke(service, parameters);  
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        out.writeObject(result);  
                    } catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException e) {
                        e.printStackTrace();  
                    }
                } catch (IOException|NoSuchMethodException|SecurityException|ClassNotFoundException e) {
                    e.printStackTrace();  
                }
            }  
        }  
    }  