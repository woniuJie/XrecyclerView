package com.chuchujie.core.widget.recyclerview.footerview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chuchujie.core.widget.recyclerview.R;


/**
 * Created by wangjing on 8/16/16.
 */
public class FooterLoadingView extends RelativeLayout implements IFooterView {

    private boolean mShowLoadingView;

    private boolean mShowLastView;

    private String mLoadingText;

    private String mLastPageText;

    private TextView mLoadedInfoView;

    private TextView mLoadingInfoView;

    private ImageView mLoadingImageView;

    private View mLoadingView;

    public FooterLoadingView(Context context) {
        super(context);
        initView();
    }

    public FooterLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public FooterLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FooterLoadingView(Context context, AttributeSet attrs,
                             int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.xrv_footer_public, this);
        mLoadingView = findViewById(R.id.footerLoadingView);
        mLoadingInfoView = (TextView) findViewById(R.id.footerLoadingTextView);
        mLoadingImageView = (ImageView) findViewById(R.id.footerLoadingImageView);
        mLoadedInfoView = (TextView) findViewById(R.id.footerLastPageView);
        setLastPageText(R.string.xrv_footer_nomore);
        setLoadingText(R.string.xrv_footer_loading);
    }

    /**
     * 设置正在加载提示文案
     *
     * @param resId
     */
    @Override
    public void setLoadingText(int resId) {
        setLoadingText(getResources().getString(resId));
    }

    /**
     * 设置正在加载提示文案
     *
     * @param loadingText
     */
    @Override
    public void setLoadingText(String loadingText) {
        this.mLoadingText = loadingText;
    }

    /**
     * 设置最后一页提示文案
     *
     * @param resId
     */
    @Override
    public void setLastPageText(int resId) {
        setLastPageText(getResources().getString(resId));
    }

    /**
     * 设置最后一页提示文案
     *
     * @param lastPageText
     */
    @Override
    public void setLastPageText(String lastPageText) {
        this.mLastPageText = lastPageText;
    }

    /**
     * showFooterView:显示或者隐藏FooterView. <br/>
     *
     * @param showLoadingView true,显示正在加载View;false,隐藏正在加载View
     * @param showLastView    true,显示最后一页View;false,隐藏最后一页View
     * @author wangjing
     */
    @Override
    public void showFooterView(boolean showLoadingView, boolean showLastView) {
        this.mShowLoadingView = showLoadingView;
        this.mShowLastView = showLastView;
        updateViewState();
    }

    /**
     * 更新FooterView展示
     */
    private void updateViewState() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(GONE);
        }
        if (mLoadedInfoView != null) {
            mLoadedInfoView.setVisibility(GONE);
        }
        mLoadingImageView.clearAnimation();
        if (mShowLoadingView) {
            if (mLoadingView != null) {
                mLoadingView.setVisibility(VISIBLE);
            }
            if (!TextUtils.isEmpty(mLoadingText)) {
                mLoadingInfoView.setVisibility(VISIBLE);
                mLoadingInfoView.setText(mLoadingText);
            }
            Animation animation = AnimationUtils.loadAnimation(getContext(),
                    R.anim.xrv_loading_progress_anim);
            mLoadingImageView.startAnimation(animation);
        }

        if (mShowLastView) {
            if (mLoadedInfoView != null) {
                mLoadedInfoView.setVisibility(VISIBLE);
            }
            if (!TextUtils.isEmpty(mLastPageText))
                mLoadedInfoView.setText(mLastPageText);
        }
    }

}
