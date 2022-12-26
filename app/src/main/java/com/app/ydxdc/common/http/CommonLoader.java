package com.app.ydxdc.common.http;

import android.content.Context;

import com.app.common.okgo.BaseLoader;
import com.app.common.okgo.callback.EntityCallback;
import com.app.common.okgo.callback.JsonCallback;
import com.app.common.okgo.entity.BaseQuery;
import com.app.common.okgo.entity.BaseResponeEntity;
import com.app.common.okgo.utils.TypeUtils;
import com.app.common.util.GsonUtils;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 *
 */
public abstract class CommonLoader extends BaseLoader {

    protected Context mContext;

    public CommonLoader(Context context) {
        this.mContext = context;
    }

    public static String AUTHORIZATION_HEAD = "";

    /**************************   get   *******************************/

    public <T> void get(String url, Map<String, Object> paramsMap, final EntityCallback<T> entityCallback) {
        doGetJson(url, paramsMap, null, entityCallback);
    }

    public <T> void get(String url, Map<String, Object> paramsMap, Map<String, String> headers, final EntityCallback<T> entityCallback) {
        if (headers == null) {
            headers = Maps.newHashMap();
            headers.put("Authorization", AUTHORIZATION_HEAD);
        }
        doGetJson(url, paramsMap, headers, entityCallback);
    }

    /****
     * get请求，Object参数,不带附加头
     * @param url
     * @param object
     * @param entityCallback
     */
    public <T, Q extends BaseQuery> void get(String url, Q object, final EntityCallback<T> entityCallback) {
        doGetJson(url, object2Map(object), null, entityCallback);
    }

    /***
     * get请求，Object参数，带附加头
     * @param url
     * @param object
     * @param headers
     * @param entityCallback
     */
    public <T, Q extends BaseQuery> void get(String url, Q object, Map<String, String> headers, final EntityCallback<T> entityCallback) {
        doGetJson(url, object2Map(object), headers, entityCallback);
    }

    /********************************* POST ******************************************/

    /****
     * post 请求,表单提交,不带附加头
     * @param url
     * @param paramsMap
     * @param entityCallback
     */
    public <T> void postForm(String url, Map paramsMap, final EntityCallback<T> entityCallback) {
        doPostUrlParams(url, paramsMap, null, null, entityCallback);
    }

    /****
     * post 请求,表单提交,带附加头
     * @param url
     * @param paramsMap
     * @param entityCallback
     */
    public <T> void postForm(String url, Map paramsMap, Map<String, String> headers, final EntityCallback<T> entityCallback) {
        doPostUrlParams(url, paramsMap, null, headers, entityCallback);
    }

    /****
     * post 请求,表单提交,带附加头
     * @param url
     * @param object
     * @param entityCallback
     */
    public <T, Q extends BaseQuery> void postForm(String url, Q object, final EntityCallback<T> entityCallback) {
        doPostUrlParams(url, object2Map(object), null, null, entityCallback);
    }

    /****
     * post 请求,表单提交,带附加头
     * @param url
     * @param object
     * @param entityCallback
     */
    public <T, Q extends BaseQuery> void postForm(String url, Q object, Map<String, String> headers, final EntityCallback<T> entityCallback) {
        doPostUrlParams(url, object2Map(object), null, headers, entityCallback);
    }

    /****
     * post 请求，body中json提交,不带附加头
     * @param url
     * @param jsonParamsMap
     * @param entityCallback
     */
    public <T> void postJson(String url, Map jsonParamsMap, final EntityCallback<T> entityCallback) {
        doPostJson(url, jsonParamsMap, null, entityCallback);
    }


    /****
     * post 请求，body中json提交,带附加头
     * @param url
     * @param paramsMap
     * @param entityCallback
     */
    public <T> void postJson(String url, Map<String, Object> paramsMap, Map<String, String> headers, final EntityCallback<T> entityCallback) {
        if (headers == null) {
            headers = Maps.newHashMap();
            headers.put("Authorization", AUTHORIZATION_HEAD);
        }
        doPostJson(url, paramsMap, headers, entityCallback);
    }

    /****
     * post 请求，body中map提交,带附加头
     * @param url
     * @param paramsMap
     * @param entityCallback
     */
    public <T> void post(String url, Map<String, Object> paramsMap, Map<String, String> headers, final EntityCallback<T> entityCallback) {
        doPost(url, paramsMap, headers, entityCallback);
    }

    /****
     * post 请求，body中json提交,不带附加头
     * @param url
     * @param object
     * @param entityCallback
     */
    public <T, Q extends BaseQuery> void postJson(String url, Q object, final EntityCallback<T> entityCallback) {
        doPostJson(url, object2Map(object), null, entityCallback);
    }


    /****
     * post 请求，body中json提交,带附加头
     * @param url
     * @param object
     * @param entityCallback
     */
    public <T, Q extends BaseQuery> void postJson(String url, Q object, Map<String, String> headers, final EntityCallback<T> entityCallback) {
        doPostJson(url, object2Map(object), headers, entityCallback);
    }

    /****
     * post 请求，body中json提交,不带附加头
     * @param url
     * @param jsonParamsMap
     * @param entityCallback
     */
    public <T> void postJson(String url, JsonObject jsonParamsMap, final EntityCallback<T> entityCallback) {
        doPostJson(url, object2Map(jsonParamsMap), null, entityCallback);
    }

    /***
     * post 请求，
     * @param url
     * @param urlParamsMap  url地址后接的参数
     * @param jsonObject
     * @param entityCallback
     */
    public <T, Q extends BaseQuery> void postUrlParams(String url,
                                                       Map<String, Object> urlParamsMap, Q jsonObject,
                                                       final EntityCallback<T> entityCallback) {
        Map jsonMap = object2Map(jsonObject);
        doPostUrlParams(url, urlParamsMap, jsonMap, null, entityCallback);
    }


    /***
     * post 请求，
     * @param url
     * @param urlParamsMap  url地址后接的参数
     * @param jsonMap
     * @param callback
     */
    public <T> void postUrlParams(String url,
                                  Map<String, Object> urlParamsMap, Map<String, Object> jsonMap,
                                  final EntityCallback<T> callback) {
        doPostUrlParams(url, urlParamsMap, jsonMap, null, callback);
    }

    /***
     * post 请求，
     * @param url
     * @param urlParamsObject  url地址后接的参数
     * @param jsonObject
     * @param entityCallback
     */
    public <T, Q extends BaseQuery> void postUrlParams(String url,
                                                       Object urlParamsObject, Q jsonObject,
                                                       final EntityCallback<T> entityCallback) {
        Map urlMap = object2Map(urlParamsObject);
        Map jsonMap = object2Map(jsonObject);
        doPostUrlParams(url, urlMap, jsonMap, null, entityCallback);
    }

    /***
     * post 请求，
     * @param url
     * @param urlParamsObject  url地址后接的参数
     * @param jsonMap
     * @param entityCallback
     */
    public <T, Q extends BaseQuery> void postUrlParams(String url,
                                                       Q urlParamsObject, Map<String, Object> jsonMap,
                                                       final EntityCallback<T> entityCallback) {
        Map urlMap = object2Map(urlParamsObject);
        doPostUrlParams(url, urlMap, jsonMap, null, entityCallback);
    }

    /***
     * post 请求，
     * @param url
     * @param urlParamsMap  url地址后接的参数
     * @param jsonObject
     * @param headers
     * @param entityCallback
     */
    public <T> void postUrlParams(String url,
                                  Map<String, Object> urlParamsMap, Object jsonObject, Map<String, String> headers,
                                  final EntityCallback<T> entityCallback) {
        Map jsonMap = object2Map(jsonObject);
        doPostUrlParams(url, urlParamsMap, jsonMap, headers, entityCallback);
    }

    /***
     * post 请求，
     * @param url
     * @param urlParamsMap  url地址后接的参数
     * @param jsonMap
     * @param headers
     * @param entityCallback
     */
    public <T> void postUrlParams(String url,
                                  Map<String, Object> urlParamsMap, Map<String, Object> jsonMap, Map<String, String> headers,
                                  final EntityCallback<T> entityCallback) {
        doPostUrlParams(url, urlParamsMap, jsonMap, headers, entityCallback);
    }



    /***
     * post 请求，
     * @param url
     * @param keyName
     * @param fileList
     * @param urlParamsMap  url地址后接的参数
     * @param jsonParamsMap
     * @param entityCallback
     */
    public <T> void postFiles(String url,
                              String keyName,
                              List<File> fileList,
                              Map<String, Object> urlParamsMap,
                              Map<String, Object> jsonParamsMap,
                              Map<String, String> headers,
                              final EntityCallback<T> entityCallback) {
        doPostFiles(url, keyName, fileList, urlParamsMap, jsonParamsMap, headers, entityCallback);
    }

    public <T> void delete(String url, final EntityCallback<T> entityCallback) {
        Callback callback= new JsonCallback<JsonElement>() {
            @Override
            public void onSuccess(Response<JsonElement> response) {
                getSuccess(response.body(), entityCallback);
            }

            @Override
            public void onError(Response<JsonElement> response) {
                entityCallback.onFail(new Exception(response.getException()));
            }
        };
        OkGo.<BaseResponeEntity<String>>delete(url).tag(this).execute(callback);


    }

    /*********************************  以下为私有方法 *********************************/

    private <T> void doGetJson(String url, Map<String, Object> paramsMap, Map<String, String> headers, final EntityCallback<T> entityCallback) {
        get(url, paramsMap, headers, new JsonCallback<JsonElement>() {
            @Override
            public void onSuccess(Response<JsonElement> response) {
                getSuccess(response.body(), entityCallback);//todo  111
            }

            @Override
            public void onError(Response<JsonElement> response) {
                entityCallback.onFail(new Exception(response.getException()));
            }
        });
    }


    /****
     * post 请求，body中json提交,带附加头
     * @param url
     * @param jsonParamsMap
     * @param entityCallback
     */
    private <T> void doPostJson(String url, Map jsonParamsMap, Map<String, String> headers,
                                final EntityCallback<T> entityCallback) {
        postJson(url, jsonParamsMap, headers, new JsonCallback<JsonElement>() {
            @Override
            public void onSuccess(Response<JsonElement> response) {
                getSuccess(response.body(), entityCallback);
            }

            @Override
            public void onError(Response<JsonElement> response) {
                entityCallback.onFail(new Exception(response.getException()));
            }
        });
    }

    /****
     * post 请求，body中map提交,带附加头
     * @param url
     * @param jsonParamsMap
     * @param entityCallback
     */
    private <T> void doPost(String url,
                            Map jsonParamsMap,
                            Map<String, String> headers,
                            final EntityCallback<T> entityCallback) {
        post(url, jsonParamsMap, headers, new JsonCallback<JsonElement>() {
            @Override
            public void onSuccess(Response<JsonElement> response) {
                getSuccess(response.body(), entityCallback);
            }

            @Override
            public void onError(Response<JsonElement> response) {
                entityCallback.onFail(new Exception(response.getException()));
            }
        });
    }

    /***
     * post 请求，
     * @param url
     * @param urlParamsMap  url地址后接的参数
     * @param headers
     * @param entityCallback
     */
    private <T> void doPostUrlParams(String url,
                                     Map<String, Object> urlParamsMap,
                                     Map<String, Object> jsonMap,
                                     Map<String, String> headers,
                                     final EntityCallback<T> entityCallback) {
        postForm(url, urlParamsMap, jsonMap, headers, new JsonCallback<JsonElement>() {
            @Override
            public void onSuccess(Response<JsonElement> response) {
                getSuccess(response.body(), entityCallback);
            }

            @Override
            public void onError(Response<JsonElement> response) {
                entityCallback.onFail(new Exception(response.getException()));
            }
        });
    }

    /***
     * post 请求，
     * @param url
     * @param keyName
     * @param fileList 文件集合
     * @param urlParamsMap  url地址后接的参数
     * @param jsonParamsMap  url地址后接的参数
     * @param headers
     * @param entityCallback
     */
    private <T> void doPostFiles(String url,
                                 String keyName,
                                 List<File> fileList,
                                 Map<String, Object> urlParamsMap,
                                 Map<String, Object> jsonParamsMap,
                                 Map<String, String> headers,
                                 final EntityCallback<T> entityCallback) {
        postFiles(url, keyName, fileList, urlParamsMap, jsonParamsMap, headers, new JsonCallback<JsonElement>() {
            @Override
            public void onSuccess(Response<JsonElement> response) {
                getSuccess(response.body(), entityCallback);
            }

            @Override
            public void onError(Response<JsonElement> response) {
                entityCallback.onFail(new Exception(response.getException()));
            }
        });
    }


    /**
     * 响应成功数据转换
     *
     * @param jsonElement
     * @param entityCallback
     * @param <T>
     */
    private <T> void getSuccess(JsonElement jsonElement, EntityCallback<T> entityCallback) {
        try {
            Type rawType = TypeUtils.getClassType(entityCallback, 0);
            T entity;
            if (entityCallback instanceof ResponseCallback) {
                CommonResponse<T> commonResponse = ResponseConvert.getInstance().convertClass(jsonElement, rawType);
                //TODO 这里可以统一响应对象的状态码处理
                if (commonResponse.getCode() == 200) {
                    entity = GsonUtils.fromJson(jsonElement, rawType);
                    entityCallback.onSuccess(entity);

                } else {
                    entityCallback.onFail(new Exception(commonResponse.getMsg()));
                }
            } else {
            }

            /*
            Object object = ResponseConvert.getInstance().CoverClass(response.body(), entityCallback);
            if (object != null) {
                if (object instanceof CommonResponse) {
                    entityCallback.onSuccess((T) ((CommonResponse) object).data);
                    return;
                }
                if (object instanceof JsonElement) {
                    entityCallback.onSuccess((T) object);
                    return;
                }
                doMain.onFailed(new Exception("没有合适的类型"));
            }
            //todo...
            doMain.onFailed(new Exception("没有合适的类型"));*/
        } catch (Exception e) {
            entityCallback.onFail(e);
        }
    }

}
