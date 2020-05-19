package com.chuchujie.core.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;

/**
 * Created by wangjing on 6/7/16.
 */
public class HeaderFooterRVAdapter<T> extends BaseRVAdapter<T> {

    private BaseRVAdapter mWrappedAdapter;

    private BridgeObserver mBridgeObserver;

    private static final int TYPE_HEADER = -1;

    private static final int TYPE_FOOTER = -2;

    private static final int INVALID_LAYOUT_ID = -1;

    private LinearLayout mHeaderContainer;

    private LinearLayout mFooterContainer;

    public HeaderFooterRVAdapter(Context context, BaseRVAdapter adapter) {
        super(context);
        mWrappedAdapter = adapter;
        mBridgeObserver = new BridgeObserver(this);
        mWrappedAdapter.registerAdapterDataObserver(mBridgeObserver);
        super.setHasStableIds(mWrappedAdapter.hasStableIds());

//        initHeaderFooter();
    }

    private void initHeaderFooter() {
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        mHeaderContainer = new LinearLayout(mContext);
//        mHeaderContainer.setOrientation(LinearLayout.VERTICAL);
////        mHeaderContainer.setLayoutParams(layoutParams);
//        mFooterContainer = new LinearLayout(mContext);
//        mFooterContainer.setOrientation(LinearLayout.VERTICAL);
//        mFooterContainer.setLayoutParams(layoutParams);
    }

    public boolean isWrappedAdapterAlive() {
        return mWrappedAdapter != null;
    }

    public void release() {
        onRelease();

        if (mWrappedAdapter != null && mBridgeObserver != null) {
            mWrappedAdapter.unregisterAdapterDataObserver(mBridgeObserver);
        }

        mWrappedAdapter = null;
        mBridgeObserver = null;
    }

    protected void onRelease() {
        // override this method if needed
    }

    public BaseRVAdapter getWrappedAdapter() {
        return mWrappedAdapter;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (isWrappedAdapterAlive())
            getWrappedAdapter().onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        if (isWrappedAdapterAlive())
            getWrappedAdapter().onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        if (isWrappedAdapterAlive())
            getWrappedAdapter().onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        if (isWrappedAdapterAlive())
            getWrappedAdapter().onViewDetachedFromWindow(holder);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        if (isWrappedAdapterAlive())
            getWrappedAdapter().onViewRecycled(holder);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        if (isWrappedAdapterAlive())
            getWrappedAdapter().setHasStableIds(hasStableIds);
    }

    @Override
    public int getItemCount() {
        int itemCount;
        if (mHeaderContainer != null && mFooterContainer != null ) {
           itemCount =  isWrappedAdapterAlive() ? getWrappedAdapter().getItemCount() + 2 : 2;
        } else if (mHeaderContainer == null && mFooterContainer == null) {
            itemCount =  isWrappedAdapterAlive() ? getWrappedAdapter().getItemCount() : 0;
        } else {
            itemCount =  isWrappedAdapterAlive() ? getWrappedAdapter().getItemCount() + 1 : 1;
        }
        return itemCount;
    }

    @Override
    public long getItemId(int position) {
        return getWrappedAdapter().getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && mHeaderContainer != null)
            return TYPE_HEADER;
        else if (position == (getItemCount() - 1) && mFooterContainer != null)
            return TYPE_FOOTER;
        else
            return mHeaderContainer == null ? getWrappedAdapter().getItemViewType(position) : getWrappedAdapter().getItemViewType(position - 1);
    }

    @Override
    public int getLayoutId(int viewType) {
        if (viewType == TYPE_HEADER) {
            return INVALID_LAYOUT_ID;
        } else if (viewType == TYPE_FOOTER) {
            return INVALID_LAYOUT_ID;
        } else {
            return getWrappedAdapter().getLayoutId(viewType);
        }
    }

    @Override
    public View getItemView(int viewType) {
        if (viewType == TYPE_HEADER) {
            return mHeaderContainer;
        } else if (viewType == TYPE_FOOTER) {
            return mFooterContainer;
        } else {
            return getWrappedAdapter().getItemView(viewType);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0 && mHeaderContainer != null) {
            onBindHeaderView(holder);
        } else if (position == (getItemCount() - 1) && mFooterContainer != null) {
            onBindFooterView(holder);
        } else {
            if (mHeaderContainer == null) {
                getWrappedAdapter().onBindViewHolder(holder, position);
            } else {
                getWrappedAdapter().onBindViewHolder(holder, position - 1);
            }

        }
    }

    public void addHeaderView(View headerView) {
        if (mHeaderContainer == null) {
            mHeaderContainer = new LinearLayout(mContext);
            mHeaderContainer.setOrientation(LinearLayout.VERTICAL);
        }
        mHeaderContainer.addView(headerView);
    }

    public void removeHeaderView(View headerView) {
        mHeaderContainer.removeView(headerView);
        if (mHeaderContainer.getChildCount() <= 0) {
            mHeaderContainer = null;
        }
    }

    public void removeAllHeaderView() {
        mHeaderContainer.removeAllViews();
    }

    public void addFooterView(View footerView) {
        if (mFooterContainer == null) {
            mFooterContainer = new LinearLayout(mContext);
            mFooterContainer.setOrientation(LinearLayout.VERTICAL);
        }
        mFooterContainer.addView(footerView);
    }

    public void removeFooterView(View footerView) {
        mFooterContainer.removeView(footerView);
        if (mFooterContainer.getChildCount() <= 0) {
            mFooterContainer = null;
        }

    }

    public void removeAllFooterView() {
        mFooterContainer.removeAllViews();
    }

    protected void onBindHeaderView(ViewHolder holder) {

    }

    protected void onBindFooterView(ViewHolder holder) {

    }

    private final class BridgeObserver extends RecyclerView.AdapterDataObserver {
        private final WeakReference<BaseRVAdapter> mRefHolder;

        public BridgeObserver(BaseRVAdapter holder) {
            mRefHolder = new WeakReference<>(holder);
        }

        @Override
        public void onChanged() {
            final BaseRVAdapter holder = mRefHolder.get();
            if (holder != null) {
                holder.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            final BaseRVAdapter holder = mRefHolder.get();
            if (holder != null) {
                holder.notifyItemRangeChanged(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            final BaseRVAdapter holder = mRefHolder.get();
            if (holder != null) {
                holder.notifyItemRangeChanged(positionStart, itemCount, payload);
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            final BaseRVAdapter holder = mRefHolder.get();
            if (holder != null) {
                holder.notifyItemRangeInserted(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            final BaseRVAdapter holder = mRefHolder.get();
            if (holder != null) {
                holder.notifyItemRangeRemoved(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            final BaseRVAdapter holder = mRefHolder.get();

            if (holder != null) {
                holder.notifyItemRangeRemoved(fromPosition, toPosition);
            }
        }
    }

}
