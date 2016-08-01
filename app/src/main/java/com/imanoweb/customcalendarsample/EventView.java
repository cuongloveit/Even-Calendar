package com.imanoweb.customcalendarsample;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by cuong on 7/26/16.
 */
public class EventView extends LinearLayout {
    public EventView(Context context) {
        super(context);
        init();
    }

    public EventView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EventView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        inflate(getContext(),R.layout.layout_event,this);

    }
}
