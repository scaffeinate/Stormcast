package io.stormcast.app.stormcast.views.styled;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import io.stormcast.app.stormcast.R;

/**
 * Created by sudharti on 9/23/17.
 */

public class TypeFaceUtil {

    protected static void setTypeFace(Context context, TextView view, AttributeSet attrs) {
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.StyledView);
        String fontWeight = arr.getString(R.styleable.StyledView_weight);
        view.setTypeface(TypeFaceUtil.getTypeFace(context, fontWeight));
    }

    private static Typeface getTypeFace(Context context, String fontWeight) {
        if (fontWeight != null) {
            if (fontWeight.equals("bold")) {
                return Typeface.createFromAsset(context.getAssets(), "fonts/renner-medium.otf");
            } else if (fontWeight.equals("bolder")) {
                return Typeface.createFromAsset(context.getAssets(), "fonts/renner-bold.otf");
            }
        }
        return Typeface.createFromAsset(context.getAssets(), "fonts/renner-book.otf");
    }
}
