package com.fuel.mileage.Utilities;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.fuel.mileage.R;

/**
 * Created by Sunil Kumar on 7/27/2016.
 */
public class CustomTextView extends TextView {
    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setTextColor(ContextCompat.getColor(getContext(), R.color.colorDarkGray));
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/segoeui.ttf");
        setTypeface(tf);
    }
}

