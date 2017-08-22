package io.stormcast.app.stormcast.views.colorpick;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import io.stormcast.app.stormcast.R;

/**
 * Created by sudhar on 8/22/17.
 */

public class MaterialColorPickDialogBuilder {

    private static MaterialColorPickDialogBuilder builder;
    private static final String TITLE = "Color Picker";
    private static final int THEME_LIGHT = 0;
    private static final int THEME_DARK = 1;
    private static final int THEME_LIGHT_ICON = R.drawable.ic_eyedropper_variant_grey600_24dp;
    private static final int THEME_DARK_ICON = R.drawable.ic_eyedropper_variant_white_24dp;

    private int theme = 0;

    private AlertDialog.Builder mDialogBuilder;
    private AlertDialog mAlertDialog;

    private TextView mTitle;
    private GridView mColorsGridView;

    private MaterialColorPickDialogBuilder(Context context, int theme) {
        this.mDialogBuilder = new AlertDialog.Builder(context);
        this.theme = theme;

        View view = getView(context);
        this.mDialogBuilder.setView(view);
        this.mTitle = (TextView) view.findViewById(R.id.pick_a_color_text_view);
        this.mColorsGridView = (GridView) view.findViewById(R.id.colors_grid_view);
    }

    public static MaterialColorPickDialogBuilder with(Context context) {
        return withTheme(context, THEME_LIGHT);
    }

    public static MaterialColorPickDialogBuilder withTheme(Context context, int theme) {
        builder = new MaterialColorPickDialogBuilder(context, theme);
        return builder;
    }

    public static MaterialColorPickDialogBuilder setTheme(int theme) {
        builder.theme = theme;
        return builder;
    }

    public static MaterialColorPickDialogBuilder setTitle(String title) {
        builder.mDialogBuilder.setTitle(title);
        return builder;
    }

    public static MaterialColorPickDialogBuilder setIcon(int icon) {
        builder.mDialogBuilder.setIcon(R.drawable.ic_eyedropper_variant_white_24dp);
        return builder;
    }

    public static MaterialColorPickDialogBuilder build() {
        builder.mAlertDialog = builder.mDialogBuilder.create();
        return builder;
    }

    public static void show() {
        builder.mAlertDialog.show();
    }

    private View getView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_color_picker, null);
        return view;
    }
}
