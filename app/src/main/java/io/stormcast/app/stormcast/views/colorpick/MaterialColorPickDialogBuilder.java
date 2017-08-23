package io.stormcast.app.stormcast.views.colorpick;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.annotation.Retention;

import io.stormcast.app.stormcast.R;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by sudhar on 8/22/17.
 */

public class MaterialColorPickDialogBuilder implements DialogInterface.OnClickListener {

    private static MaterialColorPickDialogBuilder builder;

    private static final String DEFAULT_TITLE = "Color Picker";
    private static final String DEFAULT_POSITIVE_TEXT = "Ok";
    private static final String DEFAULT_NEGATIVE_TEXT = "Cancel";

    private static final String THEME_LIGHT_BG_COLOR = "#FFFFFF";
    private static final String THEME_DARK_BG = "#404040";
    private static final String THEME_LIGHT_TEXT_COLOR = "#000000";
    private static final String THEME_DARK_TEXT_COLOR = "#F7F7F7";
    private static final int THEME_LIGHT_ICON = R.drawable.ic_eyedropper_variant_grey600_24dp;
    private static final int THEME_DARK_ICON = R.drawable.ic_eyedropper_variant_white_24dp;

    @Retention(SOURCE)
    @IntDef({ColorPickTheme.THEME_LIGHT, ColorPickTheme.THEME_DARK})
    public @interface ColorPickTheme {
        int THEME_LIGHT = 0;
        int THEME_DARK = 1;
    }

    private Context mContext;
    private String title = DEFAULT_TITLE;
    private int theme = ColorPickTheme.THEME_LIGHT;
    private int icon = THEME_LIGHT_ICON;
    private String positiveText = DEFAULT_POSITIVE_TEXT;
    private String negativeText = DEFAULT_NEGATIVE_TEXT;

    private AlertDialog.Builder mDialogBuilder;
    private AlertDialog mAlertDialog;

    private RelativeLayout mLayout;
    private TextView mTitle;
    private GridView mColorsGridView;

    private OnColorPickedListener mOnColorPickedListener;

    private MaterialColorPickDialogBuilder(Context context) {
        this.mContext = context;
    }

    public static MaterialColorPickDialogBuilder with(Context context) {
        builder = new MaterialColorPickDialogBuilder(context);
        return builder;
    }

    public static MaterialColorPickDialogBuilder setTheme(@ColorPickTheme int theme) {
        builder.theme = theme;
        return builder;
    }

    public static MaterialColorPickDialogBuilder setTitle(String title) {
        builder.title = title;
        return builder;
    }

    public static MaterialColorPickDialogBuilder setIcon(int icon) {
        builder.icon = icon;
        return builder;
    }

    public static MaterialColorPickDialogBuilder setOnColorPickedListener(OnColorPickedListener onColorPickedListener) {
        builder.mOnColorPickedListener = onColorPickedListener;
        return builder;
    }

    public static MaterialColorPickDialogBuilder build() {
        builder.mDialogBuilder = new AlertDialog.Builder(builder.mContext);
        View view = builder.getView(builder.mContext);
        builder.mDialogBuilder.setView(view);
        builder.mDialogBuilder.setPositiveButton(builder.positiveText, builder);
        builder.mDialogBuilder.setNegativeButton(builder.negativeText, builder);

        builder.mTitle = (TextView) view.findViewById(R.id.pick_a_color_text_view);
        builder.mColorsGridView = (GridView) view.findViewById(R.id.colors_grid_view);
        builder.mLayout = (RelativeLayout) view.findViewById(R.id.material_color_pick_layout);

        builder.setupTheme(builder);

        builder.mAlertDialog = builder.mDialogBuilder
                .setTitle(builder.title)
                .setIcon(builder.icon)
                .create();

        return builder;
    }

    public static void show() {
        builder.mAlertDialog.show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                mOnColorPickedListener.onClick("");
                break;
        }
    }

    private static void setupTheme(MaterialColorPickDialogBuilder builder) {
        switch (builder.theme) {
            case ColorPickTheme.THEME_LIGHT:
                builder.mLayout.setBackgroundColor(Color.parseColor(THEME_LIGHT_BG_COLOR));
                builder.mTitle.setTextColor(Color.parseColor(THEME_LIGHT_TEXT_COLOR));
                builder.setIcon(THEME_LIGHT_ICON);
                break;
            case ColorPickTheme.THEME_DARK:
                builder.mLayout.setBackgroundColor(Color.parseColor(THEME_DARK_BG));
                builder.mTitle.setTextColor(Color.parseColor(THEME_DARK_TEXT_COLOR));
                builder.setIcon(THEME_DARK_ICON);
                break;
        }
    }

    private View getView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_color_picker, null);
        return view;
    }

    public interface OnColorPickedListener {
        void onClick(String colorHex);
    }
}
