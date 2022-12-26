package com.app.ydxdc.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.ydxdc.R;

import butterknife.OnClick;

public class HomeFragment extends HomeBusinessFragment {


    private TextView requestTv;

    @Override
    protected void onTitleBarInitialized() {
        setTitleBarVisibility(false);
    }

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutID() {
        requestTv = findViewById(R.id.requestTv);
        return R.layout.fragment_home;

    }

    @OnClick({R.id.requestTv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.requestTv:

                break;
            default:
        }
    }

    @Override
    public void showFailed(String message) {

    }

    @Override
    public void showProgress(String msg) {

    }

    @Override
    public void hideProgress() {

    }
}
