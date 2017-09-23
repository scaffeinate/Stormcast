package io.stormcast.app.stormcast.views.text;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

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
        this.setFont();
    }

    private void setFont() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "font/futura_pt_book.otf");
        setTypeface(font);
    }
}
