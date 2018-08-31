package com.igsdigital.premierfeedback.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Ali Jibran on 05-Oct-16.
 */

public class PremierTextViewBold extends TextView {
    public PremierTextViewBold(Context context) {
        super(context);
    }

    public PremierTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/zemestrostd-bold.ttf"));
    }

    public PremierTextViewBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
