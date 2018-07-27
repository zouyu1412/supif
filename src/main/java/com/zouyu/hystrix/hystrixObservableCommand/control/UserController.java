package com.zouyu.hystrix.hystrixObservableCommand.control;

import com.zouyu.hystrix.hystrixObservableCommand.UserHystrixObservableCommand;
import com.zouyu.hystrix.hystrixObservableCommand.pojo.User;
import rx.Observable;
import rx.Observer;

public class UserController {

//    @RequestMapping("/user/{id}")
//    public User getUser(@PathVariable("id") String id) {
//
//        HystrixCommand<User> userCommand = new UserHystrixCommond(id);
//        return userCommand.execute();
//    }

//    @RequestMapping("/userf/{id}")
//    public User getUserFallback(@PathVariable("id") String id) {
//
//        HystrixCommand<User> userCommand = new UserHystrixCommondWithFallback(id);
//        return userCommand.execute();
//    }

    public String getAll() {

        int[] ids = new int[]{1,2,3,4};

        UserHystrixObservableCommand observableCommand = new UserHystrixObservableCommand(ids);

        Observable<User> observable = observableCommand.observe();  //创建事件源

//        Observable用来向订阅者Subscriber对象 发布事件，Subscriber对象则在接收到事件后对
//        其进行处理， 而在这里所指的事件通常就是对依赖服务的调用。
//        一个Observable可以发出多个事件，直到结束或是发生异常。Observable对象每发出
//        一个事件，就会调用对应观察者Subscriber对象的onNext()方法。
//        每一个Observable的执行，最后一定会通过调用 Subscriber.onCompleted ()
//        或者Subscriber.onError()来结束该事件的操作流

        observable.subscribe(new Observer<User>() {   //匿名内部类创建订阅者，并进行订阅


            public void onCompleted() {
                System.out.println("聚合完了所有的查询请求!");
            }


            public void onError(Throwable t) {
                t.printStackTrace();
            }


            public void onNext(User user) {
                System.out.println(user);
            }
        });
        return "success";
    }

    public static void main(String[] args) {
        UserController uc = new UserController();
        uc.getAll();
    }
}