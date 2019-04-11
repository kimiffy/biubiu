package com.kimiffy.cn.biubiu.utils.event;

/**
 * Description: 事件包装类 所有的事件需要加一个包装,方便区分不同事件类型
 * Created by kimiffy on 2019/2/13.
 */

public class Event<T> {

    //识别码
    private int code;
    //具体的数据
    private T data;

    public Event(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getEvent() {
        return data;
    }

    public void setEvent(T event) {
        this.data = event;
    }

}
