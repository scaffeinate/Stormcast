package io.stormcast.app.stormcast.location.add;

import android.content.Context;
import android.widget.Toast;

import io.stormcast.app.stormcast.views.colorpick.MaterialColorPickDialog;

/**
 * Created by sudharti on 8/18/17.
 */

public class ColorPickerHelper {

    protected static void showColorPicker(final Context context, int defaultColor, final ColorPickerCallback callback) {
        MaterialColorPickDialog.with(context).build().setOnColorPickedListener(new MaterialColorPickDialog.OnColorPickedListener() {
            @Override
            public void onClick(String colorHex) {
                Toast.makeText(context, String.valueOf(colorHex), Toast.LENGTH_SHORT).show();
            }
        }).show();
    }

    interface ColorPickerCallback {
        void onColorSelected(String colorHex);
    }
}
