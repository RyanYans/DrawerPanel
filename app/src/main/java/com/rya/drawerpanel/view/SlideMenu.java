package com.rya.drawerpanel.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Rya32 on 广东石油化工学院.
 * Version 1.0
 */

public class SlideMenu extends ViewGroup {

    private static final int MENU_STATE = 101;
    private static final int MAIN_STATE = 102;
    private View leftMenu;
    private View mainView;
    private float downX;
    private float moveX;
    private int currentState;
    private Scroller mScroller;
    private float downY;
    private float moveY;

    public SlideMenu(Context context) {
        super(context);
        init();
    }

    public SlideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
    }

    public SlideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        leftMenu = getChildAt(0);
        leftMenu.measure(leftMenu.getLayoutParams().width, heightMeasureSpec);

        mainView = getChildAt(1);
        mainView.measure(widthMeasureSpec, heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        leftMenu.layout(-leftMenu.getMeasuredWidth(), 0, 0, b);

        mainView.layout(l, t, r, b);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = ev.getX();
                moveY = ev.getY();

                float offsetX = Math.abs(moveX - downX);
                float offsetY = Math.abs(moveY - downY);

                if ((offsetX > offsetY) && (offsetX > 3)) {
                    return true;
                }

                break;
            case MotionEvent.ACTION_UP:
                break;

        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getX();
                int scrollX = (int) (downX - moveX);

                int newScrollX = getScrollX() + scrollX;

                if (newScrollX < -leftMenu.getMeasuredWidth()) {
                    scrollTo(-leftMenu.getMeasuredWidth(), 0);
                } else if (newScrollX > 0) {
                    scrollTo(0, 0);
                } else {
                    scrollBy(scrollX, 0);
                }

                // 第二次移动的起始点 = 第一次移动的结束点
                downX = moveX;
                break;
            case MotionEvent.ACTION_UP:
                int centerScrollX = -leftMenu.getMeasuredWidth() / 2;
                if (getScrollX() < centerScrollX) {
                    currentState = MENU_STATE;
                } else {
                    currentState = MAIN_STATE;
                }
                updateState();

                break;
        }

        return true;
    }

    private void updateState() {
        int startX = getScrollX();
        int dx;

        if (currentState == MENU_STATE) {
            dx = -leftMenu.getMeasuredWidth() - startX;
        } else {
            dx = 0 - startX;
        }

        int duration = Math.abs(dx * 2);

        mScroller.startScroll(startX, 0, dx, 0, duration);

        //重绘界面，drawChild --> computeScroll();
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            int currX = mScroller.getCurrX();
            scrollTo(currX, 0);

            //重绘界面
            invalidate();
        }
    }

    public void openMenu() {
        currentState = MENU_STATE;
        updateState();
    }

    public void closeMenu() {
        currentState = MAIN_STATE;
        updateState();
    }

    public void switchState() {
        if (currentState == MAIN_STATE) {
            currentState = MENU_STATE;
            updateState();
        } else {
            currentState = MAIN_STATE;
            updateState();
        }
    }

}
