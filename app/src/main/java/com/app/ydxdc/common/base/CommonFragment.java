package com.app.ydxdc.common.base;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import androidx.core.content.ContextCompat;

import com.app.common.base.BaseFragment;
import com.app.common.util.DPreference;
import com.app.common.util.HTLog;

import com.app.ydxdc.R;
import com.app.ydxdc.ui.global.AppData;
import com.app.ydxdc.ui.global.XDCApplication;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public abstract class CommonFragment extends BaseFragment {

    public String TAG = this.getClass().getSimpleName();

    protected View rootView;

    View contentView;

    private LayoutInflater mInflater;

    protected TitleBar titleBar;

    public static final int COMMON_REQUEST_CODE = 1024;
    public static final int COMMON_RESULT_CODE = 200;

    public static final int COMMON_SHOW_RESULT_CODE = 203;

    private boolean invalidate = false;

    private Bundle backBundle;

    private Unbinder unbinder;

    protected XDCApplication application;

    protected AppData appData;

    protected DPreference dPreference;

    TitleBar.Action action;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.application = (XDCApplication) mActivity.getApplication();
        this.appData = application.getAppData();
        dPreference = DPreference.getInstance(mActivity, XDCApplication.PREFERENCE_NAME);
    }

    /**
     * 参数初始化，可以覆盖此方法进行实例参数初始化的操作。此方法在布局初始化之前调用。
     */
    protected void onArgumentsInitialized(Bundle bundle) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = inflater;
        initRootView(inflater, container, savedInstanceState);
        onArgumentsInitialized(getArguments());
        onTitleBarInitialized();
        unbinder = ButterKnife.bind(this, rootView);

        if (!(this instanceof ILazyInitFragment)) {
            reset(true);
        }
        return rootView;
    }

    protected void initRootView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        LinearLayout baseLayout = new LinearLayout(getActivity());
        baseLayout.setOrientation(LinearLayout.VERTICAL);
        baseLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        baseLayout.setBackgroundColor(Color.TRANSPARENT);
        View titleView = inflater.inflate(R.layout.x_titlebar, baseLayout, false);
        titleBar = titleView.findViewById(R.id.titleBar);
        titleBar.setImmersive(true);
        titleBar.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.bg_color));
        titleBar.setLeftClickListener(v -> {
            boolean back = onBackPressedSupport();
            if (!back) {
                if (mActivity != null) {
                    mActivity.onBackPressedSupport();
                }
            }
        });
        action = new TitleBar.TextAction("确定") {
            @Override
            public void performAction(View view) {
                onSubmit();
            }
        };
        titleBar.addAction(action);
        baseLayout.addView(titleView);
        contentView = onCreateContentView(inflater, container, savedInstanceState);
        LayoutParams layoutParams = onCreateContentViewLayoutParams(getActivity());
        baseLayout.addView(contentView, layoutParams);
        rootView = baseLayout;
    }

    protected void setTitleBarAction(String text) {
        action = new TitleBar.TextAction(text) {
            @Override
            public void performAction(View view) {
                onSubmit();
            }
        };
        titleBar.addAction(action);
    }

    /**
     * 标题栏初始化完毕回调方法，建议在此方法内设置标题栏布局
     */
    protected void onTitleBarInitialized() {

    }

    /**
     * 内容布局参数，可通过覆盖方法自定义容器的位置。
     *
     * @return
     */
    protected LayoutParams onCreateContentViewLayoutParams(Activity activity) {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    /**
     * 创建内容View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutID(), null);
    }

    /**
     * 返回时触发，包括点击左上角返回键和后退键。子类可覆盖用于处理扩展业务逻辑。
     *
     * @return 返回false则执行完此方法后自动finish当前界面，返回true停留在当前界面。
     */
    protected boolean onBack() {
        return false;
    }


    /**
     * 描述：初始化数据。建议在此方法内填充视图的数据。<br/>
     * <b>注意：此方法在onBindView后执行。</b>
     */
    protected abstract void onInitData();


    /**
     * 描述：用指定资源ID表示的View填充主界面，视图创建完毕后调用，建议在此方法内注册监听。<br/>
     * <b>注意：此方法在onInitData前执行。</b>
     *
     * @param contentView 内容View
     */
    protected void onBindView(View contentView) {

    }

    /**
     * 设置后退按钮是否可见
     *
     * @param visible
     */
    protected void setNavigationVisibility(boolean visible) {
        if (titleBar != null) {
            if (visible) {
                titleBar.setLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressedSupport();
                    }
                });
            } else {
                titleBar.setLeftClickListener(null);
                titleBar.setLeftImageDrawable(null);
            }
        }
    }

    protected void setNavigationIcon(int resourceId) {
        if (titleBar != null) {
            titleBar.setLeftImageResource(resourceId);
        }
    }

    /**
     * 确认键按下时回调
     */
    protected void onSubmit() {
        invalidateBackRefresh(null);
    }


//    public boolean onKeyUp(int keyCode, KeyEvent event){
//        return false;
//    }

//    /**
//     * 返回键按下时回调，如返回false，则会自动调用FManager.back方法。
//     * @return
//     */
//    protected boolean onBack(){
//        return false;
//    }

    /**
     * 刷新视图
     *
     * @param bundle
     */
    public void onRefresh(Bundle bundle) {

    }

    /**
     * 结束当前Framgent
     */
    public void finish() {
        hideSoftInput();
        if (invalidate) {
            setFragmentResult(COMMON_RESULT_CODE, backBundle);
            invalidate = false;
        } else {
            setFragmentResult(COMMON_SHOW_RESULT_CODE, null);
        }
        mActivity.onBackPressedSupport();
    }

    /**
     * 重置当前界面
     *
     * @param resetBind 是否重新注册控件监听
     */
    public void reset(boolean resetBind) {
        if (resetBind) {
            onBindView(rootView);
        }
        onInitData();
    }

    /**
     * 刷新当前界面和数据。不重新注册控件监听。
     */
    public void refreshData() {
        onInitData();
    }

    /**
     * 标记后退刷新，调用此方法后，如果按下后退键，则前一个界面会自动调用onRefresh进行刷新。
     *
     * @param bundle 返回前一个界面的Bundle
     */
    public void invalidateBackRefresh(Bundle bundle) {
        this.invalidate = true;
        this.backBundle = bundle;
    }

    /**
     * 标记后退刷新，调用此方法后，如果按下后退键，则前一个界面会自动调用onRefresh进行刷新。
     */
    public void invalidateBackRefresh() {
        this.invalidate = true;
    }

    /**
     * 设置是否显示标题
     *
     * @param visible 是否可见
     */
    protected void setTitleBarVisibility(boolean visible) {
        if (titleBar != null) {
            titleBar.setVisibility(visible ? VISIBLE : View.GONE);
            /*if (!visible) {
                contentView.setPadding(0, StatusBarUtils.getStatusBarHeight(mActivity), 0, 0);
            }*/
        }
    }

    /**
     * 设置是否显示标题
     *
     * @param visible 是否可见
     * @param fusion  是否融合状态栏
     *//*
    protected void setTitleBarVisibility(boolean visible, boolean fusion) {
        if (titleBar != null) {
            titleBar.setVisibility(visible ? VISIBLE : View.GONE);
            if (fusion) {
                contentView.setPadding(0, StatusBarUtils.getStatusBarHeight(mActivity), 0, 0);
            }
        }
    }*/

    /**
     * 描述：设置标题文本.
     *
     * @param text 文本
     */
    public void setTitle(String text) {
        if (titleBar != null) {
            titleBar.setTitle(text);
        }
    }

    /**
     * 描述：设置标题文本.
     *
     * @param resId 资源ID
     */
    public void setTitle(int resId) {
        if (titleBar != null) {
            titleBar.setTitle(resId);
        }
    }

    /**
     * 设置主标题文字是否显示
     *
     * @param visible
     */
    public void setTitleVisibility(boolean visible) {
        if (titleBar != null) {
            titleBar.setVisibility(visible ? VISIBLE : GONE);
        }
    }

    /**
     * 设置中间标题
     */
    public void setCenterTitle(String title) {
        if (titleBar != null) {
            titleBar.removeAllActions();
            titleBar.setTitle(title);
        }

    }

    /**
     * 描述：设置标题文本.
     *
     * @param text 文本
     */
    public void setSubmitText(CharSequence text) {
        if (titleBar != null) {
            titleBar.removeAllActions();
            action = new TitleBar.TextAction(text.toString()) {
                @Override
                public void performAction(View view) {
                    onSubmit();
                }
            };
            titleBar.addAction(action);
        }
    }

    /**
     * 描述：设置右侧图片.
     *
     * @param icon 图片资源
     */
    public void setSubmitIcon(int icon) {
        if (titleBar != null) {
            titleBar.removeAllActions();
            action = new TitleBar.ImageAction(icon) {
                @Override
                public void performAction(View view) {
                    onSubmit();
                }
            };
            titleBar.addAction(action);
        }
    }

    /**
     * 描述：设置标题文本.
     *
     * @param resId 资源ID
     */
    public void setSubmitText(int resId) {
        if (titleBar != null) {
            titleBar.removeAllActions();
            action = new TitleBar.TextAction(getContext().getResources().getString(resId)) {
                @Override
                public void performAction(View view) {
                    onSubmit();
                }
            };
            titleBar.addAction(action);
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

//    /**
//     * 在标题栏最右侧添加ActionView
//     *
//     * @param resId
//     */
//    protected void addActionView(int resId) {
//        addActionView(mInflater.inflate(resId, null));
//    }

//    /**
//     * 在标题栏最右侧添加ActionView
//     *
//     * @param view
//     */
//    protected void addActionView(View view) {
//        if (titleBar != null) {
//            titleBar.addAction(view);
//        }
//    }

//    /**
//     * 在标题栏水平居中的位置添加ActionView，调用后会自动隐藏title。
//     *
//     * @param resId
//     */
//    protected void addActionViewToCenter(int resId) {
//        addActionViewToCenter(mInflater.inflate(resId, null));
//    }

//    /**
//     * 在标题栏水平居中的位置添加ActionView，调用后会自动隐藏title。
//     *
//     * @param view
//     */
//    protected void addActionViewToCenter(View view) {
//        if (titleBar != null) {
//            titleBar.addActionViewToCenter(view);
//        }
//    }

//    /**
//     * 在标题栏添加撑满的ActionView
//     *
//     * @param resId
//     */
//    protected void addActionViewToFull(int resId) {
//        addActionViewToFull(mInflater.inflate(resId, null));
//    }

//    /**
//     * 在标题栏添加撑满的ActionView
//     *
//     * @param view
//     */
//    protected void addActionViewToFull(View view) {
//        if (titleBar != null) {
//            titleBar.addActionViewToFull(view);
//        }
//    }

    public void startActivity(Class activity) {
        Intent intent = new Intent();
        intent.setClass(mActivity, activity);
        startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        HTLog.d("fragment onActivityResult");
        hideSoftInput();
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        HTLog.d("fragment onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * @param toFragment
     */
    @Override
    public void start(ISupportFragment toFragment) {
        onHide();
        if (toFragment instanceof CommonFragment) {
            start((CommonFragment) toFragment, null);
        } else {
            super.start(toFragment);
        }
    }

    /**
     * @param toFragment
     * @param bundle
     */
    public void start(CommonFragment toFragment, Bundle bundle) {
        hideSoftInput();
        if (bundle != null) {
        toFragment.setArguments(bundle);
        }
        super.startForResult(toFragment, COMMON_REQUEST_CODE);
    }


    /**
     * 无动画的界面跳转
     *
     * @param toFragment
     * @param bundle
     */
    public void startNoAnimator(CommonFragment toFragment, Bundle bundle) {
        setFragmentAnimator(new DefaultNoAnimator());
        start(toFragment, bundle);
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
     * 通过
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        if (COMMON_REQUEST_CODE == requestCode) {
            if (resultCode == COMMON_RESULT_CODE) {
                onRefresh(data);
            } else if (resultCode == COMMON_SHOW_RESULT_CODE) {
                onShow();
            } else {
                super.onFragmentResult(requestCode, resultCode, data);
            }
        } else {
            super.onFragmentResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.appData != null) {
            if (isVisibleToUser) {
                onShow();
            } else {
                onHide();
            }
        }
    }

    /**
     * 当前fragment重新恢复到可见模式调用
     */
    public void onShow() {

    }

    /**
     * 当前fragment不可见时调用
     */
    public void onHide() {

    }

    /**
     * 重写返回回调，默认增加隐藏软键盘，子类不需要覆盖，如果需要处理后退，请重写onBack();
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        hideSoftInput();
        if (invalidate) {
            setFragmentResult(COMMON_RESULT_CODE, backBundle);
            invalidate = false;
        } else {
            setFragmentResult(COMMON_SHOW_RESULT_CODE, null);
        }
        if (onBack()) {
            return true;
        } else {
            return super.onBackPressedSupport();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        if (this instanceof ILazyInitFragment) {
            reset(true);
        }
    }

    /**
     * 在最顶层Fragment切换新的Fragment
     *
     * @param toFragment
     */
    public void startParentFragment(ISupportFragment toFragment) {
        if (getParentFragment() != null && getParentFragment() instanceof CommonFragment) {
            ((CommonFragment) getParentFragment()).start(toFragment);
        } else {
            HTLog.d("警告:startParentFragment 调用异常");
            start(toFragment);
        }
    }

    /**
     * 返回根布局文件
     *
     * @return
     */
    protected int getLayoutID() {
        return 0;
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    /**
     * 根据ID返回View
     *
     * @param id
     * @return
     */
    protected <T extends View> T findViewById(int id) {
        if (rootView != null) {
            return (T) rootView.findViewById(id);
        }
        return null;
    }

    /**
     * 隐藏软键盘
     */
    @Override
    public void hideSoftInput() {
        super.hideSoftInput();
    }

    public XDCApplication getApplication() {
        return application;
    }

    public AppData getAppData() {
        return appData;
    }


}
