package io.stormcast.app.stormcast.forecast;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.pwittchen.weathericonview.WeatherIconView;

import java.util.List;
import java.util.Map;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.models.DailyForecastModel;
import io.stormcast.app.stormcast.views.styled.StyledTextView;

/**
 * Created by sudharti on 10/2/17.
 */

public class DailyForecastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DailyForecastModel> dailyForecastModelList;
    private Context mContext;
    private ForecastFormatter mFormatter;
    private final int textColor;

    public DailyForecastAdapter(Context context, List<DailyForecastModel> dailyForecastModelList, ForecastFormatter formatter, int textColor) {
        this.mFormatter = formatter;
        this.mContext = context;
        this.dailyForecastModelList = dailyForecastModelList;
        this.textColor = textColor;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        DailyForecastModel dailyForecastModel = dailyForecastModelList.get(position);
        mFormatter.formatDailyForecast(dailyForecastModel, new ForecastFormatter.ForecastFormatterCallback() {
            @Override
            public void onFormatForecast(Map<String, String> formattedMap) {
                String icon = formattedMap.get(ForecastFormatter.ICON);
                String temperature = formattedMap.get(ForecastFormatter.TEMPERATURE);
                if (icon != null) {
                    viewHolder.mWeatherIconView.setIconResource(mContext.getResources().getString(Integer.parseInt(icon)));
                }

                if (temperature != null) {
                    viewHolder.mTemperatureTextView.setText(temperature);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dailyForecastModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final WeatherIconView mWeatherIconView;
        private final StyledTextView mTimeTextView, mTemperatureTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mWeatherIconView = (WeatherIconView) itemView.findViewById(R.id.weather_icon_view);
            mTimeTextView = (StyledTextView) itemView.findViewById(R.id.time_text_view);
            mTemperatureTextView = (StyledTextView) itemView.findViewById(R.id.temperature_text_view);

            mWeatherIconView.setIconColor(textColor);
            mTimeTextView.setTextColor(textColor);
            mTemperatureTextView.setTextColor(textColor);
        }
    }
}
