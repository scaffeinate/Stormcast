package io.stormcast.app.stormcast.forecast;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.views.styled.StyledTextView;

/**
 * Created by sudharti on 10/8/17.
 */

public class NerdStatsAdapter extends RecyclerView.Adapter<NerdStatsAdapter.ViewHolder> {

    private List<NerdStat> nerdStatList;
    private int textColor;

    public NerdStatsAdapter() {
        nerdStatList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nerd_stat, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NerdStat nerdStat = nerdStatList.get(position);
        if (nerdStat != null) {
            holder.bind(nerdStat);
        }
    }

    @Override
    public int getItemCount() {
        return this.nerdStatList.size();
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setNerdStatList(List<NerdStat> nerdStatList) {
        this.nerdStatList = nerdStatList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mIconImageView;
        private final StyledTextView mLabelTextView, mValueTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mIconImageView = (ImageView) itemView.findViewById(R.id.nerd_stat_icon_image_view);
            mLabelTextView = (StyledTextView) itemView.findViewById(R.id.nerd_stat_label_text_view);
            mValueTextView = (StyledTextView) itemView.findViewById(R.id.nerd_stat_value_text_view);
        }

        private void bind(NerdStat nerdStat) {
            mIconImageView.setImageResource(nerdStat.getIcon());
            mLabelTextView.setText(nerdStat.getLabel());
            mValueTextView.setText(nerdStat.getValue());

            mIconImageView.setColorFilter(textColor);
            mLabelTextView.setTextColor(textColor);
            mValueTextView.setTextColor(textColor);
        }
    }
}
