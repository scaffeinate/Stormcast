package io.stormcast.app.stormcast.views.colorpick;

import android.app.AlertDialog;
import android.content.Context;
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

public class MaterialColorPickDialogBuilder {

    private static MaterialColorPickDialogBuilder builder;
    private static final String DEFAULT_TITLE = "Color Picker";

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
    private int theme = -1;
    private int icon = 0;

    private AlertDialog.Builder mDialogBuilder;
    private AlertDialog mAlertDialog;

    private RelativeLayout mLayout;
    private TextView mTitle;
    private GridView mColorsGridView;

    private MaterialColorPickDialogBuilder(Context context, @ColorPickTheme int theme) {
        this.mContext = context;
        this.theme = theme;
    }

    public static MaterialColorPickDialogBuilder with(Context context) {
        return with(context, ColorPickTheme.THEME_LIGHT);
    }

    public static MaterialColorPickDialogBuilder with(Context context, @ColorPickTheme int theme) {
        builder = new MaterialColorPickDialogBuilder(context, theme);
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

    public static MaterialColorPickDialogBuilder build() {
        builder.mDialogBuilder = new AlertDialog.Builder(builder.mContext);
        View view = builder.getView(builder.mContext);
        builder.mDialogBuilder.setView(view);

        builder.mTitle = (TextView) view.findViewById(R.id.pick_a_color_text_view);
        builder.mColorsGridView = (GridView) view.findViewById(R.id.colors_grid_view);
        builder.mLayout = (RelativeLayout) view.findViewById(R.id.material_color_pick_layout);

        builder.setupTheme(builder.theme);

        builder.mAlertDialog = builder.mDialogBuilder
                .setTitle(builder.title)
                .setIcon(builder.icon)
                .create();

        return builder;
    }

    public static void show() {
        builder.mAlertDialog.show();
    }

    private void setupTheme(int theme) {
        switch (theme) {
            case ColorPickTheme.THEME_LIGHT:
                break;
            case ColorPickTheme.THEME_DARK:
                break;
        }
    }

    private View getView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_color_picker, null);
        return view;
    }
}
