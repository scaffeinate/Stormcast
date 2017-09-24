package io.stormcast.app.stormcast.views.toggle;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.views.styled.StyledButton;

/**
 * Created by sudharti on 9/24/17.
 */

public class ToggleSwitch extends LinearLayout {

    private Context mContext;

    public ToggleSwitch(Context context) {
        this(context, null);
    }

    public ToggleSwitch(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToggleSwitch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setLayoutMode(LinearLayout.HORIZONTAL);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(mContext, R.drawable.toggle_switch);
        setBackground(drawable);

        addButton("Auto", true);
        addButton("Metric", false);
        addButton("Imperial", false);
    }

    private void addButton(String text, boolean active) {
        StyledButton styledButton = (StyledButton) LayoutInflater.from(mContext).inflate(R.layout.toggle_switch_button, null);
        styledButton.setText(text);
        //styledButton.setBackgroundColor(ContextCompat.getColor(mContext, transparent));
        styledButton.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));

        addView(styledButton);
    }
}
