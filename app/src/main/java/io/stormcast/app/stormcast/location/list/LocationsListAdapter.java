package io.stormcast.app.stormcast.location.list;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.Location;

/**
 * Created by sudhar on 8/15/17.
 */
public class LocationsListAdapter extends ArrayAdapter<Location> {

    private List<Location> mLocationList;
    private Context mContext;

    public LocationsListAdapter(@NonNull Context context, List<Location> locationList) {
        super(context, 0, locationList);
        this.mLocationList = locationList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_location, null);
            viewHolder = new ViewHolder();

            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.location_name_text_view);
            viewHolder.bgColorImageView = (ImageView) convertView.findViewById(R.id.bg_color_image_view);
            viewHolder.textColorImageView = (ImageView) convertView.findViewById(R.id.text_color_image_view);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Location location = mLocationList.get(position);
        if (location != null) {
            viewHolder.nameTextView.setText(location.getName());
            GradientDrawable background = (GradientDrawable) viewHolder.bgColorImageView.getBackground();
            background.setColor(Color.parseColor(location.getBackgroundColor()));
            background = (GradientDrawable) viewHolder.textColorImageView.getBackground();
            background.setColor(Color.parseColor(location.getTextColor()));
        }

        return convertView;
    }

    static class ViewHolder {
        private TextView nameTextView;
        private ImageView bgColorImageView, textColorImageView;
    }
}
