package com.imanoweb;

import java.util.Date;

/**
 * Created by Nilanchala on 9/14/15.
 */
public interface CalendarListener {
    void onDateSelected(Date date);

    void onMonthChanged(Date time);
}
