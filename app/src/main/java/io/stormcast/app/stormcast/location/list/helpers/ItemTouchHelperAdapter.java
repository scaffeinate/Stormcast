package io.stormcast.app.stormcast.location.list.helpers;

/**
 * Created by sudharti on 9/24/17.
 */

public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
