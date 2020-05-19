package com.chuchujie.core.widget.recyclerview;

/**
 * Created by wangjing on 8/17/16.
 */
public interface IViewTypeGenerator {

    /**
     * 重新生成ViewType
     *
     * @param reCreateViewType
     */
    void setNeedReCreateViewType(boolean reCreateViewType);

    /**
     * 生成ViewType
     *
     * @param viewType
     * @return
     */
    int createViewType(String viewType);

    /**
     * 根据ViewType获取对应的键值
     *
     * @param viewType
     * @return
     */
    String getKeyByValue(int viewType);

}
