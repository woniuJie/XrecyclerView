package com.chuchujie.core.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wangjing on 6/7/16.
 */
public abstract class BaseRVAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    protected Context mContext;

    protected List<T> mDataList;

    public BaseRVAdapter(Context context) {
        this(context, Collections.<T>emptyList());
    }

    public BaseRVAdapter(Context context, List<T> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public void setDataList(List<T> dataList) {
        this.mDataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (getLayoutId(viewType) > 0) {
            return ViewHolder.createForRV(mContext, parent, getLayoutId(viewType));
        } else if (getItemView(viewType) != null) {
            return ViewHolder.createForRV(getItemView(viewType));
        } else {
            return createFallBackViewHolder();
        }
    }

    /**
     * 刷新数据
     *
     * @param newDatas
     */
    public void refreshData(List<T> newDatas) {
        if (mDataList != null) {
            mDataList.clear();
        } else {
            mDataList = new ArrayList<>();
        }
        mDataList.addAll(newDatas);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param newDatas
     */
    public void addData(List<T> newDatas) {
        if (mDataList != null) {
            mDataList.addAll(newDatas);
            notifyDataSetChanged();
        }
    }

    /**
     * 移除某项数据
     * @param position
     */
    public void removeData(int position){
        if (mDataList != null && position >= 0){
            if (position > mDataList.size() -1)
                return;
            mDataList.remove(position);
            notifyDataSetChanged();
        }
    }

    /**
     * 创建一个默认的viewholder
     *
     * @return
     */
    private ViewHolder createFallBackViewHolder() {
        TextView textView = new TextView(mContext);
        textView.setText("you must return either getLayoutId() or getItemView().");
        return ViewHolder.createForRV(textView);
    }

    /**
     * 获取item的布局layoutid
     *
     * @param viewType
     * @return
     */
    public abstract int getLayoutId(int viewType);

    /**
     * 获取item的view，主要用于自定义view或者简单的view
     *
     * @param viewType
     * @return
     */
    public abstract View getItemView(int viewType);

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public T getItem(int position) {
        return mDataList.get(position);
    }

}
