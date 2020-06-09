//package com.prince.thisescape;
//
//import java.util.List;
//
//public class ListenerRunnable implements Runnable {
//
//      private EventSource<EventListener> source;
//      public ListenerRunnable(EventSource<EventListener> source) {
//            this.source = source;
//      }
//      public void run() {
//            List<EventListener> listeners = null;
//
//            try {
//                  listeners = this.source.retrieveListeners();
//            } catch (InterruptedException e) {
//                  // TODO Auto-generated catch block
//                  e.printStackTrace();
//            }
//            for(EventListener listener : listeners) {
//                  listener.onEvent(new Object());  //执行内部类获取外部类的成员变量的方法
//            }
//      }
//  }