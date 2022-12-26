package com.app.ydxdc.common.base;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.annotation.Nullable;

import com.app.common.util.HTLog;
import com.hjq.permissions.XXPermissions;


public abstract class BaseMainActivity extends CommonActivity {

    /**
     * 默认启动页过渡时间
     */
    private static final int DEFAULT_SPLASH_DURATION_MILLIS = 500;


    String[] NEED_PERMS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA
            };

    /**
     * @return 启动页持续的时间
     */
    protected long getSplashDurationMillis() {
        return DEFAULT_SPLASH_DURATION_MILLIS;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        avoidLauncherAgain();
        setTitleBarVisibility(false);
        startSplashAnim(new AlphaAnimation(0.3F, 1.0F));
    }

    private void initXXPermissions() {
        XXPermissions.with(this).permission(NEED_PERMS).request((permissions, all) -> {
            if (all){
                onPermissionSuccess();
            } else {
                showShortMessage("用户没有在权限设置页授予权限");
            }
        });
    }

    private void avoidLauncherAgain() {
        // 避免从桌面启动程序后，会重新实例化入口类的activity
        if (!this.isTaskRoot()) { // 判断当前activity是不是所在任务栈的根
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                }
            }
        }
    }


    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == XXPermissions.REQUEST_CODE) {
            if (XXPermissions.isGranted(this, NEED_PERMS)) {
                onPermissionSuccess();
            } else {
                showShortMessage("用户没有在权限设置页授予权限");
            }
        }

    }

    protected abstract void onPermissionSuccess();

    /**
     * 开启引导过渡动画
     *
     * @param anim
     */
    private void startSplashAnim(Animation anim) {
        anim.setDuration(getSplashDurationMillis());
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                HTLog.d("动画开始");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                HTLog.d("动画结束");
                initXXPermissions();
            }
        });
        getRootView().startAnimation(anim);
    }

    private View getRootView() {
        return getWindow().getDecorView().findViewById(android.R.id.content);
    }

}
