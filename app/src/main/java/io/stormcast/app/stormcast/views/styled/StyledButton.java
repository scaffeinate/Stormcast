package io.stormcast.app.stormcast.views.styled;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by sudharti on 9/24/17.
 */

public class StyledButton extends android.support.v7.widget.AppCompatButton {
    public StyledButton(Context context) {
        this(context, null);
    }

    public StyledButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.buttonStyle);
    }

    public StyledButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypeFaceUtil.setTypeFace(context, this, attrs);
    }
}
