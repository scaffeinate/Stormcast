package io.stormcast.app.stormcast.location.list;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by sudharti on 9/24/17.
 */

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private ItemTouchHelperAdapter mAdapter;

    public ItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        this.mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
