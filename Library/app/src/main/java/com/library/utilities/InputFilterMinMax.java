package com.library.utilities;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterMinMax implements InputFilter {
    private final float minValue, maxValue;

    public InputFilterMinMax(float minValue, float maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    private boolean isInRange(float a, float b, float c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            float inputValue = Float.parseFloat(dest.toString() + source.toString());
            if (isInRange(minValue, maxValue, inputValue)) return null;
        } catch (NumberFormatException e) {
            return "";
        }
        return "";
    }
}