package com.chuchujie.core.widget.recyclerview;


/**
 * 全局多条目Adapter. Item实体类的接口 <br/>
 * 具体使用者，参考{@link BaseLVMultiAdapter}
 * Created by yedr on 2016/6/7.
 */
public interface IEntity {

    /**
     * 获取ViewType
     *
     * @return
     */
    String getViewType();

    /**
     * 注入实体类的ViewType<br/>
     * 当前规范：注入{@link IAdapterDelegate }实现类的类名
     *
     * @return
     */
    void setViewType(String viewType);

}
