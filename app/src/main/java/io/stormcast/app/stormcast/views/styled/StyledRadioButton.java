package io.stormcast.app.stormcast.views.styled;

import android.content.Context;
import android.util.AttributeSet;

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
        TypeFaceUtil.setTypeFace(context, this, attrs);
    }
}
