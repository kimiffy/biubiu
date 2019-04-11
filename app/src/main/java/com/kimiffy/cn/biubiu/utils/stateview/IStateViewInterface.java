package com.kimiffy.cn.biubiu.utils.stateview;

import android.app.Activity;
import android.view.View;

/**
 * Description: 功能接口
 * Created by kimiffy on 2019/3/21.
 */

public interface IStateViewInterface extends IStateView {

    void setRootView(Activity activity);

    void setRootView(View view);

    void setDataErrorResId(int mDataErrorViewID);

    void setDataEmptyResId(int mDataEmptyViewID);

    void setNetErrorResId(int mNetErrorViewID);

    void setLoadingResId(int mLoadingViewID);

    View getCurrentStateView();

    int getCurrentCode();

    void setCurrentCode(int stateViewCode);

    View getStateViewByCode(int stateViewCode);

    void setRetryListener(View.OnClickListener listener);

    void clean();

}
