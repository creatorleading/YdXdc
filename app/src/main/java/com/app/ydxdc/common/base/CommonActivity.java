package com.app.ydxdc.common.base;

import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.app.common.BaseActivity;
import com.app.common.base.BaseFragment;
import com.app.common.util.DPreference;
import com.app.ydxdc.R;
import com.app.ydxdc.ui.global.AppData;
import com.app.ydxdc.ui.global.XDCApplication;

import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * CommonActivity
 */
public abstract class CommonActivity extends BaseActivity {

    protected final String TAG = this.getClass().getSimpleName();

    /**
     * 总布局.
     */
    protected RelativeLayout baseLayout = null;

    /**
     * 标题栏布局.
     */
    public TitleBar titleBar;

    TitleBar.Action action;

    /**
     * 主内容布局.
     */
    protected View contentLayout = null;

    protected XDCApplication application;

    protected AppData appData;

    protected DPreference dPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dPreference = DPreference.getInstance(this, XDCApplication.PREFERENCE_NAME);
        this.baseLoader = getBaseLoader();
        //设置沉浸式状态栏
        StatusBarUtils.translucent(this);
        this.application = (XDCApplication) getApplication();
        this.appData = application.getAppData();
        initRootContentView();
        //注解控件
        ButterKnife.bind(this);
        //注册监听
        onBindView();
        //初始化数据。
        onInitData();
    }

    protected void initRootContentView() {
        baseLayout = new RelativeLayout(this);
        View titleView = LayoutInflater.from(this).inflate(R.layout.x_titlebar, baseLayout, false);
        titleBar = titleView.findViewById(R.id.titleBar);
        titleBar.setBackgroundColor(ContextCompat.getColor(this, R.color.bg_color));
        titleBar.setLeftClickListener(v -> onBackPressedSupport());
        RelativeLayout.LayoutParams titleLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        titleLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        baseLayout.addView(titleView, titleLayoutParams);
        RelativeLayout.LayoutParams contentLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        contentLayoutParams.addRule(RelativeLayout.BELOW, titleBar.getId());
        contentLayout = LayoutInflater.from(this).inflate(getLayoutID(), null);
        baseLayout.addView(contentLayout, contentLayoutParams);
        RelativeLayout.LayoutParams baseParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setContentView(baseLayout, baseParams);
        onTitleBarInitialized();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void start(ISupportFragment supportFragment) {
        if (getTopFragment() != null && (getTopFragment() instanceof BaseFragment)) {
            ((BaseFragment) getTopFragment()).start(supportFragment);
        } else {
            super.start(supportFragment);
        }
    }

    /**
     * 无动画的界面跳转
     *
     * @param toFragment
     */
    public void startNoAnimator(CommonFragment toFragment) {
        setFragmentAnimator(new DefaultNoAnimator());
        start(toFragment);
    }

    /**
     * 无动画的界面跳转
     *
     * @param toFragment
     */
    public void replaceNoAnimator(CommonFragment toFragment) {
        setFragmentAnimator(new DefaultNoAnimator());
        replace(toFragment);
    }

    /**
     * 返回键按下时回调，自动调用finish方法。
     *
     * @return
     */
    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    /**
     * 设置是否显示标题
     *
     * @param visible 是否可见
     */
    protected void setTitleBarVisibility(boolean visible) {
        if (titleBar != null) {
            titleBar.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    protected void setTitleBarAction(String text) {
        action = new TitleBar.TextAction(text == null ? "确定" : text) {
            @Override
            public void performAction(View view) {
                onSubmit();
            }
        };
        titleBar.addAction(action);
    }

    /**
     * 设置是否显示标题
     *
     * @param visible 是否可见
     * @param fusion  是否融合状态栏
     */
    protected void setTitleBarVisibility(boolean visible, boolean fusion) {
        if (titleBar != null) {
            titleBar.setVisibility(visible ? VISIBLE : View.GONE);
            if (fusion) {
                contentLayout.setPadding(0, StatusBarUtils.getStatusBarHeight(this), 0, 0);
            }
        }
    }

    /**
     * 确认键按下时回调
     */
    protected void onSubmit() {

    }

    /**
     * 描述：设置标题文本。
     *
     * @param resId 资源ID
     */
    @Override
    public void setTitle(int resId) {
        if (titleBar != null) {
            titleBar.setTitle(resId);
        }
    }

    /**
     * 描述：设置标题文本。
     *
     * @param title 标题文本
     */
    @Override
    public void setTitle(CharSequence title) {
        if (titleBar != null) {
            titleBar.setTitle(title);
        }
    }

    /**
     * 设置确认按钮是否可见
     *
     * @param visible
     */
    protected void setSubmitBtnVisibility(boolean visible) {
        if (!visible) {
            titleBar.removeAction(action);
        }
    }

    public void startActivity(Class activity) {
        Intent intent = new Intent();
        intent.setClass(getApplication(), activity);
        startActivity(intent);
    }

    /**
     * 返回根布局文件ID
     *
     * @return
     */
    protected abstract int getLayoutID();


    protected void onTitleBarInitialized() {

    }

    public AppData getAppData() {
        return appData;
    }


    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultVerticalAnimator();
    }

}

