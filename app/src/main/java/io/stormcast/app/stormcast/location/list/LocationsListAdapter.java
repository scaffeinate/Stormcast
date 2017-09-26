package io.stormcast.app.stormcast.location.list;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.location.list.helpers.OnStartDragListener;
import io.stormcast.app.stormcast.views.styled.StyledTextView;

/**
 * Created by sudhar on 8/15/17.
 */
public class LocationsListAdapter extends RecyclerView.Adapter<LocationsListAdapter.ViewHolder> {

    private List<LocationModel> mLocationModelList;
    private OnStartDragListener mOnStartDragListener;

    public LocationsListAdapter(List<LocationModel> locationModelList, OnStartDragListener onStartDragListener) {
        this.mLocationModelList = locationModelList;
        this.mOnStartDragListener = onStartDragListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        LocationModel locationModel = mLocationModelList.get(position);
        if (locationModel != null) {
            holder.nameTextView.setText(locationModel.getName());
        }

        holder.reorderIcon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEventCompat.getActionMasked(motionEvent) == MotionEvent.ACTION_DOWN) {
                    mOnStartDragListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLocationModelList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final StyledTextView nameTextView;
        private final ImageView reorderIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (StyledTextView) itemView.findViewById(R.id.location_name_text_view);
            reorderIcon = (ImageView) itemView.findViewById(R.id.reorder_icon);
        }
    }
}
