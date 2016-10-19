package com.example.qiyue.viewanalysis.view;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.qiyue.viewanalysis.L;
import com.example.qiyue.viewanalysis.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**     onTouch return false
 * 10-19 11:22:55.556 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton  dispatchTouchEvent  ACTION_DOWN
 10-19 11:22:55.556 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton  onTouch  ACTION_DOWN
 10-19 11:22:55.556 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton  onTouchEvent  ACTION_DOWN
 10-19 11:22:55.556 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton super onTouchEvent  true

 10-19 11:22:55.706 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton  dispatchTouchEvent  ACTION_MOVE
 10-19 11:22:55.706 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton  onTouch  ACTION_MOVE
 10-19 11:22:55.706 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton  onTouchEvent  ACTION_MOVE
 10-19 11:22:55.706 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton super onTouchEvent  true

 10-19 11:23:01.076 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton  dispatchTouchEvent  ACTION_UP
 10-19 11:23:01.076 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton  onTouch  ACTION_UP
 10-19 11:23:01.076 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton  onTouchEvent  ACTION_UP
 10-19 11:23:01.076 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton super onTouchEvent  true

    onTouch return true    发现 所有事件都不经过 onTouchEvent里了

 10-19 11:25:27.881 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton  dispatchTouchEvent  ACTION_DOWN
 10-19 11:25:27.881 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton  onTouch  ACTION_DOWN
 10-19 11:25:27.931 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton  dispatchTouchEvent  ACTION_MOVE
 10-19 11:25:27.931 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton  onTouch  ACTION_MOVE
 10-19 11:25:27.946 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton  dispatchTouchEvent  ACTION_MOVE
 10-19 11:25:27.946 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton  onTouch  ACTION_MOVE
 10-19 11:25:27.961 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton  dispatchTouchEvent  ACTION_MOVE
 10-19 11:25:27.961 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton  onTouch  ACTION_MOVE
 10-19 11:25:27.981 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton  dispatchTouchEvent  ACTION_MOVE
 10-19 11:25:27.981 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton  onTouch  ACTION_MOVE
 10-19 11:25:27.991 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton  dispatchTouchEvent  ACTION_UP
 10-19 11:25:27.991 31714-31714/com.example.qiyue.viewanalysis I/qiyue: CustomButton  onTouch  ACTION_UP

         dispatchTouchEvent return true   onTouch  onTouchEvent也什么都接不到
 10-19 11:27:58.521 5233-5233/com.example.qiyue.viewanalysis I/qiyue: CustomButton  dispatchTouchEvent  ACTION_DOWN
 10-19 11:27:58.691 5233-5233/com.example.qiyue.viewanalysis I/qiyue: CustomButton  dispatchTouchEvent  ACTION_MOVE
 10-19 11:27:59.006 5233-5233/com.example.qiyue.viewanalysis I/qiyue: CustomButton  dispatchTouchEvent  ACTION_UP



 View 的事件分发还是挺简单  dispatchTouchEvent  ---onTouch   --- onTouchEvent
      但是想了解更深入，需要了解 onTouchEvent里面

 onTouchEvent里面
              0ms        115ms       500ms
 DOWN时：  PREPRESSED   PRESSED     LongClickListener( )

 Move时：


 a、首先设置标志为PREPRESSED，设置mHasPerformedLongPress=false ;然后发出一个115ms后的mPendingCheckForTap；
 b、如果115ms内没有触发UP，则将标志置为PRESSED，清除PREPRESSED标志，同时发出一个延时为500-115ms的，检测长按任务消息；
 c、如果500ms内（从DOWN触发开始算），则会触发LongClickListener:
 此时如果LongClickListener不为null，则会执行回调，同时如果LongClickListener.onClick返回true，才把mHasPerformedLongPress设置为true;否则mHasPerformedLongPress依然为false;

 MOVE时：
 主要就是检测用户是否划出控件，如果划出了：
 115ms内，直接移除mPendingCheckForTap；
 115ms后，则将标志中的PRESSED去除，同时移除长按的检查：removeLongPressCallback();


 UP时：  （115ms~500ms会触发onCLick）

 a、如果115ms内，触发UP，此时标志为PREPRESSED，则执行UnsetPressedState，setPressed(false);会把setPress转发下去，可以在View中复写dispatchSetPressed方法接收；
 b、如果是115ms-500ms间，即长按还未发生，则首先移除长按检测，执行onClick回调；
 c、如果是500ms以后，那么有两种情况：
 i.设置了onLongClickListener，且onLongClickListener.onClick返回true，则点击事件OnClick事件无法触发；
 ii.没有设置onLongClickListener或者onLongClickListener.onClick返回false，则点击事件OnClick事件依然可以触发；
 d、最后执行mUnsetPressedState.run()，将setPressed传递下去，然后将PRESSED标识去除；

 */
public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Button button = (Button) findViewById(R.id.customButton);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        L.i("CustomButton "+" onTouch "+" ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        L.i("CustomButton "+" onTouch "+" ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        L.i("CustomButton "+" onTouch "+" ACTION_UP");
                        break;
                }
                return true;
            }
        });

    }


}
