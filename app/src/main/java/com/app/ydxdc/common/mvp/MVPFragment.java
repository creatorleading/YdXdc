package com.app.ydxdc.common.mvp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.app.common.util.HTLog;

import com.app.ydxdc.R;
import com.app.ydxdc.common.base.CommonFragment;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.LoadingDialog;

/**
 * MVPFragment
 * 实现MVP模式的Fragment
 */
public abstract class MVPFragment<P extends BasePresenter> extends CommonFragment implements BaseView {

    protected P mPresenter;

    LoadingDialog mLoadingDialog;

    /**
     * 创建内容View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutID(), null);
    }

    /**
     * 创建Presenter
     *
     * @return
     */
    @Override
    public abstract P initPresenter();

    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.initRootView(inflater, container, savedInstanceState);
        this.mPresenter = (P) initPresenter();
        if (this.mPresenter != null) {
            this.mPresenter.attach(this);
        }
        mLoadingDialog = WidgetUtils.getLoadingDialog(getContext())
                .setLoadingIcon(ResUtils.getDrawable(R.drawable.ic_launcher))
                .setLoadingSpeed(8);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.mPresenter != null) {
            this.mPresenter.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (this.mPresenter != null) {
            mPresenter.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mPresenter != null) {
            mPresenter.detach();
        }
    }

    @Override
    public void showProgress(String msg) {
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.updateMessage(msg);
            mLoadingDialog.show();
        }
    }

    @Override
    public void showFailed(String message) {
        HTLog.e(getClass().toString()+"   "+message);
        showLong(message);
    }

    @Override
    public void hideProgress() {
        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLoadingDialog.recycle();
    }

    public void hideKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public LoadingDialog getLoadingDialog() {
        return mLoadingDialog;
    }
}
