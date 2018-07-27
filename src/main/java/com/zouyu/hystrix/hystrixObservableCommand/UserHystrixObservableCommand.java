package com.zouyu.hystrix.hystrixObservableCommand;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import com.zouyu.hystrix.hystrixObservableCommand.pojo.User;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

/**
 * HystrixObservableCommand使用示例 
 * 只需要集成HystrixObservableCommand类，并覆写construct方法即可 
 * @author Administrator 
 * 
 */
public class UserHystrixObservableCommand extends HystrixObservableCommand<User> {
    private List<User> users = new ArrayList<User>();

    public UserHystrixObservableCommand(int[] ids) {
        super(HystrixCommandGroupKey.Factory.asKey("usercommand"));// 调用父类构造方法
        for(int x:ids){
            System.out.println(x);
            User user = new User(x,"zouyu"+x,"address"+x,x+10);
            users.add(user);
        }
    }

    protected Observable<User> construct() {
        System.out.println("construct! thread:" + Thread.currentThread().getName());
        return Observable.create(new Observable.OnSubscribe<User>(){
            public void call(Subscriber<? super User> subscriber) {
                try {// 写业务逻辑，注意try-catch
                    if (!subscriber.isUnsubscribed()) {
                        for (User u : users) {
//                            CloseableHttpClient client = HttpClients.createDefault();
//                            HttpGet get = new HttpGet("http://localhost:7901/user/" + id);
//                            CloseableHttpResponse response = client.execute(get);
//                            HttpEntity entity = response.getEntity();
//                            String body = EntityUtils.toString(entity);
//                            ObjectMapper mapper = ObjectMapperInstance.getInstance();
//                            User u = mapper.readValue(body, User.class);
//                          TimeUnit.SECONDS.sleep(3);
                            subscriber.onNext(u);
                        }
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    /**
     * fallback方法的写法，覆写resumeWithFallback方法
     * 当调用出现异常时，会调用该降级方法
     */
    @Override
    public Observable<User> resumeWithFallback() {
        return Observable.create(new Observable.OnSubscribe<User>() {
            public void call(Subscriber<? super User> observer) {
                try {
                    if (!observer.isUnsubscribed()) {
                        User u = new User();
                        u.setUsername("刘先生");
                        u.setId(100);
                        observer.onNext(u);
                        observer.onCompleted();
                    }
                } catch (Exception e) {
                    observer.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }
}