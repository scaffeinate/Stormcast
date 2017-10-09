package io.stormcast.app.stormcast.views.navdrawer;

import android.content.Context;
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
import io.stormcast.app.stormcast.views.styled.StyledTextView;

/**
 * Created by sudharti on 10/1/17.
 */

public class NavDrawerAdapter extends ArrayAdapter<NavDrawerItem> {

    protected final static int VIEW_TYPE_LIST_ITEM = 0;
    protected final static int VIEW_TYPE_DIVIDER = 1;
    private List<NavDrawerItem> mNavDrawerItemList;
    private LayoutInflater inflater;

    public NavDrawerAdapter(Context context, List<NavDrawerItem> navDrawerItemList) {
        super(context, 0, navDrawerItemList);
        this.mNavDrawerItemList = navDrawerItemList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        NavDrawerItem navDrawerItem = mNavDrawerItemList.get(position);
        return navDrawerItem.isDivider() ? VIEW_TYPE_DIVIDER : VIEW_TYPE_LIST_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        final NavDrawerItem navDrawerItem = mNavDrawerItemList.get(position);
        int viewType = getItemViewType(position);

        if (convertView == null) {
            viewHolder = new ViewHolder();

            switch (viewType) {
                case VIEW_TYPE_DIVIDER:
                    convertView = inflater.inflate(R.layout.item_divider, null);
                    break;
                case VIEW_TYPE_LIST_ITEM:
                    convertView = inflater.inflate(R.layout.item_nav_drawer_list, null);

                    viewHolder.mTitle = (StyledTextView) convertView.findViewById(R.id.title_text_view);
                    viewHolder.mIcon = (ImageView) convertView.findViewById(R.id.icon_image_view);
                    break;
            }

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (viewType == VIEW_TYPE_LIST_ITEM) {
            viewHolder.mTitle.setText(navDrawerItem.getTitle());
            viewHolder.mIcon.setImageResource(navDrawerItem.getIcon());
        }

        return convertView;
    }

    class ViewHolder {
        private ImageView mIcon;
        private TextView mTitle;
    }
}
