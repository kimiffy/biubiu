package com.kimiffy.cn.biubiu.ui.search;

import com.kimiffy.cn.biubiu.base.contract.IBaseView;
import com.kimiffy.cn.biubiu.base.contract.IPresenter;
import com.kimiffy.cn.biubiu.bean.HotWordBean;
import com.kimiffy.cn.biubiu.bean.SearchHistoryBean;

import java.util.List;

/**
 * Description:搜索页契约类
 * Created by kimiffy on 2019/5/28.
 */

public class SearchContract {


    public interface Presenter extends IPresenter<SearchContract.View> {
        void getHotWord();

        void getSearchHistory();

        void saveHistory(List<SearchHistoryBean> historyBeanList);

        void cleanHistory();
    }


    public interface View extends IBaseView {
        void getHotWordSuccess(List<HotWordBean> hotWordBeans);

        void getSearchHistorySuccess(List<SearchHistoryBean> historyBeans);

        void cleanHistorySuccess();

        void saveHistorySuccess(List<SearchHistoryBean> historyBeans);
    }


}
