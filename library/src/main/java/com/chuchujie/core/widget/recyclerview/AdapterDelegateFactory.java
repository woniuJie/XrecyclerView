package com.chuchujie.core.widget.recyclerview;


/**
 * AdapterDelegate的工厂类<br/>
 * <p>
 * 目前采用反射生成AdapterDelegate，如果需要考虑性能，建议采用apt的形式，在编译期间动态生成代码
 * <p>
 * Created by yedr on 2016/6/7.
 */
public class AdapterDelegateFactory {

    /**
     * 通过viewType(类名)动态加载创建、加载对应的类对象{@link IAdapterDelegate}
     *
     * @param viewType
     * @return
     */
    public static IAdapterDelegate newInstance(String viewType) {
        IAdapterDelegate result = null;

        try {
            Class clazz = Class.forName(viewType);
            result = (IAdapterDelegate) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result == null)
            throw new RuntimeException("new IAdapterDelegate Failed:" + viewType
                    + ", check you had a default constructor method.");

        return result;
    }
}
