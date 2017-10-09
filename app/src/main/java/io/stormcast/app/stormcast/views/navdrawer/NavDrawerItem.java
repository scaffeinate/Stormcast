package io.stormcast.app.stormcast.views.navdrawer;

/**
 * Created by sudharti on 10/1/17.
 */

public class NavDrawerItem {
    private int icon;
    private String title;
    private boolean isDivider;

    public NavDrawerItem() {
        this(0, null, true);
    }

    public NavDrawerItem(int icon, String title) {
        this(icon, title, false);
    }

    private NavDrawerItem(int icon, String title, boolean isDivider) {
        this.icon = icon;
        this.title = title;
        this.isDivider = isDivider;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDivider() {
        return isDivider;
    }

    public void setDivider(boolean divider) {
        isDivider = divider;
    }
}
