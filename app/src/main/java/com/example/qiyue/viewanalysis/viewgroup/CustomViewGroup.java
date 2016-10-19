package com.example.qiyue.viewanalysis.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.qiyue.viewanalysis.L;
import com.example.qiyue.viewanalysis.R;
import com.example.qiyue.viewanalysis.view.CustomButton;

/**
 * EXACTLY：表示设置了精确的值，一般当childView设置其宽、高为精确值、match_parent时，ViewGroup会将其设置为EXACTLY；
 AT_MOST：表示子布局被限制在一个最大值内，一般当childView设置其宽、高为wrap_content时，ViewGroup会将其设置为AT_MOST；
 UNSPECIFIED：表示子布局想要多大就多大，一般出现在AadapterView的item的heightMode中、ScrollView的childView的heightMode中；此种模式比较少见:
 */
public class CustomViewGroup extends ViewGroup {

    private CustomButton customButton;

    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs)
    {
        return new MarginLayoutParams(getContext(), attrs);
        /**
         * 这里就可以解析布局中的参数，可以在构造方法中解析
         */
       // return new CustomLayoutParams(getContext(),attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        customButton = (CustomButton) getChildAt(0);


        /**
         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        /**
         * 测量所有孩子
         */
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int cCount = getChildCount();
        /**
         * 根据childView计算的出的宽和高，以及设置的margin计算容器的宽和高，主要用于容器是warp_content时
         */
        int finalWidth = 0;
        int finalHeight = 0;

        int cWidth =0;
        int cHeight = 0;
        MarginLayoutParams cParams  = null;
        for (int i = 0; i < cCount; i++)
        {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            /**
             * 可以获取布局中的 宽高和margin
             */
            cParams = (MarginLayoutParams) childView.getLayoutParams();
            finalWidth = finalWidth+ cWidth + cParams.leftMargin + cParams.rightMargin;
            finalHeight = cHeight + cParams.topMargin + cParams.bottomMargin;

        }


        switch (widthMode){
            case MeasureSpec.EXACTLY:
                 finalWidth = sizeWidth;
                break;
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }

        switch (heightMode){
            case MeasureSpec.EXACTLY:
                finalHeight = sizeHeight;
                break;
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        setMeasuredDimension(finalWidth,finalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;
        /**
         * 遍历所有childView根据其宽和高，以及margin进行布局
         */
        for (int i = 0; i < 1; i++)
        {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            int cl = 0, ct = 0, cr = 0, cb = 0;
            cl = cParams.leftMargin;
            ct = cParams.topMargin;
            cr = cParams.leftMargin + cWidth;
            cb = cParams.topMargin + cHeight;

            childView.layout(cl, ct, cr, cb);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                L.e("CustomViewGroup "+" onInterceptTouchEvent "+" ACTION_DOWN");
              //  return true;
                break;
            case MotionEvent.ACTION_MOVE:
                L.e("CustomViewGroup "+" onInterceptTouchEvent "+" ACTION_MOVE");
               // return true;
                return true;
               // break;
            case MotionEvent.ACTION_UP:
                L.e("CustomViewGroup "+" onInterceptTouchEvent "+" ACTION_UP");
                //return true;
                break;
        }

        if (super.onInterceptTouchEvent(ev)){
            L.i("CustomViewGroup "+"super onInterceptTouchEvent "+" true");
            return true;
        }else{
            L.i("CustomViewGroup "+"super onInterceptTouchEvent "+" false");
            return false;
        }
       // return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                L.i("CustomViewGroup "+" dispatchTouchEvent "+" ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                L.i("CustomViewGroup "+" dispatchTouchEvent "+" ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                L.i("CustomViewGroup "+" dispatchTouchEvent "+" ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                L.i("CustomViewGroup "+" onTouchEvent "+" ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                L.i("CustomViewGroup "+" onTouchEvent "+" ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                L.i("CustomViewGroup "+" onTouchEvent "+" ACTION_UP");
                break;
        }
        if (super.onTouchEvent(ev)){
            L.i("CustomViewGroup "+"super onTouchEvent "+" true");
            return true;
        }else{
            L.i("CustomViewGroup "+"super onTouchEvent "+" false");
            return false;
        }
     //   return super.onTouchEvent(ev);
    }

   /* class CustomLayoutParams extends MarginLayoutParams{

        public CustomLayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);
            *//**解析自定义属性**//*
           *//* TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UScrollParam);
            mDiscrollveAlpha = a.getBoolean(R.styleable.UScrollParam_scroll_alpha, false);
            mDiscrollveScaleX = a.getBoolean(R.styleable.UScrollParam_scroll_scaleX, false);
            mDiscrollveScaleY = a.getBoolean(R.styleable.UScrollParam_scroll_scaleY, false);
            mDisCrollveTranslation = a.getInt(R.styleable.UScrollParam_scroll_translation, -1);
            mDiscrollveFromBgColor = a.getColor(R.styleable.UScrollParam_scroll_fromBgColor, -1);
            mDiscrollveToBgColor = a.getColor(R.styleable.UScrollParam_scroll_toBgColor, -1);
            a.recycle();*//*
        }
    }*/
}
