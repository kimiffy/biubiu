package com.kimiffy.cn.biubiu.ui.home;

import android.text.TextUtils;

import com.kimiffy.cn.biubiu.base.BaseBean;
import com.kimiffy.cn.biubiu.base.BasePresenter;
import com.kimiffy.cn.biubiu.bean.ArticleBean;
import com.kimiffy.cn.biubiu.bean.BannerBean;
import com.kimiffy.cn.biubiu.bean.HotWordBean;
import com.kimiffy.cn.biubiu.bean.UserBean;
import com.kimiffy.cn.biubiu.constant.Config;
import com.kimiffy.cn.biubiu.constant.Key;
import com.kimiffy.cn.biubiu.database.model.LoginInfo;
import com.kimiffy.cn.biubiu.http.callback.BaseObserver;
import com.kimiffy.cn.biubiu.http.exception.ErrorType;
import com.kimiffy.cn.biubiu.utils.GsonUtil;
import com.kimiffy.cn.biubiu.utils.SpUtil;
import com.kimiffy.cn.biubiu.utils.UserUtil;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Description:首页控制层
 * Created by kimiffy on 2019/4/23.
 */

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private boolean isFirstTimeLoad = true;
    private boolean isRefresh;
    private int currentPage;

    HomePresenter(HomeContract.View view) {
        mView = view;
    }

    @Override
    public void firstFresh() {
        LoginInfo loginInfo = UserUtil.getLoginInfo();
        if (null != loginInfo) {
            final String username = loginInfo.username;
            final String password = loginInfo.password;
            Disposable disposable = Observable.timer(Config.LOAD_DELAY_TIME_2, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            autoLogin(username, password);
                        }
                    });
            compositeDisposable.add(disposable);

        } else {
            Disposable disposable = Observable.timer(Config.LOAD_DELAY_TIME, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            refresh();
                        }
                    });
            compositeDisposable.add(disposable);
        }
    }


    @Override
    public void refresh() {
        isRefresh = true;
        currentPage = 0;
        getArticleList(currentPage);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        currentPage++;
        getArticleList(currentPage);
    }

    @Override
    public void getBanner() {
        addDisposable(mApiService.getBannerList(), new BaseObserver<BaseBean<List<BannerBean>>>() {
            @Override
            public void onSuccess(BaseBean<List<BannerBean>> bean) {
                List<BannerBean> data = bean.data;
                String bannerString = GsonUtil.toJson(data);
                SpUtil.putString(Key.PREF_BANNER_LIST, bannerString);
                mView.getBannerSuccess(data);
            }

            @Override
            public void onFailure(String msg, ErrorType errorType) {
                String json = SpUtil.getString(Key.PREF_BANNER_LIST, "");
                List<BannerBean> list = GsonUtil.toList(json, BannerBean.class);
                if (null != list && !list.isEmpty()) {
                    mView.getBannerSuccess(list);
                } else {
                    mView.getBannerFail(msg);
                }
            }
        });

    }

    @Override
    public void getArticleList(int page) {

        addDisposable(mApiService.getArticleList(page), new BaseObserver<BaseBean<ArticleBean>>() {
            @Override
            public void onSuccess(BaseBean<ArticleBean> bean) {
                List<ArticleBean.DatasBean> datas = bean.data.getDatas();
                if (null != datas && !datas.isEmpty()) {
                    isFirstTimeLoad = false;
                    mView.showContent();
                    mView.getArticleListSuccess(datas, isRefresh);
                } else {
                    if (isFirstTimeLoad) {
                        mView.showDataEmpty();
                    } else {
                        mView.noMoreData();
                    }
                }
                if (isRefresh) {
                    mView.stopRefresh();
                }
            }

            @Override
            public void onFailure(String msg, ErrorType errorType) {
                if (isFirstTimeLoad) {
                    if (errorType.ordinal() == 0) {
                        mView.showNetError();
                    } else {
                        mView.showDataError();
                    }
                }
                mView.getArticleListFail(msg);
                if (isRefresh) {
                    mView.stopRefresh();
                }
            }
        });

    }

    /**
     * 收藏
     *
     * @param id       文章id
     * @param position 列表position
     */
    @Override
    public void doCollect(int id, final int position) {
        addDisposable(mApiService.collectArticle(id), new BaseObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean bean) {
                mView.collectSuccess(position);
            }

            @Override
            public void onFailure(String msg, ErrorType errorType) {
                mView.collectFail(position, msg);
            }
        });

    }

    @Override
    public void unCollect(int id, final int position) {
        addDisposable(mApiService.unCollectArticle(id), new BaseObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean bean) {
                mView.unCollectSuccess(position);
            }

            @Override
            public void onFailure(String msg, ErrorType errorType) {
                mView.unCollectFail(position, msg);
            }
        });
    }

    @Override
    public void getHotWord() {
        addDisposable(mApiService.getHotWordBean(), new BaseObserver<BaseBean<List<HotWordBean>>>() {
            @Override
            public void onSuccess(BaseBean<List<HotWordBean>> bean) {
                List<HotWordBean> data = bean.data;
                if (null != data && !data.isEmpty()) {
                    int size = data.size();
                    int random = new Random().nextInt(size);
                    String hotWord = data.get(random).getName();
                    if (!TextUtils.isEmpty(hotWord)) {
                        mView.getHotWordSuccess(hotWord);
                    }
                    String hotString = GsonUtil.toJson(data);
                    SpUtil.putString(Key.PREF_HOT_WORD_LIST, hotString);
                }
            }

            @Override
            public void onFailure(String msg, ErrorType errorType) {

            }
        });
    }


    /**
     * 自动登录
     *
     * @param username 账号
     * @param password 密码
     */
    private void autoLogin(String username, final String password) {
        addDisposable(mApiService.login(username, password), new BaseObserver<BaseBean<UserBean>>() {
            @Override
            public void onSuccess(BaseBean<UserBean> bean) {
                UserBean userBean = bean.data;
                UserUtil.handleLoginInfo(userBean, password);
                refresh();
            }

            @Override
            public void onFailure(String msg, ErrorType errorType) {
                refresh();
            }
        });
    }

}
