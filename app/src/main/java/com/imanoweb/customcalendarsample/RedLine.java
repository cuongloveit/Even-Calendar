package com.imanoweb.customcalendarsample;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by cuong on 7/27/16.
 */
public class RedLine extends RelativeLayout {
    private TextView tvTime;

    public RedLine(Context context) {
        super(context);
        init();
    }

    public RedLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RedLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        inflate(getContext(),R.layout.layout_red_line,this);
        tvTime = (TextView) findViewById(R.id.tvRedTime);
    }

    public void setTime(String time){
        tvTime.setText(time);
    }
}
