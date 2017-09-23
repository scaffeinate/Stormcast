package io.stormcast.app.stormcast.views.styled;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import io.stormcast.app.stormcast.R;

/**
 * Created by sudharti on 9/23/17.
 */

public class StyledRadioButton extends android.support.v7.widget.AppCompatRadioButton {
    public StyledRadioButton(Context context) {
        this(context, null);
    }

    public StyledRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.radioButtonStyle);
    }

    public StyledRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.StyledRadioButton);
        String fontWeight = arr.getString(R.styleable.StyledRadioButton_rb_weight);
        setTypeface(TypeFaceUtil.getTypeFace(context, fontWeight));
    }
}
