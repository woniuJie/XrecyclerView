package com.chuchujie.core.xrecyclerview;

import com.chuchujie.core.widget.recyclerview.IEntity;

/**
 * Created by wangjing on 2018/1/3.
 */
public class ItemData implements IEntity {

    private String name;

    public ItemData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getViewType() {
        return ItemDelegate.class.getName();
    }

    @Override
    public void setViewType(String viewType) {

    }
}
