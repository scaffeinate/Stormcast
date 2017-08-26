package io.stormcast.app.stormcast.location.add;

import io.stormcast.app.stormcast.views.colorpick.MaterialColorPickDialog;

/**
 * Created by sudharti on 8/18/17.
 */

public class ColorPickerHelper {

    protected static void showColorPicker(MaterialColorPickDialog.Builder builder, final ColorPickerCallback callback) {
        builder.setTheme(MaterialColorPickDialog.ColorPickTheme.THEME_DARK)
                .build()
                .setOnColorPickedListener(new MaterialColorPickDialog.OnColorPickedListener() {
                    @Override
                    public void onClick(String colorHex) {
                        callback.onColorSelected(colorHex);
                    }
                }).show();
    }

    interface ColorPickerCallback {
        void onColorSelected(String colorHex);
    }
}
