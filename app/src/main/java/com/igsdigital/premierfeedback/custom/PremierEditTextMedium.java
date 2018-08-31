package com.igsdigital.premierfeedback.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Ali Jibran on 05-Oct-16.
 */

public class PremierEditTextMedium extends EditText {
    public PremierEditTextMedium(Context context) {
        super(context);
    }

    public PremierEditTextMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/zemestrostd-medium.ttf"));
    }

    public PremierEditTextMedium(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
