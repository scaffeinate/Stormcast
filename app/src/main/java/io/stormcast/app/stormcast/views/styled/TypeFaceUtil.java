package io.stormcast.app.stormcast.views.styled;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by sudharti on 9/23/17.
 */

public class TypeFaceUtil {
    protected static Typeface getTypeFace(Context context, String fontWeight) {
        if(fontWeight != null) {
            if (fontWeight.equals("bold")) {
                return Typeface.createFromAsset(context.getAssets(), "fonts/renner-medium.otf");
            } else if (fontWeight.equals("bolder")) {
                return Typeface.createFromAsset(context.getAssets(), "fonts/renner-bold.otf");
            }
        }
        return Typeface.createFromAsset(context.getAssets(), "fonts/renner-book.otf");
    }
}
