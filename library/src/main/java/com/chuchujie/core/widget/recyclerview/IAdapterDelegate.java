package com.chuchujie.core.widget.recyclerview;

import android.content.Context;
import android.view.View;

/**
 * 全局AdapterDelegate接口，统一代理{@link android.widget.ListView} {@link android.support.v7.widget.RecyclerView}的Adapter
 * <p/>
 * Note：采用这种“委托”手法的目的 <br/>
 * 1). 统一单条目、多条目的实现 <br/>
 * 2). 统一ListView、RecyclerView的Adapter实现
 * <p/>
 * Created by yedr on 2016/6/7.
 */
public interface IAdapterDelegate<T, U> {

    // 没有必要让业务层实现ViewType；
    // 两种方案： 用getLayoutId()来替代
    // 如果有问题； 考虑在Adapter自身做ViewType缓存，比如Map<String, Interger>的方式来缓存
//    /**
//     * 设置ItemViewType，用于ListView的标记回收
//     *
//     * @return
//     */
//    int getViewType();

    /**
     * 返回Item对应的布局资源, getLayoutId()和getItemView()只需要实现一个方法
     *
     * @return
     */
    int getLayoutId();

    /**
     * 直接返回一个ItemView, getLayoutId()和getItemView()只需要实现一个方法
     *
     * @return
     */
    View getItemView();

    /**
     * 设置回调对象，一般传presenter
     *
     * @param callBackHolder
     */
    void setCallBackHolder(U callBackHolder);

    /**
     * 回调函数，绑定数据到ListView&RecylerView的条目
     *
     * @param holder
     * @param data
     * @param position
     */
    void onBindViewHolder(ViewHolder holder, T data, int position);

    /**
     * 设置上下文
     * @param context
     */
    void setContext(Context context);
}
