package com.chuchujie.core.widget.recyclerview;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * RecyclerView的item的点击事件封装
 * Created by wangjing on 6/6/16.
 */
public class OnRVItemClickListener implements RecyclerView.OnItemTouchListener {

    private OnRvItemClickListener mListener;

    private GestureDetectorCompat mGestureDetectorCompat;

    public OnRVItemClickListener(OnRvItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (mGestureDetectorCompat == null) {
            mGestureDetectorCompat = new GestureDetectorCompat(rv.getContext(),
                    new ItemTouchHelperGestureListener(rv));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        if (mGestureDetectorCompat != null)
            mGestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public interface OnRvItemClickListener {

        void onItemClick(RecyclerView recyclerView, ViewHolder viewHolder, int position);

        void onItemLongClick(RecyclerView recyclerView, ViewHolder viewHolder, int position);

        void onItemDoubleClick(RecyclerView recyclerView, ViewHolder viewHolder, int position);

    }

    public class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {

        private RecyclerView mRecyclerView;

        private boolean checkNull() {
            return mListener == null;
        }

        public ItemTouchHelperGestureListener(RecyclerView rv) {
            this.mRecyclerView = rv;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (checkNull())
                return super.onSingleTapUp(e);
            View childView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childView != null) {
                mRecyclerView.getChildViewHolder(childView);
                ViewHolder viewHolder = (ViewHolder) mRecyclerView.getChildViewHolder(childView);
                int position = mRecyclerView.getChildAdapterPosition(childView);
                mListener.onItemClick(mRecyclerView, viewHolder, position);
                return true;
            }
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            if (checkNull())
                return;
            View childView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childView != null) {
                mRecyclerView.getChildViewHolder(childView);
                ViewHolder viewHolder = (ViewHolder) mRecyclerView.getChildViewHolder(childView);
                int position = mRecyclerView.getChildAdapterPosition(childView);
                mListener.onItemLongClick(mRecyclerView, viewHolder, position);
            }
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (checkNull())
                return super.onDoubleTap(e);
            View childView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childView != null) {
                mRecyclerView.getChildViewHolder(childView);
                ViewHolder viewHolder = (ViewHolder) mRecyclerView.getChildViewHolder(childView);
                int position = mRecyclerView.getChildAdapterPosition(childView);
                mListener.onItemDoubleClick(mRecyclerView, viewHolder, position);
                return true;
            }
            return super.onDoubleTap(e);
        }

    }

}
