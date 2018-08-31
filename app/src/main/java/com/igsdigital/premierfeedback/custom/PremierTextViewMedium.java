package com.igsdigital.premierfeedback.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Ali Jibran on 05-Oct-16.
 */

public class PremierTextViewMedium extends TextView {
    public PremierTextViewMedium(Context context) {
        super(context);
    }

    public PremierTextViewMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/zemestrostd-medium.ttf"));
    }

    public PremierTextViewMedium(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
