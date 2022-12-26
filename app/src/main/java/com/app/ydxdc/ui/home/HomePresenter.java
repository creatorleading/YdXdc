package com.app.ydxdc.ui.home;

import android.content.Context;

import com.app.ydxdc.common.mvp.BasePresenterImpl;
import com.app.ydxdc.ui.loader.HomeLoader;

public class HomePresenter extends BasePresenterImpl<HomeContract.View> implements HomeContract.Presenter {

    private final Context mContext;
    private final HomeLoader mHomeLoader;

    /**
     * 修改密码
     *
     * @param mContext
     */
    public HomePresenter(Context mContext) {
        this.mContext = mContext;
        this.mHomeLoader = new HomeLoader(mContext);
    }



}
