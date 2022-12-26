package com.app.ydxdc.ui.splash;


import android.os.Bundle;

import com.app.ydxdc.R;
import com.app.ydxdc.common.base.CommonActivity;
import com.app.ydxdc.ui.login.LoginActivity;
import com.xuexiang.xutil.app.ActivityUtils;




/**
 * 欢迎界面
 */
public class SplashActivity extends CommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onTitleBarInitialized() {
        setTitleBarVisibility(false);
    }



    private void toLoginActivity() {
        ActivityUtils.startActivity(LoginActivity.class);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_exit);
        finish();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_splash;
    }
}
