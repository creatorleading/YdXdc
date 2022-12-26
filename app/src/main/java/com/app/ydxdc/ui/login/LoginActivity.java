package com.app.ydxdc.ui.login;

import com.app.ydxdc.R;
import com.app.ydxdc.common.mvp.MVPActivity;
import com.app.ydxdc.ui.entity.LoginInfo;
import com.app.ydxdc.ui.entity.UserInfo;
import com.app.ydxdc.ui.loader.LoginLoader;
import com.app.ydxdc.ui.main.MainActivity;
import com.xuexiang.xutil.app.ActivityUtils;

public class LoginActivity extends MVPActivity<LoginContract.Presenter> implements LoginContract.View {
    @Override
    protected void onTitleBarInitialized() {
        setTitleBarVisibility(false);
    }


    @Override
    protected int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void onInitData() {
        super.onInitData();
        ActivityUtils.startActivity(MainActivity.class);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_exit);
        finish();
    }

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void loginSuccess(LoginInfo loginInfo) {

    }

    @Override
    public void getUserInfoSuccess(UserInfo userInfo) {

    }
}
