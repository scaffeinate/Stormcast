package io.stormcast.app.stormcast.location.list;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.Location;

/**
 * Created by sudhar on 8/15/17.
 */
public class LocationsListAdapter extends RecyclerView.Adapter<LocationsListAdapter.ViewHolder> {

    private List<Location> mLocationList;

    public LocationsListAdapter(List<Location> locationList) {
        this.mLocationList = locationList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Location location = mLocationList.get(position);
        if (location != null) {
            holder.nameTextView.setText(location.getName());
            GradientDrawable background = (GradientDrawable) holder.bgColorImageView.getBackground();
            background.setColor(Color.parseColor(location.getBackgroundColor()));
            background = (GradientDrawable) holder.textColorImageView.getBackground();
            background.setColor(Color.parseColor(location.getTextColor()));
        }
    }

    @Override
    public int getItemCount() {
        return mLocationList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final View parentView;
        private final TextView nameTextView;
        private final ImageView bgColorImageView, textColorImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.parentView = itemView;

            nameTextView = (TextView) itemView.findViewById(R.id.location_name_text_view);
            bgColorImageView = (ImageView) itemView.findViewById(R.id.bg_color_image_view);
            textColorImageView = (ImageView) itemView.findViewById(R.id.text_color_image_view);
        }
    }
}
