package com.app.ydxdc.common.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.common.base.BaseFragment;
import com.app.ydxdc.R;


/**
 * 默认空白容器
 */

public class ContentFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content,null);
    }

}
