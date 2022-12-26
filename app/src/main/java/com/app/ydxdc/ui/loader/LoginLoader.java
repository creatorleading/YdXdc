package com.app.ydxdc.ui.loader;

import android.content.Context;

import com.app.ydxdc.common.http.CommonLoader;
import com.app.ydxdc.common.http.ResponseCallback;
import com.app.ydxdc.ui.entity.LoginInfo;
import com.app.ydxdc.ui.entity.UserInfo;
import com.app.ydxdc.ui.global.ServiceConst;
import com.google.common.collect.Maps;


import java.nio.charset.StandardCharsets;
import java.util.Map;



/**
 * 登录请求
 */
public class LoginLoader extends CommonLoader {

    public LoginLoader(Context mContext) {
        super(mContext);
    }

    /**
     * 登录
     * @param username
     * @param password
     * @param businessCallBack
     */
    public void login(String username, String password, ResponseCallback<LoginInfo> businessCallBack){
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("username",username);
        paramMap.put("password",password);
        postJson(ServiceConst.getUrl(mContext, ServiceConst.LOGIN_URL), paramMap,businessCallBack);
    }

    public void getUserInfo(ResponseCallback<UserInfo> businessCallBack){
        Map<String, Object> paramMap = Maps.newHashMap();
        get(ServiceConst.getUrl(mContext, ServiceConst.USER_INFO), paramMap,null,businessCallBack);
    }

}
