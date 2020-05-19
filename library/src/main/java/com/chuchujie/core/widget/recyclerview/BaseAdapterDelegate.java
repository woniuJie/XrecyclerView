package com.chuchujie.core.widget.recyclerview;

import android.content.Context;

/**
 * 规范IAdapterDelegate的接口必须实现默认的构造函数,否则工厂无法创建实例
 * <p/>
 * Created by wangjing on 6/9/16.
 */
public abstract class BaseAdapterDelegate<T, U> implements IAdapterDelegate<T, U> {

    private U mCallBackHolder;

    private Context mContext;

    public BaseAdapterDelegate() {
    }

    @Override
    public void setCallBackHolder(U object) {
        mCallBackHolder = object;
    }

    public U getCallBackHolder() {
        return mCallBackHolder;
    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

}
