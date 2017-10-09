package io.stormcast.app.stormcast.forecast;

/**
 * Created by sudharti on 10/8/17.
 */

public class NerdStat {
    private int icon;
    private String label, value;

    public NerdStat(int icon, String label, String value) {
        this.icon = icon;
        this.label = label;
        this.value = value;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
