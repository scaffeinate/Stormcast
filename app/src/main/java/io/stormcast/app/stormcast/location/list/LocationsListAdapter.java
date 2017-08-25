package io.stormcast.app.stormcast.location.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.models.LocationModel;

/**
 * Created by sudhar on 8/15/17.
 */
public class LocationsListAdapter extends RecyclerView.Adapter<LocationsListAdapter.ViewHolder> {

    private List<LocationModel> mLocationModelList;

    public LocationsListAdapter(List<LocationModel> locationModelList) {
        this.mLocationModelList = locationModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LocationModel locationModel = mLocationModelList.get(position);
        if (locationModel != null) {
            holder.nameTextView.setText(locationModel.getName());
        }
    }

    @Override
    public int getItemCount() {
        return mLocationModelList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final View parentView;
        private final TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.parentView = itemView;

            nameTextView = (TextView) itemView.findViewById(R.id.location_name_text_view);
        }
    }
}
