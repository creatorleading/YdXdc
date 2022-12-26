/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.app.ydxdc.common.mvp;
/**
 * BaseView MVP视图接口
 * 
 */
public interface BaseView<P extends BasePresenter> {

    /**
     * 创建Presenter
     * @return
     */
    P initPresenter();

    /**
     * 显示失败消息
     * @param message
     */
    void showFailed(String message);

    /**
     * 显示loading框
     */
    void showProgress(String msg);

    /**
     * 隐藏loading框
     */
    void hideProgress();

}
