package com.example.qiyue.viewanalysis.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.qiyue.viewanalysis.L;

/**
 * Created by Administrator on 2016/10/19 0019.
 */
public class CustomButton extends Button {
    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        /**
         * 当父类容器设置onInterceptTouchEvent 时，此方法会可以让他不起作用或者起作用
         */
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                L.i("CustomButton "+" dispatchTouchEvent "+" ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                L.i("CustomButton "+" dispatchTouchEvent "+" ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                L.i("CustomButton "+" dispatchTouchEvent "+" ACTION_UP");
                break;
        }
        //return true;
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                L.i("CustomButton "+" onTouchEvent "+" ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                L.i("CustomButton "+" onTouchEvent "+" ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                L.i("CustomButton "+" onTouchEvent "+" ACTION_UP");
                break;
        }
        /**
         * 处理点击，短时间触碰，长按事件
         */
        if(super.onTouchEvent(event)){
            L.i("CustomButton "+"super onTouchEvent "+" true");
            return true;
        }else{
            L.i("CustomButton "+"super onTouchEvent "+" false");
            return false;
        }
        //return super.onTouchEvent(event);
    }
}
