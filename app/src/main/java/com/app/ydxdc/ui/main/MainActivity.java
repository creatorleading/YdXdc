package com.app.ydxdc.ui.main;

import com.app.ydxdc.R;
import com.app.ydxdc.common.mvp.MVPActivity;
import com.app.ydxdc.ui.home.HomeFragment;

/**
 * 首页activity
 */
public class MainActivity extends MVPActivity<MainContract.Presenter> implements MainContract.View {


    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public MainPresenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void onBindView() {
        HomeFragment homeFragment = HomeFragment.newInstance();
        loadRootFragment(R.id.contentFl, homeFragment);
    }

    @Override
    public void onBackPressedSupport() {
        if (getTopFragment() instanceof HomeFragment) {
            application.toExit(this);
        } else {
            super.onBackPressedSupport();
        }
    }
}