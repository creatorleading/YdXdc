package com.app.ydxdc.common.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import es.dmoral.toasty.MyToast;
import es.dmoral.toasty.Toasty;
import me.yokeyword.fragmentation.Fragmentation;


/**
 * BaseApplication
 */
public abstract class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fragmentation.builder().stackViewMode(Fragmentation.BUBBLE).install();
        Toasty.Config.getInstance();
        MyToast.init(this, false, false);
        closeAndroidPDialog();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            @SuppressLint("SoonBlockedPrivateApi") Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }





    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

}
