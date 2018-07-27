package com.prince.springevent;

import org.springframework.context.ApplicationEvent;

/**
 * Created by zouy-c on 2018/1/2.
 */
public class MyApplicationEvent extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MyApplicationEvent(Object source) {
        super(source);
    }
}
