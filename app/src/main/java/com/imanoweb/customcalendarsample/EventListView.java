package com.imanoweb.customcalendarsample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by cuong on 7/26/16.
 */
public class EventListView extends RelativeLayout {
    private ArrayList<Integer> tvIds;
    private ArrayList<Integer> lineIds;
    private ArrayList<Integer> rulerIds;
    private int marginTopLine;
    private int heightRow;

    public EventListView(Context context) {
        super(context);
        init();
    }

    public EventListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EventListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        configLayout();
        addRuler();
        addTimeText();
        addLine();
        setTimeEvent(9, 30, 11, 30);
        setTimeEvent(6, 30, 7, 30);
        addRedLine(10, 20);
        addRedLine(4, 40);

    }

    private void configLayout() {
        setPadding(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.height_tv_time_row));
        heightRow = getResources().getDimensionPixelOffset(R.dimen.height_row);
        marginTopLine = (getResources().getDimensionPixelOffset(R.dimen.height_tv_time_row) / 2);
    }


    private void addTimeText() {
        tvIds = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            LayoutParams lp = new LayoutParams(getResources().getDimensionPixelOffset(R.dimen.width_tv_time), ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.addRule(BELOW, rulerIds.get(i));
            lp.setMargins(0, -marginTopLine + getResources().getDimensionPixelOffset(R.dimen.height_line), getResources().getDimensionPixelOffset(R.dimen.margin_tv_time_line), 0);
            TextView tvTimeNext = new TextView(getContext());
            convertHour(i,0);
            tvTimeNext.setText(convertHour(i,0));
            tvTimeNext.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            tvTimeNext.setLayoutParams(lp);
            int idRow = generateViewId();
            tvTimeNext.setId(idRow);
            tvIds.add(idRow);
            addView(tvTimeNext);
        }
    }

    private String convertHour(int hour, int minute) {
        String unit = " AM";
        int time = hour;
        if (hour >= 12)
            unit = " PM";
        if (hour > 12)
            time = hour - 12;
        if (minute > 0)
            return time + ":" + minute + unit;
        return time + unit;
    }

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    public void setTimeEvent(int startHour, int startMinute, int endHour, int endMinute) {
        View timeView = new View(getContext());
        View viewAgent = new EventView(getContext());
        timeView.setBackgroundResource(R.color.bg_time_event);
        float ratioStart = 60 / startMinute;//ratio to set start minute
        float ratioEnd = 60 / endMinute;
        int minuteStart = (int) (heightRow / ratioStart);
        int minuteEnd = (int) (heightRow / ratioEnd);

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, minuteStart, 0, -minuteEnd);
        lp.addRule(BELOW, rulerIds.get(startHour));
        lp.addRule(RIGHT_OF, tvIds.get(startHour));
        lp.addRule(ALIGN_BOTTOM, rulerIds.get(endHour));

        LayoutParams lpAgent = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lpAgent.setMargins(0, minuteStart, 0, -minuteEnd);
        lpAgent.addRule(BELOW, rulerIds.get(startHour));
        lpAgent.addRule(RIGHT_OF, tvIds.get(startHour));
        lpAgent.addRule(ALIGN_BOTTOM, rulerIds.get(endHour));

        timeView.setLayoutParams(lp);
        addView(timeView);
        viewAgent.setLayoutParams(lpAgent);
        addView(viewAgent);
    }

    private void addLine() {
        lineIds = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.height_line));
            lp.addRule(BELOW, rulerIds.get(i));
            lp.addRule(RelativeLayout.RIGHT_OF, tvIds.get(i));
            lp.setMargins(0, 0, 0, 0);
            View lineNext = new View(getContext());
            lineNext.setBackgroundResource(R.color.line_time_row);
            lineNext.setLayoutParams(lp);
            int idLineNext = generateViewId();
            lineNext.setId(idLineNext);
            lineIds.add(idLineNext);
            addView(lineNext);
        }
    }

    private void addRuler() {
        rulerIds = new ArrayList<>();
        LayoutParams lpFirst = new LayoutParams(100, getResources().getDimensionPixelOffset(R.dimen.height_line));
        lpFirst.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lpFirst.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lpFirst.setMargins(0, getResources().getDimensionPixelOffset(R.dimen.height_tv_time_row), 0, 0);
        View line = new View(getContext());
        line.setLayoutParams(lpFirst);
        int idLine = generateViewId();
        line.setId(idLine);
        rulerIds.add(idLine);
        addView(line);
        for (int i = 1; i < 24; i++) {
            LayoutParams lp = new LayoutParams(700, getResources().getDimensionPixelOffset(R.dimen.height_line));
            lp.addRule(BELOW, rulerIds.get(i - 1));
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            int marginTop;
            if (i == 0)
                marginTop = 0;
            else marginTop = heightRow;
            lp.setMargins(0, marginTop, 0, 0);
            View lineNext = new View(getContext());
            lineNext.setLayoutParams(lp);
            int idLineNext = generateViewId();
            lineNext.setId(idLineNext);
            rulerIds.add(idLineNext);
            addView(lineNext);
        }
    }

    public int getHeightRow() {
        return heightRow;
    }

    public void setHeightRow(int heightRow) {
        this.heightRow = heightRow;
    }

    public void addRedLine(int hour, int minute) {
        RedLine view = new RedLine(getContext());
        view.setTime(convertHour(hour,minute));
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(BELOW, rulerIds.get(hour));
        //lp.addRule(RIGHT_OF, tvIds.get(hour));
        float ratio = 60/minute;
        lp.setMargins(0, (int) (heightRow /ratio)-getResources().getDimensionPixelOffset(R.dimen.height_red_line_layout), 0, 0);
        view.setLayoutParams(lp);
        addView(view);

    }

}
