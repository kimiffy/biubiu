package com.kimiffy.cn.biubiu.utils.event;

import org.greenrobot.eventbus.EventBus;

/**
 * Description: EventBus 工具类
 * Created by kimiffy on 2019/2/13.
 */

public class EventBusUtil {

    /**
     * 注册
     *
     * @param subscriber
     */
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    /**
     * 反注册
     *
     * @param subscriber
     */
    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    /**
     * 发送事件
     *
     * @param event
     */
    public static void post(Event event) {
        EventBus.getDefault().post(event);
    }

    /**
     * 发送黏性事件
     *
     * @param event
     */
    public static void postSticky(Event event) {
        EventBus.getDefault().postSticky(event);
    }


    /**
     * 移除一个黏性事件
     *
     * @param event
     * @return 事件是否移除
     */
    public static boolean removeSticky(Event event) {
        return EventBus.getDefault().removeStickyEvent(event);
    }

    /**
     * 移除所有黏性事件
     *
     */
    public static void removeAllStickyEvent() {
        EventBus.getDefault().removeAllStickyEvents();
    }

}
