package io.stormcast.app.stormcast.home;

/**
 * Created by sudharti on 10/1/17.
 */

public interface CustomizeCallbacks {

    void setToolbarTitle(String title);

    void setToolbarTextColor(int color);

    void setToolbarBackgroundColor(int color);

    void setNavDrawerHeaderBackgroundColor(int color);

    void setNavDrawerSelected(int position);
}
