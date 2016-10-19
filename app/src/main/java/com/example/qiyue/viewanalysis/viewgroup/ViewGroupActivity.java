package com.example.qiyue.viewanalysis.viewgroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.qiyue.viewanalysis.L;
import com.example.qiyue.viewanalysis.R;
/**   正常流程  down  move up 都会走 dispatchTouchEvent
 * onInterceptTouchEvent
 * dispatchTouchEvent
 * onTouchEvent(ziView 的)
 *
 *  只有自身的 onTouch  和 onTouchEvent不会走  （事件分发流程）
 *
 *
 *
 *
 10-19 15:02:19.966 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_DOWN
 10-19 15:02:19.966 18852-18852/com.example.qiyue.viewanalysis E/qiyue: CustomViewGroup  onInterceptTouchEvent  ACTION_DOWN
 10-19 15:02:19.966 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup super onInterceptTouchEvent  false
 10-19 15:02:19.966 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomButton  dispatchTouchEvent  ACTION_DOWN
 10-19 15:02:19.966 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomButton  onTouchEvent  ACTION_DOWN
 10-19 15:02:19.966 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomButton super onTouchEvent  true

 10-19 15:02:19.996 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_MOVE
 10-19 15:02:19.996 18852-18852/com.example.qiyue.viewanalysis E/qiyue: CustomViewGroup  onInterceptTouchEvent  ACTION_MOVE
 10-19 15:02:19.996 18852-18852/com.example.qiyue.viewanalysis E/qiyue: CustomViewGroup  onInterceptTouchEvent  ACTION_UP
 10-19 15:02:19.996 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup super onInterceptTouchEvent  false
 10-19 15:02:19.996 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomButton  dispatchTouchEvent  ACTION_MOVE
 10-19 15:02:19.996 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomButton  onTouchEvent  ACTION_MOVE
 10-19 15:02:19.996 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomButton super onTouchEvent  true

 10-19 15:02:20.456 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_UP
 10-19 15:02:20.456 18852-18852/com.example.qiyue.viewanalysis E/qiyue: CustomViewGroup  onInterceptTouchEvent  ACTION_UP
 10-19 15:02:20.456 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup super onInterceptTouchEvent  false
 10-19 15:02:20.456 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomButton  dispatchTouchEvent  ACTION_UP
 10-19 15:02:20.456 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomButton  onTouchEvent  ACTION_UP
 10-19 15:02:20.456 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomButton super onTouchEvent  true


 dispatchTouchEvent --- onInterceptTouchEvent --- super onInterceptTouchEvent ---false  --- View dispatchTouchEvent -onTouch--- onTouchEvent---

 当onInterceptTouchEvent 监听move 时return true 时，

 down事件可以顺利传递 1.viewGroup dispatchTouchEvent
                    2.viewGroup onInterceptTouchEvent (return false)
                    3.view dispatchTouchEvent
                    4.view onTouch (return false)
                    5.view onTouchEvent

              第一次move:
 move事件传递     1.viewGroup dispatchTouchEvent
                 2.viewGroup onInterceptTouchEvent (return true)
                 3.view onTouchEvent 这个方法会被调用，但是监听不到move 事件
              第二次和以后
                 1.viewGroup dispatchTouchEvent
                 2.viewGroup onTouch
                 3.viewGroup onTouchEvent （不处理父类就会return false，而view的会始终返回true）

 up 事件传递   1.dispatchTouchEvent
              2.onTouch    (return false)
              3.onTouchEvent  不出处理会返回false



 10-19 14:52:24.866 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_DOWN
 10-19 14:52:24.866 18852-18852/com.example.qiyue.viewanalysis E/qiyue: CustomViewGroup  onInterceptTouchEvent  ACTION_DOWN
 10-19 14:52:24.866 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup super onInterceptTouchEvent  false
 10-19 14:52:24.866 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomButton  dispatchTouchEvent  ACTION_DOWN
 10-19 14:52:24.866 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomButton  onTouchEvent  ACTION_DOWN
 10-19 14:52:24.866 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomButton super onTouchEvent  true

 10-19 14:52:24.896 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_MOVE
 10-19 14:52:24.896 18852-18852/com.example.qiyue.viewanalysis E/qiyue: CustomViewGroup  onInterceptTouchEvent  ACTION_MOVE
 10-19 14:52:24.896 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomButton super onTouchEvent  true

 10-19 14:52:24.911 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_MOVE
 10-19 14:52:24.911 18852-18852/com.example.qiyue.viewanalysis I/qiyue: customViewGroup onTouch
 10-19 14:52:24.911 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  onTouchEvent  ACTION_MOVE
 10-19 14:52:24.911 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup super onTouchEvent  false


 10-19 14:52:30.106 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_UP
 10-19 14:52:30.106 18852-18852/com.example.qiyue.viewanalysis I/qiyue: customViewGroup onTouch
 10-19 14:52:30.106 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  onTouchEvent  ACTION_UP
 10-19 14:52:30.106 18852-18852/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup super onTouchEvent  false

 当onInterceptTouchEvent 监听down时 return true 时，

 只会有down事件经过 1.ViewGroup dispatchTouchEvent
                  2.ViewGroup onIntercepTouchEvent
                  3.ViewGroup onTouch   (return false)
                  4.ViewGroup onTouchEvent  （不处理父类会返回false)


 10-19 15:29:31.611 29138-29138/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_DOWN
 10-19 15:29:31.611 29138-29138/com.example.qiyue.viewanalysis E/qiyue: CustomViewGroup  onInterceptTouchEvent  ACTION_DOWN
 10-19 15:29:31.611 29138-29138/com.example.qiyue.viewanalysis I/qiyue: customViewGroup onTouch
 10-19 15:29:31.611 29138-29138/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  onTouchEvent  ACTION_DOWN
 10-19 15:29:31.611 29138-29138/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup super onTouchEvent  false


 当onInterceptTouchEvent 监听up时 return true 时，
                 1.ViewGroup dispatchTouchEvent
                 2.ViewGroup onIntercepTouchEvent
                 3.view onTouchEvent 但是监听不到up事件，返回true

 10-19 15:34:16.796 29138-29138/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_DOWN
 10-19 15:34:16.796 29138-29138/com.example.qiyue.viewanalysis E/qiyue: CustomViewGroup  onInterceptTouchEvent  ACTION_DOWN
 10-19 15:34:16.796 29138-29138/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup super onInterceptTouchEvent  false
 10-19 15:34:16.796 29138-29138/com.example.qiyue.viewanalysis I/qiyue: CustomButton  dispatchTouchEvent  ACTION_DOWN
 10-19 15:34:16.796 29138-29138/com.example.qiyue.viewanalysis I/qiyue: CustomButton  onTouchEvent  ACTION_DOWN
 10-19 15:34:16.796 29138-29138/com.example.qiyue.viewanalysis I/qiyue: CustomButton super onTouchEvent  true

 10-19 15:34:16.821 29138-29138/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_MOVE
 10-19 15:34:16.821 29138-29138/com.example.qiyue.viewanalysis E/qiyue: CustomViewGroup  onInterceptTouchEvent  ACTION_MOVE
 10-19 15:34:16.821 29138-29138/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup super onInterceptTouchEvent  false
 10-19 15:34:16.821 29138-29138/com.example.qiyue.viewanalysis I/qiyue: CustomButton  dispatchTouchEvent  ACTION_MOVE
 10-19 15:34:16.821 29138-29138/com.example.qiyue.viewanalysis I/qiyue: CustomButton  onTouchEvent  ACTION_MOVE
 10-19 15:34:16.821 29138-29138/com.example.qiyue.viewanalysis I/qiyue: CustomButton super onTouchEvent  true

 10-19 15:34:16.871 29138-29138/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_MOVE
 10-19 15:34:16.871 29138-29138/com.example.qiyue.viewanalysis E/qiyue: CustomViewGroup  onInterceptTouchEvent  ACTION_MOVE
 10-19 15:34:16.871 29138-29138/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup super onInterceptTouchEvent  false
 10-19 15:34:16.871 29138-29138/com.example.qiyue.viewanalysis I/qiyue: CustomButton  dispatchTouchEvent  ACTION_MOVE
 10-19 15:34:16.871 29138-29138/com.example.qiyue.viewanalysis I/qiyue: CustomButton  onTouchEvent  ACTION_MOVE
 10-19 15:34:16.871 29138-29138/com.example.qiyue.viewanalysis I/qiyue: CustomButton super onTouchEvent  true

 10-19 15:34:16.906 29138-29138/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_UP
 10-19 15:34:16.906 29138-29138/com.example.qiyue.viewanalysis E/qiyue: CustomViewGroup  onInterceptTouchEvent  ACTION_UP
 10-19 15:34:16.906 29138-29138/com.example.qiyue.viewanalysis I/qiyue: CustomButton super onTouchEvent  true

 */


/**
 * 当onInterceptTouchEvent 就是让某些事件自己处理，比如侧滑布局里面有个button(前提触摸到)，ListView，
 * 你会发现怎么也滑动不出来，因为子类button 会被消费掉，因此在ViewGroup 的onInterceptTouchEvent里面监听move事件返回true
 *
 *
 */


/**
 * 10-19 15:56:41.201 23976-23976/com.example.qiyue.viewanalysis D/ViewRootImpl: ViewPostImeInputStage ACTION_DOWN
 10-19 15:56:41.201 23976-23976/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_DOWN
 10-19 15:56:41.201 23976-23976/com.example.qiyue.viewanalysis E/qiyue: CustomViewGroup  onInterceptTouchEvent  ACTION_DOWN
 10-19 15:56:41.201 23976-23976/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup super onInterceptTouchEvent  false
 10-19 15:56:41.201 23976-23976/com.example.qiyue.viewanalysis I/qiyue: CustomButton  dispatchTouchEvent  ACTION_DOWN
 10-19 15:56:41.201 23976-23976/com.example.qiyue.viewanalysis I/qiyue: CustomButton  onTouchEvent  ACTION_DOWN
 10-19 15:56:41.201 23976-23976/com.example.qiyue.viewanalysis I/qiyue: CustomButton super onTouchEvent  true

 10-19 15:56:41.226 23976-23976/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_MOVE
 10-19 15:56:41.241 23976-23976/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_MOVE
 10-19 15:56:41.261 23976-23976/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_MOVE
 10-19 15:56:41.276 23976-23976/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_MOVE
 10-19 15:56:41.291 23976-23976/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_MOVE
 10-19 15:56:41.326 23976-23976/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_MOVE
 10-19 15:56:41.361 23976-23976/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_MOVE
 10-19 15:56:41.376 23976-23976/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_MOVE
 10-19 15:56:41.411 23976-23976/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_MOVE
 10-19 15:56:41.461 23976-23976/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_MOVE
 10-19 15:56:41.466 23976-23976/com.example.qiyue.viewanalysis I/qiyue: CustomViewGroup  dispatchTouchEvent  ACTION_UP
 10-19 15:56:41.466 23976-23976/com.example.qiyue.viewanalysis I/qiyue: CustomButton  dispatchTouchEvent  ACTION_UP
 10-19 15:56:41.466 23976-23976/com.example.qiyue.viewanalysis I/qiyue: CustomButton  onTouchEvent  ACTION_UP
 10-19 15:56:41.466 23976-23976/com.example.qiyue.viewanalysis I/qiyue: CustomButton super onTouchEvent  true
 */

public class ViewGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);
        findViewById(R.id.customViewGroup).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                L.i("customViewGroup onTouch ");
                return false;
            }
        });
    }
}
