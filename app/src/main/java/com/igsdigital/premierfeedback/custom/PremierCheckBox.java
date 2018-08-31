package com.igsdigital.premierfeedback.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * Created by Ali Jibran on 05-Oct-16.
 */

public class PremierCheckBox extends CheckBox {
    public PremierCheckBox(Context context) {
        super(context);
    }

    public PremierCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/zemestrostd-medium.ttf"));
    }

}
