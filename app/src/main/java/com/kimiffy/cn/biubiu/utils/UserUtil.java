package com.kimiffy.cn.biubiu.utils;

import com.kimiffy.cn.biubiu.bean.CollectIdBean;
import com.kimiffy.cn.biubiu.bean.UserBean;
import com.kimiffy.cn.biubiu.database.model.LoginInfo;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Description:用户相关工具类
 * Created by kimiffy on 2019/3/6.
 */

public class UserUtil {

    private UserUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 用户是否登录了
     *
     * @return 登录状态
     */
    public static boolean isUserLogin() {
        List<LoginInfo> users = SQLite.select().
                from(LoginInfo.class).
                queryList();
        return !users.isEmpty();
    }

    /**
     * 获取用户登录信息
     *
     * @return LoginInfo
     */
    public static LoginInfo getLoginInfo() {
        List<LoginInfo> users = SQLite.select().
                from(LoginInfo.class).
                queryList();
        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }


    /**
     * 处理登录数据
     *
     * @param userBean 登录成功后返回的信息
     * @param psw 用户输入的密码
     */
    public  static void handleLoginInfo(UserBean userBean, String psw) {
        deleteLoginInfo();
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.id = userBean.getId();
        loginInfo.username = userBean.getUsername();
        loginInfo.password = psw;
        loginInfo.email = userBean.getEmail();
        loginInfo.icon = userBean.getIcon();
        loginInfo.type = userBean.getType();
        loginInfo.chapterTops = GsonUtil.toJson(userBean.getChapterTops());
        loginInfo.collectIds = GsonUtil.toJson(userBean.getCollectIds());
        loginInfo.save();
    }

    /**
     * 删除存贮的用户登录信息
     */
    public static void deleteLoginInfo() {
        List<LoginInfo> users = SQLite.select().
                from(LoginInfo.class).
                queryList();
        for (LoginInfo user : users) {
            user.delete();
        }
    }


    /**
     * 获取用户收藏文章ID列表
     *
     * @return List<CollectIdBean>
     */
    public static List<CollectIdBean> getCollectIds() {
        LoginInfo loginInfo = getLoginInfo();
        if (null != loginInfo) {
            String collectIds = loginInfo.collectIds;
            return GsonUtil.toList(collectIds, CollectIdBean.class);
        } else {
            return null;
        }
    }
}
