package com.app.ydxdc.ui.global;

import android.content.Context;

import com.app.common.util.DPreference;


/**
 * 服务地址常量
 */
public final class ServiceConst {

    public static final String default_ip = "116.1.203.74";
    public static final String default_port = "8093";


    public static final String server_name = "gxdms";

//    http://1.119.169.81:10004
    /**
     * 登录
     */
    public static final String LOGIN_URL = "/login";

    /**
     * 新增保存无人机照片存储
     */
    public static final String UPLOAD_FILE = "/uavs/data/add";

    /**
     * 实时上传无人机信息
     */
    public static final String DYNAMIC_ADD = "/uavs/data/dynamicAdd";

    /**
     * 获取用户信息
     */
    public static final String USER_INFO = "/getInfo";

    /**
     * 获取无人机任务列表
     */
    public static final String WRJ_TASK = "/uavs/taskManagement/list";

    /**
     * 部门信息
     */
    public static final String GET_DEPARTMENT = "/system/depts/list";

    /**
     * 查询用户信息列表
     */
    public static final String GET_DEPARTMENT_USER = "/system/user/list";

    /**
     * 新增保存无人机任务管理
     */
    public static final String ADD_TASK = "/uavs/taskManagement/add";
    /**
     * 删除任务
     */
    public static final String DELETE_TASK = "/uavs/taskManagement";

    /**
     * 编辑任务
     */
    public static final String EDIT_TASK = "/uavs/taskManagement/edit";
    /**
     * 获取无人机列表
     */
    public static final String GET_DRONE_LIST = "/uavs/equipmentManagement/list";

    /**
     * 获取服务地址
     *
     * @param context
     * @param pathUrl
     * @return
     */
    public static String getUrl(Context context, String pathUrl) {
        String ip = DPreference.getInstance(context, XDCApplication.PREFERENCE_NAME).get("ip", ServiceConst.default_ip);
        String port = DPreference.getInstance(context, XDCApplication.PREFERENCE_NAME).get("port", ServiceConst.default_port);
        String server_name = DPreference.getInstance(context, XDCApplication.PREFERENCE_NAME).get("server_name", ServiceConst.server_name);
        return "http://" + ip + ":" + port + "/" + server_name + pathUrl;

    }


}
