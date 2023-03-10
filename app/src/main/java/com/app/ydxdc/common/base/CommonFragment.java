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
     * ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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
        action = new TitleBar.TextAction("??????") {
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
     * ?????????????????????????????????????????????????????????????????????????????????
     */
    protected void onTitleBarInitialized() {

    }

    /**
     * ?????????????????????????????????????????????????????????????????????
     *
     * @return
     */
    protected LayoutParams onCreateContentViewLayoutParams(Activity activity) {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    /**
     * ????????????View
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
     * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @return ??????false??????????????????????????????finish?????????????????????true????????????????????????
     */
    protected boolean onBack() {
        return false;
    }


    /**
     * ????????????????????????????????????????????????????????????????????????<br/>
     * <b>?????????????????????onBindView????????????</b>
     */
    protected abstract void onInitData();


    /**
     * ????????????????????????ID?????????View????????????????????????????????????????????????????????????????????????????????????<br/>
     * <b>?????????????????????onInitData????????????</b>
     *
     * @param contentView ??????View
     */
    protected void onBindView(View contentView) {

    }

    /**
     * ??????????????????????????????
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
     * ????????????????????????
     */
    protected void onSubmit() {
        invalidateBackRefresh(null);
    }


//    public boolean onKeyUp(int keyCode, KeyEvent event){
//        return false;
//    }

//    /**
//     * ????????????????????????????????????false?????????????????????FManager.back?????????
//     * @return
//     */
//    protected boolean onBack(){
//        return false;
//    }

    /**
     * ????????????
     *
     * @param bundle
     */
    public void onRefresh(Bundle bundle) {

    }

    /**
     * ????????????Framgent
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
     * ??????????????????
     *
     * @param resetBind ??????????????????????????????
     */
    public void reset(boolean resetBind) {
        if (resetBind) {
            onBindView(rootView);
        }
        onInitData();
    }

    /**
     * ????????????????????????????????????????????????????????????
     */
    public void refreshData() {
        onInitData();
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????????????????????????????onRefresh???????????????
     *
     * @param bundle ????????????????????????Bundle
     */
    public void invalidateBackRefresh(Bundle bundle) {
        this.invalidate = true;
        this.backBundle = bundle;
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????????????????????????????onRefresh???????????????
     */
    public void invalidateBackRefresh() {
        this.invalidate = true;
    }

    /**
     * ????????????????????????
     *
     * @param visible ????????????
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
     * ????????????????????????
     *
     * @param visible ????????????
     * @param fusion  ?????????????????????
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
     * ???????????????????????????.
     *
     * @param text ??????
     */
    public void setTitle(String text) {
        if (titleBar != null) {
            titleBar.setTitle(text);
        }
    }

    /**
     * ???????????????????????????.
     *
     * @param resId ??????ID
     */
    public void setTitle(int resId) {
        if (titleBar != null) {
            titleBar.setTitle(resId);
        }
    }

    /**
     * ?????????????????????????????????
     *
     * @param visible
     */
    public void setTitleVisibility(boolean visible) {
        if (titleBar != null) {
            titleBar.setVisibility(visible ? VISIBLE : GONE);
        }
    }

    /**
     * ??????????????????
     */
    public void setCenterTitle(String title) {
        if (titleBar != null) {
            titleBar.removeAllActions();
            titleBar.setTitle(title);
        }

    }

    /**
     * ???????????????????????????.
     *
     * @param text ??????
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
     * ???????????????????????????.
     *
     * @param icon ????????????
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
     * ???????????????????????????.
     *
     * @param resId ??????ID
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
     * ??????????????????????????????
     *
     * @param visible
     */
    protected void setSubmitBtnVisibility(boolean visible) {
        if (!visible) {
            titleBar.removeAction(action);
        }
    }

//    /**
//     * ???????????????????????????ActionView
//     *
//     * @param resId
//     */
//    protected void addActionView(int resId) {
//        addActionView(mInflater.inflate(resId, null));
//    }

//    /**
//     * ???????????????????????????ActionView
//     *
//     * @param view
//     */
//    protected void addActionView(View view) {
//        if (titleBar != null) {
//            titleBar.addAction(view);
//        }
//    }

//    /**
//     * ???????????????????????????????????????ActionView???????????????????????????title???
//     *
//     * @param resId
//     */
//    protected void addActionViewToCenter(int resId) {
//        addActionViewToCenter(mInflater.inflate(resId, null));
//    }

//    /**
//     * ???????????????????????????????????????ActionView???????????????????????????title???
//     *
//     * @param view
//     */
//    protected void addActionViewToCenter(View view) {
//        if (titleBar != null) {
//            titleBar.addActionViewToCenter(view);
//        }
//    }

//    /**
//     * ???????????????????????????ActionView
//     *
//     * @param resId
//     */
//    protected void addActionViewToFull(int resId) {
//        addActionViewToFull(mInflater.inflate(resId, null));
//    }

//    /**
//     * ???????????????????????????ActionView
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
     * ????????????????????????
     *
     * @param toFragment
     * @param bundle
     */
    public void startNoAnimator(CommonFragment toFragment, Bundle bundle) {
        setFragmentAnimator(new DefaultNoAnimator());
        start(toFragment, bundle);
    }

    /**
     * ????????????????????????
     *
     * @param toFragment
     */
    public void startNoAnimator(CommonFragment toFragment) {
        setFragmentAnimator(new DefaultNoAnimator());
        start(toFragment);
    }

    /**
     * ??????
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
     * ??????fragment?????????????????????????????????
     */
    public void onShow() {

    }

    /**
     * ??????fragment??????????????????
     */
    public void onHide() {

    }

    /**
     * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????onBack();
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
     * ????????????Fragment????????????Fragment
     *
     * @param toFragment
     */
    public void startParentFragment(ISupportFragment toFragment) {
        if (getParentFragment() != null && getParentFragment() instanceof CommonFragment) {
            ((CommonFragment) getParentFragment()).start(toFragment);
        } else {
            HTLog.d("??????:startParentFragment ????????????");
            start(toFragment);
        }
    }

    /**
     * ?????????????????????
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
     * ??????ID??????View
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
     * ???????????????
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
