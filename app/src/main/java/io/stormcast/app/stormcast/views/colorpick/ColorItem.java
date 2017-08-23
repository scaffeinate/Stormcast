package io.stormcast.app.stormcast.views.colorpick;

/**
 * Created by sudharti on 8/22/17.
 */

public class ColorItem {
    private String color;
    private boolean isSelected;

    public ColorItem(String color, boolean isSelected) {
        this.color = color;
        this.isSelected = isSelected;
    }

    public String getColor() {
        return color;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
