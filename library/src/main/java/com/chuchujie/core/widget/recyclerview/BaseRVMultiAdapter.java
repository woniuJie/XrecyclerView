package com.chuchujie.core.widget.recyclerview;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.view.View;

import java.util.List;

/**
 * 多条目Adapter的统一实现类；具体ItemView的实现，需要实现{@link IAdapterDelegate}接口
 * <p/>
 * Created by wangjing on 2016/6/7.
 */
public class BaseRVMultiAdapter<T extends IEntity, U> extends BaseRVAdapter<T> {

    /**
     * 回调的处理者
     */
    private U mCallBackHolder;

    /**
     * IAdapterDelegate的缓存：否则每次newInstance AdapterDelegate 性能低下
     * <p/>
     * adapter的viewtype, 对应mStrViewTypeCache
     */
    private ArrayMap<Integer, IAdapterDelegate<T, U>> mIntViewTypeCache = new ArrayMap<>();

    /**
     * ViewType的工厂
     */
    private IViewTypeGenerator mViewTypeGenerator;

    /**
     * 构造函数
     *
     * @param context
     * @param dataList
     */
    public BaseRVMultiAdapter(Context context, List<T> dataList) {
        super(context, dataList);
    }

    /**
     * 获取ViewType的工厂
     *
     * @return
     */
    public IViewTypeGenerator getViewTypeGenerator() {
        if (mViewTypeGenerator == null)
            mViewTypeGenerator = onCreateViewTypeGenerator();
        return mViewTypeGenerator;
    }

    /**
     * 创建ViewType工厂
     *
     * @return
     */
    protected IViewTypeGenerator onCreateViewTypeGenerator() {
        return new ViewTypeGenerator();
    }

    /**
     * 设置回调的持有者
     *
     * @param callBackHolder
     */
    public void setCallBackHolder(U callBackHolder) {
        this.mCallBackHolder = callBackHolder;
    }

    @Override
    public int getItemViewType(int position) {
        // 从ViewTypeGenerator工厂中获取ViewType
        return getViewTypeGenerator().createViewType(getItem(position).getViewType());
    }

    @Override
    public int getLayoutId(int viewType) {
        return getAdapterDelegate(viewType).getLayoutId();
    }

    @Override
    public View getItemView(int viewType) {
        return getAdapterDelegate(viewType).getItemView();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        getAdapterDelegate(getItemViewType(position))
                .onBindViewHolder(holder, getDataList().get(position), position);
    }

    /**
     * 代理器,通过代理去处理ViewHolder
     *
     * @param viewType
     * @return
     */
    protected IAdapterDelegate<T, U> getAdapterDelegate(int viewType) {
        IAdapterDelegate<T, U> result = mIntViewTypeCache.get(viewType);
        if (null == result) {
            result = AdapterDelegateFactory.newInstance(
                    getViewTypeGenerator().getKeyByValue(viewType));
            result.setCallBackHolder(mCallBackHolder);
            result.setContext(mContext);
            mIntViewTypeCache.put(viewType, result);
        }
        return result;
    }

}
