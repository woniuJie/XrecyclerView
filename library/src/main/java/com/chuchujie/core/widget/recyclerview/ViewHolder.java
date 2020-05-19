package com.chuchujie.core.widget.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * RecyclerView和ListView统一使用一个ViewHolder
 * <p/>
 * Created by wangsai on 2015/8/10.
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    /**
     * 为ItemView中子view做的cache
     */
    private SparseArray<View> mCacheViews;

    private Context mContext;

    public ViewHolder(View itemView, Context context) {
        super(itemView);
        mCacheViews = new SparseArray<>();
        this.mContext = context;
    }

    /**
     * get app context
     *
     * @return
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * 为RecyclerView创建ViewHolder
     *
     * @param context
     * @param parent
     * @param layoutId
     * @return
     */
    public static ViewHolder createForRV(Context context, ViewGroup parent, int layoutId) {
        if (context == null)
            throw new IllegalArgumentException("context can't be null");
        if (parent == null)
            throw new IllegalArgumentException("parent can't be null");
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutId, parent, false);
        return new ViewHolder(view, context);
    }

    /**
     * 为RecyclerView创建ViewHolder
     *
     * @param itemView
     * @return
     */
    public static ViewHolder createForRV(View itemView) {
        if (itemView == null)
            throw new IllegalArgumentException("itemView can't be null");
        return new ViewHolder(itemView, itemView.getContext());
    }

//    /**
//     * 为ListView创建ViewHolder
//     *
//     * @param context
//     * @param convertView
//     * @param parent
//     * @param layoutId
//     * @return
//     */
//    public static ViewHolder createForLV(Context context, View convertView,
//                                         ViewGroup parent, int layoutId) {
//        if (convertView == null) {
//            LayoutInflater inflater = LayoutInflater.from(context);
//            View viewItem = inflater.inflate(layoutId, parent, false);
//            ViewHolder viewHolder = new ViewHolder(viewItem, context);
//            viewItem.setTag(R.id.item_holder, viewHolder);
//            return viewHolder;
//        } else {
//            return (ViewHolder) convertView.getTag(R.id.item_holder);
//        }
//    }

    /**
     * 获取ItemView
     *
     * @return
     */
    public View getItemView() {
        return itemView;
    }

    /**
     * 根据id获取View
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int id) {
        View view = mCacheViews.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            mCacheViews.put(id, view);
        }
        return (T) view;
    }

    public ViewHolder setText(TextView textView,CharSequence sequence){
        if (textView != null){
            textView.setText(sequence);
        }
        return this;
    }

    public ViewHolder setText(int id, CharSequence sequence) {
        TextView view = getView(id);
        if (view != null)
            view.setText(sequence);
        return this;
    }

    public ViewHolder setTextColor(int id, String colorStr) {
        TextView view = getView(id);
        if (view != null && !TextUtils.isEmpty(colorStr))
            view.setTextColor(Color.parseColor(colorStr));
        return this;
    }

    public ViewHolder setTextColor(int id, int colorId) {
        TextView view = getView(id);
        if (view != null)
            view.setTextColor(view.getResources().getColor(colorId));
        return this;
    }

    public ViewHolder setText(int id, int resId) {
        TextView view = getView(id);
        if (view != null)
            view.setText(resId);
        return this;
    }

    public ViewHolder setBackgroundColor(int id, String colorStr) {
        View view = getView(id);
        if (view != null && !TextUtils.isEmpty(colorStr))
            view.setBackgroundColor(Color.parseColor(colorStr));
        return this;
    }

    public ViewHolder setBackgroundColor(int id, int colorId) {
        View view = getView(id);
        if (view != null)
            view.setBackgroundColor(view.getResources().getColor(colorId));
        return this;
    }

    public ViewHolder setImageResource(int id, int resId) {
        ImageView iv = getView(id);
        if (iv != null)
            iv.setImageResource(resId);
        return this;
    }

//    public ViewHolder setImageUrl(int viewId, String url) {
//        ImageLoader.getInstance().display((CustomImageView) getView(viewId), url);
//        return this;
//    }
//
//    public ViewHolder setImageUrl(int viewId, String url, int defaultResId) {
//        ImageLoader.getInstance().display((CustomImageView) getView(viewId), url, defaultResId);
//        return this;
//    }
//
//    public ViewHolder setImageUrl(int viewId, String url, int defaultResId,
//                                  int width, int hwScale) {
//        ImageLoader.getInstance().display((CustomImageView) getView(viewId), url,
//                defaultResId, width, hwScale);
//        return this;
//    }
//
//    public ViewHolder setAlpha(int viewId, int alpha) {
//        ViewUtils.setAlpha(getView(viewId), alpha);
//        return this;
//    }

    public ViewHolder setOnClickListener(int viewId, View.OnClickListener clickListener) {
        getView(viewId).setOnClickListener(clickListener);
        return this;
    }

    public ViewHolder setOnLongClickListener(int viewId,
                                             View.OnLongClickListener longClickListener) {
        getView(viewId).setOnLongClickListener(longClickListener);
        return this;
    }

    public ViewHolder setOnTouchListener(int viewId, View.OnTouchListener touchListener) {
        getView(viewId).setOnTouchListener(touchListener);
        return this;
    }

    public ViewHolder show(int id) {
        View view = getView(id);
        if (view != null)
            view.setVisibility(View.VISIBLE);
        return this;
    }

    public ViewHolder hide(int id) {
        View view = getView(id);
        if (view != null)
            view.setVisibility(View.GONE);
        return this;
    }

}
