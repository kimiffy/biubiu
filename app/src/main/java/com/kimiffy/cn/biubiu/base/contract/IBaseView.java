package com.kimiffy.cn.biubiu.base.contract;

import com.kimiffy.cn.biubiu.utils.stateview.StateViewProxy;
import com.kimiffy.cn.biubiu.utils.stateview.IStateView;

/**
 * Description:
 * Created by kimiffy on 2019/3/11.
 */

public interface IBaseView  extends IStateView{

    /**
     * 获取状态布局
     * @return
     */
    StateViewProxy getStateView();

}
