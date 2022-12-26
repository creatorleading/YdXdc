package com.app.ydxdc.ui.global;

import android.app.Activity;
import android.app.Application;


import com.app.common.base.okgo.OkHttpInitHelper;
import com.app.common.util.DPreference;
import com.app.common.util.OrientationSensorUtils;


import com.app.ydxdc.R;
import com.app.ydxdc.common.base.BaseApplication;
import com.xuexiang.xui.BuildConfig;
import com.xuexiang.xui.XUI;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xutil.XUtil;



public class XDCApplication extends BaseApplication {

    public static final String PREFERENCE_NAME = "mvp";

    private static XDCApplication application = null;

    public static boolean isDebug = false;


    private AppData appData;

    protected DPreference dPreference;


    public AppData getAppData() {
        return appData;
    }

    /**
     * TODO 方向传感器
     */
    public OrientationSensorUtils sensorUtils;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        this.appData = new AppData();
        dPreference = DPreference.getInstance(this);
        initUI();
        OkHttpInitHelper.getInstance(this).init();
    }




    public static Application getInstance() {
        return application;
    }




    /**
     * 初始化XUI 框架
     */
    private void initUI() {
        XUI.init(this);
        XUI.debug(BuildConfig.DEBUG);
        XUtil.init(this);
    }

    /**
     * @return
     */
    public static XDCApplication getApplication() {
        return application;
    }


    public boolean toExit(final Activity activity) {
        new MaterialDialog.Builder(activity)
                .iconRes(R.drawable.ic_launcher)
                .limitIconToDefaultSize() // limits the displayed icon size to 48dp
                .title(R.string.tips)
                .content("是否退出?")
                .positiveText(R.string.submit)
                .positiveColor(ResUtils.getColor(R.color.red))
                .onPositive((dialog, which) -> {
                    //TODO 方向传感器
                    if (sensorUtils != null) {
                        sensorUtils.stopOrientationSensor();
                    }
                    dialog.dismiss();
                    android.os.Process.killProcess(android.os.Process.myPid());
                })
                .negativeText(R.string.cancel)
                .onNegative((dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
        return true;
    }


}





