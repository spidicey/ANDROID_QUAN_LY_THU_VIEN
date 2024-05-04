package com.library.utilities;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    public static String format(Date date) {
        return FORMATTER.format(date);
    }

    public static Date parse(String date) {
        try {
            return FORMATTER.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
