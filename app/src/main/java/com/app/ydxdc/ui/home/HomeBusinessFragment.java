package com.app.ydxdc.ui.home;


import com.app.ydxdc.common.mvp.MVPFragment;

public abstract class HomeBusinessFragment extends MVPFragment<HomeContract.Presenter> implements HomeContract.View {
    @Override
    protected void onInitData() {

    }

    @Override
    public  HomeContract.Presenter initPresenter() {
        return new HomePresenter(mActivity);
    }

}
