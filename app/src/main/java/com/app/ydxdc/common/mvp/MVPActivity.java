package com.app.ydxdc.common.mvp;

import com.app.common.util.HTLog;
import com.app.ydxdc.R;
import com.app.ydxdc.common.base.CommonActivity;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.dialog.LoadingDialog;

/**
 * MVPActivity
 * 实现MVP模式的Activity抽象类
 */
public abstract class MVPActivity<P extends BasePresenter> extends CommonActivity implements BaseView {

    protected P mPresenter;

    LoadingDialog mLoadingDialog;

    @Override
    protected void initRootContentView() {
        super.initRootContentView();
        this.mPresenter = (P) initPresenter();
        if (this.mPresenter != null) {
            this.mPresenter.attach(this);
        }
        mLoadingDialog = WidgetUtils.getLoadingDialog(this)
                .setLoadingIcon(ResUtils.getDrawable(R.drawable.ic_launcher))
                .setLoadingSpeed(8);
    }

    @Override
    public void showFailed(String message) {
        HTLog.e(getClass().toString()+"   "+message);
        showLongMessage(message);
    }

    @Override
    public void showProgress(String msg) {
        if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.updateMessage(msg);
            mLoadingDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.mPresenter != null) {
            mPresenter.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.mPresenter != null) {
            mPresenter.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mPresenter != null) {
            mPresenter.detach();
        }
        if (mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog.recycle();
        }
    }
}
