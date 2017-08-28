package io.stormcast.app.stormcast.forecast;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.pwittchen.weathericonview.WeatherIconView;

import java.util.List;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.models.HourlyModel;

/**
 * Created by sudharti on 8/28/17.
 */

public class HourlyForecastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HourlyModel> mHourlyModelList;
    private int mTextColor;

    public HourlyForecastAdapter(List<HourlyModel> hourlyModelList, int textColor) {
        this.mHourlyModelList = hourlyModelList;
        this.mTextColor = textColor;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hourly, null);
        return new HourlyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HourlyViewHolder hourlyViewHolder = ((HourlyViewHolder) holder);
        HourlyModel hourlyModel = this.mHourlyModelList.get(position);
        if (hourlyModel != null) {
            android.text.format.DateFormat dateFormat = new android.text.format.DateFormat();
            String formattedTime = dateFormat.format("HH:mm", hourlyModel.getTime() * 1000).toString();
            hourlyViewHolder.mTimeTextView.setText(formattedTime);
            hourlyViewHolder.mTemperatureTextView.setText(hourlyModel.getTemperature().toString());

            hourlyViewHolder.mTemperatureTextView.setTextColor(mTextColor);
            hourlyViewHolder.mTimeTextView.setTextColor(mTextColor);
            hourlyViewHolder.mWeatherIconView.setIconColor(mTextColor);
        }
    }

    @Override
    public int getItemCount() {
        return this.mHourlyModelList.size();
    }

    class HourlyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTemperatureTextView;
        private WeatherIconView mWeatherIconView;
        private TextView mTimeTextView;

        public HourlyViewHolder(View itemView) {
            super(itemView);
            mTimeTextView = (TextView) itemView.findViewById(R.id.time_text_view);
            mWeatherIconView = (WeatherIconView) itemView.findViewById(R.id.weather_icon_view);
            mTemperatureTextView = (TextView) itemView.findViewById(R.id.temperature_text_view);
        }
    }
}
