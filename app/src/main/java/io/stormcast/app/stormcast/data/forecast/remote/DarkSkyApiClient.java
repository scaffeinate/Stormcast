package io.stormcast.app.stormcast.data.forecast.remote;

import io.stormcast.app.stormcast.common.models.Location;
import io.stormcast.app.stormcast.common.network.Forecast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sudharti on 8/22/17.
 */

public class DarkSkyApiClient {
    public static final String BASE_URL = "https://api.darksky.net/";
    private static final String API_KEY = "API_KEY";
    private static DarkSkyApiClient mDarkSkyApiClient;
    private static Retrofit mRetrofit;
    private static DarkSkyAPI darkSkyApi;

    public static DarkSkyApiClient getInstance() {
        if (mDarkSkyApiClient == null) {
            mDarkSkyApiClient = new DarkSkyApiClient();
        }

        return mDarkSkyApiClient;
    }

    private DarkSkyApiClient() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        darkSkyApi = mRetrofit.create(DarkSkyAPI.class);
    }

    public void loadForecast(Location location, final ApiCallback apiCallback) {
        String latLng = new StringBuilder()
                .append(location.getLatitude())
                .append(",")
                .append(location.getLongitude())
                .toString();
        String exclude = "minutely";
        String units = (location.getUnit() == Location.UNIT_AUTO) ? "auto" : (location.getUnit() == Location.UNIT_IMPERIAL) ? "us" : "si";
        darkSkyApi.loadForecast(API_KEY, latLng, exclude, units).enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                apiCallback.onLoadForecast(response.body());
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                apiCallback.onFailure(t.getMessage());
            }
        });
    }

    interface ApiCallback {
        void onLoadForecast(Forecast forecast);
        void onFailure(String errorMessage);
    }
}
