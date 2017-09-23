package io.stormcast.app.stormcast.views.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import io.stormcast.app.stormcast.R;

/**
 * Created by sudhar on 9/22/17.
 */

public class StyledTextView extends android.support.v7.widget.AppCompatTextView {
    public StyledTextView(Context context) {
        this(context, null);
    }

    public StyledTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public StyledTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.StyledTextView);
        String fontWeight = arr.getString(R.styleable.StyledTextView_tv_weight);
        setTypeface(TypeFaceUtil.getTypeFace(context, fontWeight));
    }
}
