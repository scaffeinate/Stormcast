package io.stormcast.app.stormcast.views.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import io.stormcast.app.stormcast.R;

/**
 * Created by sudhar on 9/22/17.
 */

public class StyledEditText extends android.support.v7.widget.AppCompatEditText {

    public StyledEditText(Context context) {
        this(context, null);
    }

    public StyledEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public StyledEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.StyledEditText);
        String fontWeight = arr.getString(R.styleable.StyledEditText_et_weight);
        setTypeface(TypeFaceUtil.getTypeFace(context, fontWeight));
    }
}
