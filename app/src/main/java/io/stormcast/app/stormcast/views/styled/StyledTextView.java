package io.stormcast.app.stormcast.views.styled;

import android.content.Context;
import android.util.AttributeSet;

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
        TypeFaceUtil.setTypeFace(context, this, attrs);
    }
}
