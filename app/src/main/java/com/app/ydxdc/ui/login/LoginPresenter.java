package com.app.ydxdc.ui.login;

import android.content.Context;

import com.app.ydxdc.common.http.ResponseCallback;
import com.app.ydxdc.common.mvp.BasePresenterImpl;
import com.app.ydxdc.ui.entity.LoginInfo;
import com.app.ydxdc.ui.entity.UserInfo;
import com.app.ydxdc.ui.loader.LoginLoader;


/**
 * 登录Presenter
 */

public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter {

    private final Context mContext;
    private LoginLoader mLoader;

    /**
     * 登录
     *
     * @param mContext
     */
    public LoginPresenter(Context mContext) {
        this.mContext = mContext;
        this.mLoader = new LoginLoader(mContext);
    }


    @Override
    public void login(String username, String password) {
        mLoader.login(username, password, new ResponseCallback<LoginInfo>() {

            @Override
            public void onSuccess(LoginInfo loginInfo) {
                getView().loginSuccess(loginInfo);
            }

            @Override
            public void onFail(Exception e) {
                getView().hideProgress();
                getView().showFailed(e.getMessage());
            }
        });
    }

    @Override
    public void getUserInfo() {
        mLoader.getUserInfo(new ResponseCallback<UserInfo>() {

            @Override
            public void onSuccess(UserInfo userInfo) {
                getView().getUserInfoSuccess(userInfo);
            }

            @Override
            public void onFail(Exception e) {
                getView().hideProgress();
                getView().showFailed(e.getMessage());
            }
        });
    }

}
