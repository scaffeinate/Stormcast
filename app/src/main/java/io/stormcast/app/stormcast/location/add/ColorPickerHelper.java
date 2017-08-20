package io.stormcast.app.stormcast.location.add;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

/**
 * Created by sudharti on 8/18/17.
 */

public class ColorPickerHelper {

    protected static void showColorPicker(Context context, int defaultColor, final ColorPickerCallback callback) {
        ColorPickerDialogBuilder.with(context)
                .setTitle("Choose color")
                .initialColor(ContextCompat.getColor(context, defaultColor))
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        callback.onColorSelected(String.format("#%06X", (0xFFFFFF & color)));
                    }
                })
                .setPositiveButton("Ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    interface ColorPickerCallback {
        void onColorSelected(String colorHex);
    }
}
