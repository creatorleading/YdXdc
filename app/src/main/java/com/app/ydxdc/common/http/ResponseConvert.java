package com.app.ydxdc.common.http;

import com.app.common.util.GsonUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

/**
 * 封装统一响应对象转换器
 */
public class ResponseConvert<T> {

    public static ResponseConvert getInstance() {
        return new ResponseConvert();
    }

    /**
     * 将返回值转换成统一的响应对象
     *
     * @param jsonElement
     * @param rawType
     * @return
     */
    public CommonResponse<T> convertClass(JsonElement jsonElement, Type rawType) {
        CommonResponse<T> commonResponse = GsonUtils.fromJson(jsonElement, CommonResponse.class);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        //TODO 在这里写包装类型的转换，
//        if (jsonObject.has("data")) {
//            T entity = GsonUtils.fromJson(jsonObject.get("data"), rawType);
//            commonResponse.setData(entity);
//        } else if (jsonObject.has("rows")) {
//            T entity = GsonUtils.fromJson(jsonObject.get("rows"), rawType);
//            commonResponse.setData(entity);
//        }
        return commonResponse;
    }


}
