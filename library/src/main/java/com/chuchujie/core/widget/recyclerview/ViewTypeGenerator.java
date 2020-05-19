package com.chuchujie.core.widget.recyclerview;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangjing on 8/17/16.
 */
public class ViewTypeGenerator implements IViewTypeGenerator {

    private Map<String, Integer> mIntViewTypeCache = new HashMap<>();

    private Map<Integer, String> mStrViewTypeCache = new HashMap<>();

    private boolean mReCreateViewType;

    private int nextViewType;

    public ViewTypeGenerator() {
    }

    @Override
    public void setNeedReCreateViewType(boolean reCreateViewType) {
        this.mReCreateViewType = reCreateViewType;
    }

    @Override
    public int createViewType(String viewType) {
        if (mReCreateViewType) {
            mIntViewTypeCache.clear();
            mStrViewTypeCache.clear();
        }
        Integer resultViewType = mIntViewTypeCache.get(viewType);
        if (resultViewType == null) {
            resultViewType = nextViewType++;
            mIntViewTypeCache.put(viewType, resultViewType.intValue());
            mStrViewTypeCache.put(resultViewType.intValue(), viewType);
        }
        return resultViewType.intValue();
    }

    @Override
    public String getKeyByValue(int viewType) {
        return mStrViewTypeCache.get(viewType);
    }

}
