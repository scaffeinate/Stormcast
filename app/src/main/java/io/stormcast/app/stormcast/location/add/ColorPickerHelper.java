package io.stormcast.app.stormcast.location.add;

import android.content.Context;

import io.stormcast.app.stormcast.views.colorpick.MaterialColorPickDialogBuilder;

/**
 * Created by sudharti on 8/18/17.
 */

public class ColorPickerHelper {

    protected static void showColorPicker(Context context, int defaultColor, final ColorPickerCallback callback) {
        MaterialColorPickDialogBuilder.with(context).setTitle("Color Picker").build().show();
    }

    interface ColorPickerCallback {
        void onColorSelected(String colorHex);
    }
}
