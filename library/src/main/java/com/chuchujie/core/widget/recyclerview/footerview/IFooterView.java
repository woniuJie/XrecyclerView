package com.chuchujie.core.widget.recyclerview.footerview;

/**
 * Created by wangjing on 9/22/16 11:06 PM.
 */
public interface IFooterView {

    /**
     * 设置正在加载提示文案
     *
     * @param resId
     */
    void setLoadingText(int resId);

    /**
     * 设置正在加载提示文案
     *
     * @param loadingText
     */
    void setLoadingText(String loadingText);

    /**
     * 设置最后一页提示文案
     *
     * @param resId
     */
    void setLastPageText(int resId);

    /**
     * 设置最后一页提示文案
     *
     * @param lastPageText
     */
    void setLastPageText(String lastPageText);

    /**
     * showFooterView:显示或者隐藏FooterView. <br/>
     *
     * @param showLoadingView true,显示正在加载View;false,隐藏正在加载View
     * @param showLastView    true,显示最后一页View;false,隐藏最后一页View
     *
     * @author wangjing
     */
    void showFooterView(boolean showLoadingView, boolean showLastView);

}