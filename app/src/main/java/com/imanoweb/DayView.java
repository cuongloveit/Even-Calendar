package com.imanoweb;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Nilanchala on 9/11/15.
 */
public class DayView extends TextView {
    private Date date;
    private List<DayDecorator> decorators;

    public DayView(Context context) {
        this(context, null, 0);
    }

    public DayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            if (isInEditMode())
                return;
        }
    }

    public void bind(Date date, List<DayDecorator> decorators) {
        this.date = date;
        this.decorators = decorators;

        final SimpleDateFormat df = new SimpleDateFormat("d");
        int day = Integer.parseInt(df.format(date));
        setText(String.valueOf(day));


    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthDesc = MeasureSpec.getMode(widthMeasureSpec);
        int heightDesc = MeasureSpec.getMode(heightMeasureSpec);
        int size = 0;
        if (widthDesc == MeasureSpec.UNSPECIFIED
                && heightDesc == MeasureSpec.UNSPECIFIED) {
           // size = getContext().getResources().getDimensionPixelSize(R.dimen.default_size); // Use your own default size, for example 125dp
            size = 80;
        } else if ((widthDesc == MeasureSpec.UNSPECIFIED || heightDesc == MeasureSpec.UNSPECIFIED)
                && !(widthDesc == MeasureSpec.UNSPECIFIED && heightDesc == MeasureSpec.UNSPECIFIED)) {
            //Only one of the dimensions has been specified so we choose the dimension that has a value (in the case of unspecified, the value assigned is 0)
            size = width > height ? width : height;
        } else {
            //In all other cases both dimensions have been specified so we choose the smaller of the two
            size = width > height ? height : width;
        }
        setMeasuredDimension(size, size);
    }

    public void decorate() {
        //Set custom decorators
        if (null != decorators) {
            for (DayDecorator decorator : decorators) {
                decorator.decorate(this);
            }
        }
    }

    public Date getDate() {
        return date;
    }
}