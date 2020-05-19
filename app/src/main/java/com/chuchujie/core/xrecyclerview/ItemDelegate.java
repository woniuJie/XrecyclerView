package com.chuchujie.core.xrecyclerview;

import android.view.View;

import com.chuchujie.core.widget.recyclerview.BaseAdapterDelegate;
import com.chuchujie.core.widget.recyclerview.ViewHolder;

/**
 * Created by wangjing on 2018/1/3.
 */
public class ItemDelegate extends BaseAdapterDelegate<ItemData, MainActivity> {
    @Override
    public int getLayoutId() {
        return R.layout.item_view;
    }

    @Override
    public View getItemView() {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, ItemData data, int position) {
        holder.setText(R.id.textView, data.getName());
    }
}
