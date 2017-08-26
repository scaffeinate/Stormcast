package io.stormcast.app.stormcast.views.colorpick;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.annotation.IntDef;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.List;

import io.stormcast.app.stormcast.R;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by sudhar on 8/22/17.
 */

public class MaterialColorPickDialog {

    private static Builder mBuilder;

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

    private static final List<ColorItem> mColorItems = new ArrayList<>();

    public static Builder with(Context context) {
        if (mBuilder == null) {
            getColors(context);
            mBuilder = new Builder(context);
        }
        return mBuilder;
    }

    private static void getColors(Context context) {
        String[] colors = context.getResources().getStringArray(R.array.colors);
        for (String color : colors) {
            mColorItems.add(new ColorItem(color, false));
        }
    }

    public static class Builder implements AdapterView.OnItemClickListener {

        private Context mContext;

        private String title = DEFAULT_TITLE;
        private int theme = ColorPickTheme.THEME_LIGHT;
        private int icon = THEME_LIGHT_ICON;

        private AlertDialog.Builder mDialogBuilder;
        private AlertDialog mAlertDialog;

        private RelativeLayout mLayout;
        private TextView mTitle;
        private GridView mColorsGridView;
        private MaterialColorGridAdapter mAdapter;

        private OnColorPickedListener mOnColorPickedListener = null;

        protected Builder(Context context) {
            this.mContext = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setIcon(int icon) {
            this.icon = icon;
            return this;
        }

        public Builder setTheme(@ColorPickTheme int theme) {
            this.theme = theme;
            return this;
        }

        public Builder setOnColorPickedListener(OnColorPickedListener onColorPickedListener) {
            this.mOnColorPickedListener = onColorPickedListener;
            return this;
        }

        public Builder build() {
            this.mDialogBuilder = new AlertDialog.Builder(this.mContext);
            View view = this.getView();

            this.mDialogBuilder.setView(view);

            this.mTitle = (TextView) view.findViewById(R.id.pick_a_color_text_view);
            this.mColorsGridView = (GridView) view.findViewById(R.id.colors_grid_view);
            this.mLayout = (RelativeLayout) view.findViewById(R.id.material_color_pick_layout);
            this.mColorsGridView.setOnItemClickListener(this);

            this.setupTheme();
            this.fillGrid();

            this.mAlertDialog = this.mDialogBuilder.create();
            return this;
        }

        public void show() {
            this.mAlertDialog.show();
            this.limitHeight();
        }

        private View getView() {
            LayoutInflater inflater = LayoutInflater.from(this.mContext);
            View view = inflater.inflate(R.layout.dialog_color_picker, null);
            return view;
        }

        private void fillGrid() {
            this.mAdapter = new MaterialColorGridAdapter(this.mContext, mColorItems);
            this.mColorsGridView.setAdapter(mAdapter);
        }

        private void limitHeight() {
            WindowManager windowManager = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
            if (windowManager != null) {
                Display display = windowManager.getDefaultDisplay();
                if (display != null) {
                    Point size = new Point();
                    display.getSize(size);
                    this.mAlertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (0.65 * size.y));
                }
            }
        }

        private void setupTheme() {
            switch (this.theme) {
                case ColorPickTheme.THEME_LIGHT:
                    this.mLayout.setBackgroundColor(Color.parseColor(THEME_LIGHT_BG_COLOR));
                    this.mTitle.setTextColor(Color.parseColor(THEME_LIGHT_TEXT_COLOR));
                    this.setIcon(THEME_LIGHT_ICON);
                    break;
                case ColorPickTheme.THEME_DARK:
                    this.mLayout.setBackgroundColor(Color.parseColor(THEME_DARK_BG));
                    this.mTitle.setTextColor(Color.parseColor(THEME_DARK_TEXT_COLOR));
                    this.setIcon(THEME_DARK_ICON);
                    break;
            }
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            switch (view.getId()) {
                case R.id.colors_grid_view:
                    if (this.mOnColorPickedListener != null) {
                        this.mOnColorPickedListener.onClick(mColorItems.get(position).getColor());
                    }
                    break;
            }
        }
    }

    public interface OnColorPickedListener {
        void onClick(String colorHex);
    }
}
