package io.stormcast.app.stormcast.views.colorpick;

import android.app.Activity;
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

    private static final List<ColorItem> sColorItems = new ArrayList<>();

    public static Builder with(Context context) {
        if (sColorItems.isEmpty()) {
            getColors(context);
        }

        return new Builder(context);
    }

    private static void getColors(Context context) {
        String[] colors = context.getResources().getStringArray(R.array.colors);
        for (String color : colors) {
            sColorItems.add(new ColorItem(color, false));
        }
    }

    public static class Builder implements AdapterView.OnItemClickListener {

        private Context mContext;

        private String title = null;
        private int theme = ColorPickTheme.THEME_LIGHT;
        private int icon = THEME_LIGHT_ICON;

        private AlertDialog.Builder mDialogBuilder;
        private AlertDialog mAlertDialog;

        private RelativeLayout mLayout;
        private TextView mTitle;
        private GridView mColorsGridView;
        private MaterialColorGridAdapter mAdapter;

        private OnColorPickedListener mOnColorPickedListener = null;
        private List<ColorItem> mColorsList;

        private int mSelectedIndex = -1;

        protected Builder(Context context) {
            this.mContext = context;
            this.mColorsList = deepCopy(sColorItems);
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

            if (this.title != null) {
                mDialogBuilder.setTitle(title);
            }

            this.setupTheme();
            this.fillGrid();

            this.mAlertDialog = this.mDialogBuilder.create();
            return this;
        }

        public void show() {
            if (mContext instanceof Activity) {
                Activity activity = (Activity) mContext;
                if (!activity.isFinishing() && !activity.isDestroyed()) {
                    this.mAlertDialog.show();
                    this.limitHeight();
                }
            }
        }

        private View getView() {
            LayoutInflater inflater = LayoutInflater.from(this.mContext);
            View view = inflater.inflate(R.layout.dialog_color_picker, null);
            return view;
        }

        private void fillGrid() {
            this.mAdapter = new MaterialColorGridAdapter(this.mContext, mColorsList);
            this.mColorsGridView.setAdapter(mAdapter);
            this.mColorsGridView.setOnItemClickListener(this);
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

        private List<ColorItem> deepCopy(List<ColorItem> colorItems) {
            List<ColorItem> copyList = new ArrayList<>();
            for (int i = 0; i < colorItems.size(); i++) {
                ColorItem colorItem = colorItems.get(i);
                copyList.add(new ColorItem(colorItem.getColor(), colorItem.isSelected()));
            }
            return copyList;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            if (this.mOnColorPickedListener != null) {
                ColorItem colorItem = mColorsList.get(position);
                if (colorItem != null) {
                    if (mSelectedIndex != -1) {
                        ColorItem selectedItem = mColorsList.get(mSelectedIndex);
                        selectedItem.setSelected(false);
                    }

                    colorItem.setSelected(true);
                    mSelectedIndex = position;
                    mAdapter.notifyDataSetChanged();

                    this.mAlertDialog.dismiss();
                    this.mOnColorPickedListener.onClick(colorItem.getColor());
                }
            }
        }
    }

    public interface OnColorPickedListener {
        void onClick(String colorHex);
    }
}
