package com.app.ydxdc.ui.main;

import android.content.Context;

import com.app.ydxdc.common.mvp.BasePresenterImpl;
import com.app.ydxdc.ui.loader.MainLoader;


/**
 * 主界面Presenter
 */

public class MainPresenter extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter {

    private final Context mContext;
    private final MainLoader mLoader;


    public MainPresenter(Context mContext) {
        this.mContext = mContext;
        this.mLoader = new MainLoader(mContext);

    }



}
