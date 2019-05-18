package com.kimiffy.cn.biubiu.utils.event;

/**
 * Description: EventBus 事件识别码 每一个事件需要有唯一对应的识别码,方便区分不同类型的事件
 * Created by kimiffy on 2019/2/13.
 */

public class EventCode {

    public static final int TEST_CODE = -1;
    public static final int COLLECT_ARTICLE_SUCCESS = 1;//收藏文章成功
    public static final int CANCEL_COLLECT_ARTICLE_SUCCESS = 2;//取消收藏文章

}
