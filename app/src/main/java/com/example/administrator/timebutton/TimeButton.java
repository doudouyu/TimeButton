package com.example.administrator.timebutton;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2016/11/24.
 */
public class TimeButton extends Button implements View.OnClickListener {
    private String beforeText = "点击获取验证码";
    private String afterText = "秒后重新获取验证码";
    private boolean isFirstClick = true;//判断按钮是否被点击过，默认是未点击
    private int time = 10 * 1000;//倒计时的总时长
    private TimeButton timeButton;
    private int intervalTime = 1000;//间隔时间

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (time > 0) {
                        time -= intervalTime;
                        timeButton.setText(time / intervalTime + afterText);
                        handler.sendEmptyMessageDelayed(0, intervalTime);
                        timeButton.setEnabled(false);
                    } else {
                        timeButton.setText(beforeText);
                        timeButton.setEnabled(true);
                    }

                    break;
            }

        }
    };

    public TimeButton(Context context) {
        super(context);
        init();
    }


    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.timeButton = this;
        setText(beforeText);
        setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        //当按钮被点击的时候，判断是不是第一次点击
        if (isFirstClick) {
            //第一次点击，开始倒计时
            //发送handler消息通知主线程开始更新ui
            handler.sendEmptyMessageDelayed(0, intervalTime);
            time -= intervalTime;
            timeButton.setEnabled(false);
            timeButton.setText(time / intervalTime + afterText);
            isFirstClick = false;
        } else {
            timeButton.setText(beforeText);
            timeButton.setEnabled(true);
            time = 10 * intervalTime;
            handler.sendEmptyMessageDelayed(0, intervalTime);
        }
    }
}
