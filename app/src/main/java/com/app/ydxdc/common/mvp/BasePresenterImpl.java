package com.app.ydxdc.common.mvp;

import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * BasePresenterImpl
 * MVP Presenter实现类
 *
 */
public abstract class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {

    private SoftReference<BaseView> mReferenceView;
    private V mProxyView;

    @Override
    @SuppressWarnings("unchecked")
    public void attach(V view) {
        mReferenceView = new SoftReference<>(view);
        //处理子类未实现接口导致动态代理转换异常
        List<Class<?>> superClazzList = new ArrayList<>();
        getAllInterfaces(view.getClass(), superClazzList);
        Class<?>[] interfaces = superClazzList.toArray(new Class<?>[superClazzList.size()]);

        mProxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                if (mReferenceView == null || mReferenceView.get() == null) {
                    return null;
                }
                return method.invoke(mReferenceView.get(), objects);
            }
        });
    }

    public V getView() {
        return mProxyView;
    }

    @Override
    public void detach() {
        mReferenceView.clear();
        mReferenceView = null;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    private static void getAllInterfaces(Class<?> clazz, List<Class<?>> interfaceList) {
        if (clazz == null) {
            return;
        }
        Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> interfaceCls : interfaces) {
            if(interfaceCls.getName().startsWith("android")){
                //忽略原生接口
                continue;
            }
            if(!interfaceList.contains(interfaceCls)){
                interfaceList.add(interfaceCls);
            }
        }
        if(clazz.getSuperclass() != null){
            getAllInterfaces(clazz.getSuperclass(),interfaceList);
        }
    }
}
