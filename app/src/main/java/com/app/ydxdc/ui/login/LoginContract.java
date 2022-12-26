package com.app.ydxdc.ui.login;


import com.app.ydxdc.common.mvp.BasePresenter;
import com.app.ydxdc.common.mvp.BaseView;
import com.app.ydxdc.ui.entity.LoginInfo;
import com.app.ydxdc.ui.entity.UserInfo;

/**
 * 登录功能接口定义
 */
public interface LoginContract {

    interface View extends BaseView {

        void loginSuccess(LoginInfo loginInfo);

        void getUserInfoSuccess(UserInfo userInfo);

    }

    interface Presenter extends BasePresenter<View> {
        /**
         * 登录
         *
         * @param username
         * @param password
         */
        void login(String username, String password);

        void getUserInfo();


    }
}
