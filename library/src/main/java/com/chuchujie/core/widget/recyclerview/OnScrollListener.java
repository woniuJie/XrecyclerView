package com.chuchujie.core.widget.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * RecyclerView 滚动监听器
 * Created by wangjing on 4/20/16.
 */
public class OnScrollListener extends RecyclerView.OnScrollListener {

    /**
     * 回调
     */
    private RVOnScrollListener mOnScrollListener;

    /**
     * 滑动敏感值,主要用于一件置顶
     */
    private static final int SCROLL_SENSITIVITY = 10;

    /**
     * 统计时间默认间隔
     */
    private static final long FIVE_SECONDS = 5 * 1000;

    /**
     * 倒数第几个position可见默认值
     */
    private static final int BACKWARD_VISIBLE_POSITION = 5;

    /**
     * 倒数第几个position可见
     */
    private int mBackWardVisiblePosition = BACKWARD_VISIBLE_POSITION;

    /**
     * 上次统计的时间
     */
    private long mLastRecordTime;

    /**
     * 统计时间频率
     */
    private long mRecordFrequency = FIVE_SECONDS;

    /**
     * 上次拉到的最大item的位置
     */
    private int mMaxLastVisiblePosition = 0;

    /**
     * 最后一个和第一个可见item的position
     */
    private int[] mFlVisibleItemPosition;

    /**
     * 上一次滑动状态的记录
     */
    private int mPreScrollState;

    public OnScrollListener() {
        this(null);
    }

    public OnScrollListener(RVOnScrollListener onScrollListener) {
        this(onScrollListener, BACKWARD_VISIBLE_POSITION, FIVE_SECONDS);
    }

    public OnScrollListener(RVOnScrollListener onScrollListener,
                            int backWardVisiblePosition,
                            long recordFrequency) {
        this.mOnScrollListener = onScrollListener;
        this.mBackWardVisiblePosition = backWardVisiblePosition;
        this.mRecordFrequency = recordFrequency;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                handleBackWardPositionVisible(recyclerView, mFlVisibleItemPosition);
                startLoadImg();
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                // 如果上一次是自然滚动的话，就停止加载
                if (mPreScrollState == RecyclerView.SCROLL_STATE_SETTLING) {
                    stoploadImg();
                } else {
                    startLoadImg();
                }
                break;
            case RecyclerView.SCROLL_STATE_SETTLING:
                stoploadImg();
                break;
        }
        mPreScrollState = newState;
    }

    private void startLoadImg() {
//        if (Fresco.getImagePipeline().isPaused()) {
//            Fresco.getImagePipeline().resume();
//        }
    }

    private void stoploadImg() {
//        if (!Fresco.getImagePipeline().isPaused()) {
//            Fresco.getImagePipeline().pause();
//        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (mOnScrollListener == null)
            return;
        // 获取第一个和最后一个可见item的位置
        mFlVisibleItemPosition = getFLVisibleItemPosition(recyclerView.getLayoutManager());
        // 处理统计
        handlePV(mFlVisibleItemPosition, handleScrollUpDown(recyclerView, dx, dy));
        mOnScrollListener.onScrolled(recyclerView, dx, dy);
    }

    /**
     * 下拉刷新后重置最大的最后一个可见item的position为0
     */
    public void resetMaxLastVisiblePosition() {
        mMaxLastVisiblePosition = 0;
    }

    /**
     * 设置统计频率
     *
     * @param recordFrequency
     */
    public void setRecordFrequency(long recordFrequency) {
        this.mRecordFrequency = recordFrequency;
    }

    /**
     * 设置倒数第几个开始请求下一页数据
     *
     * @param backWardVisiblePosition
     */
    public void setBackWardVisiblePosition(int backWardVisiblePosition) {
        this.mBackWardVisiblePosition = backWardVisiblePosition;
    }

    /**
     * 设置回调
     *
     * @param onScrollListener
     */
    public void setOnScrollListener(RVOnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    /**
     * 倒数第几个item可见
     *
     * @param recyclerView
     * @param flVisibleItemPosition
     */
    private void handleBackWardPositionVisible(RecyclerView recyclerView,
                                               int[] flVisibleItemPosition) {
        int totalItemCount = getItemCount(recyclerView);
        int endPosition = totalItemCount - mBackWardVisiblePosition;
        if (flVisibleItemPosition != null && flVisibleItemPosition.length >= 2) {
            if (flVisibleItemPosition[1] > mMaxLastVisiblePosition
                    && flVisibleItemPosition[1] >= endPosition) {
                mMaxLastVisiblePosition = totalItemCount;
                mOnScrollListener.onBackWardPositionVisible();
            }
        }

    }

    /**
     * 获取recyclerview的item总数
     *
     * @param recyclerView
     * @return
     */
    private int getItemCount(RecyclerView recyclerView) {
        return recyclerView.getAdapter() == null ? 0 : recyclerView.getAdapter().getItemCount();
    }

    /**
     * PV回调
     *
     * @param flVisibleItemPosition
     * @param direction
     */
    private void handlePV(int[] flVisibleItemPosition, boolean direction) {
        long currentTimeMillis = System.currentTimeMillis();
        int visibleCount = Math.abs(flVisibleItemPosition[0] - flVisibleItemPosition[1]);
        mOnScrollListener.onPvEvent(flVisibleItemPosition[0], visibleCount, direction);

        if ((currentTimeMillis - mLastRecordTime) > mRecordFrequency) {
            mOnScrollListener.onPvEvent(flVisibleItemPosition[0], visibleCount);
            mLastRecordTime = currentTimeMillis;
        }
    }

    /**
     * 获取第一个可见的item的position和最后一个可见的item的position
     *
     * @param layoutManager
     * @return
     */
    private int[] getFLVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        int[] positions = new int[2];
        if (layoutManager == null)
            return positions;
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager llm = (LinearLayoutManager) layoutManager;
            positions[0] = llm.findFirstVisibleItemPosition();
            positions[1] = llm.findLastVisibleItemPosition();
        } else if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager glm = (GridLayoutManager) layoutManager;
            positions[0] = glm.findFirstVisibleItemPosition();
            positions[1] = glm.findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager sglm = (StaggeredGridLayoutManager) layoutManager;
            int first, last;
            sglm.findFirstVisibleItemPositions(positions);
            first = positions[0];
            sglm.findLastVisibleItemPositions(positions);
            last = positions[0];
            positions[0] = first;
            positions[1] = last;
        }
        return positions;
    }

    /**
     * 往上还是往下滑动
     *
     * @param recyclerView
     * @param dx
     * @param dy
     */
    private boolean handleScrollUpDown(RecyclerView recyclerView, int dx, int dy) {
//        boolean direction = false;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null)
            return direction;
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager llm = (LinearLayoutManager) layoutManager;
            switch (llm.getOrientation()) {
                case LinearLayoutManager.HORIZONTAL:
                    if (dx > SCROLL_SENSITIVITY) {
                        direction = false;
                    } else if (dx < -SCROLL_SENSITIVITY) {
                        direction = true;
                    }
                    break;
                case LinearLayoutManager.VERTICAL:
                    if (dy > SCROLL_SENSITIVITY) {
                        direction = false;
                    } else if (dy < -SCROLL_SENSITIVITY) {
                        direction = true;
                    }
                    break;
            }
        } else if (layoutManager instanceof GridLayoutManager) {
            if (dy > SCROLL_SENSITIVITY) {
                direction = false;
            } else if (dy < -SCROLL_SENSITIVITY) {
                direction = true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager sglm = (StaggeredGridLayoutManager) layoutManager;
            switch (sglm.getOrientation()) {
                case StaggeredGridLayoutManager.HORIZONTAL:
                    if (dx > SCROLL_SENSITIVITY) {
                        direction = false;
                    } else if (dx < -SCROLL_SENSITIVITY) {
                        direction = true;
                    }
                    break;
                case StaggeredGridLayoutManager.VERTICAL:
                    if (dy > SCROLL_SENSITIVITY) {
                        direction = false;
                    } else if (dy < -SCROLL_SENSITIVITY) {
                        direction = true;
                    }
                    break;
            }
        }
        mOnScrollListener.onScrollDirectionChange(direction);
        return direction;
    }

    private boolean direction;

    /**
     * 封装的滚动监听器
     */
    public interface RVOnScrollListener {

        /**
         * 滚动方向改变
         *
         * @param directionDown true --> 向下滚动, false-->向上滚动
         */
        void onScrollDirectionChange(boolean directionDown);

        /**
         * 设置滑动到倒数第几个View开始请求下一页
         */
        void onBackWardPositionVisible();

        /**
         * 统计打点,每隔{@link #mRecordFrequency }时间记录一次
         */
        void onPvEvent(int firstVisiblePosition, int visibleCount);

        /**
         * 统计打点，没时间限制，告诉你上拉下拉,自己处理去重的业务逻辑
         *
         * @param firstVisiblePosition
         * @param visibleCount
         * @param isPullUp
         */
        void onPvEvent(int firstVisiblePosition, int visibleCount, boolean isPullUp);

        /**
         * headerview可见时，隐藏一键置顶
         *
         * @param recyclerView
         * @param dx
         * @param dy
         */
        void onScrolled(RecyclerView recyclerView, int dx, int dy);

    }

}
