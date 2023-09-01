package com.example.finalteamproject.main;

import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.HashSet;

public class DateDecorator implements DayViewDecorator {

    private final int color;
    private final HashSet<CalendarDay> dates;

    public DateDecorator(int color, HashSet<CalendarDay> dates) {
        this.color = color;
        this.dates =dates;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if (dates.contains(day)) {
            Log.d("확인용", "shouldDecorate: ");
            return true;
        }
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(5, color));
    }
}
