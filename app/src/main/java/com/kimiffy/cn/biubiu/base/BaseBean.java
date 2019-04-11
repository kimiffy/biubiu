package com.kimiffy.cn.biubiu.base;

import java.io.Serializable;

/**
 * Description:请求数据包装类
 * Created by kimiffy on 2019/3/12.
 */

public class BaseBean<T> implements Serializable {

    // TODO: 2019/3/12 需根据服务器返回数据封装这个包装类
    /**
     * 自定义错误码
     */
    public int errorCode;
    /**
     * 消息提示
     */
    public String errorMsg;

    /**
     * 泛型实体类
     */
    public T data;
}
