package io.stormcast.app.stormcast.views.styled;

import android.content.Context;
import android.util.AttributeSet;

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
        TypeFaceUtil.setTypeFace(context, this, attrs);
    }
}
