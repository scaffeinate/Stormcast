package io.stormcast.app.stormcast.data.forecast.remote;

import io.stormcast.app.stormcast.common.models.LocationModel;
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

    private DarkSkyApiClient() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        darkSkyApi = mRetrofit.create(DarkSkyAPI.class);
    }

    public static DarkSkyApiClient getInstance() {
        if (mDarkSkyApiClient == null) {
            mDarkSkyApiClient = new DarkSkyApiClient();
        }

        return mDarkSkyApiClient;
    }

    public void loadForecast(LocationModel locationModel, final ApiCallback apiCallback) {
        String latLng = new StringBuilder()
                .append(locationModel.getLatitude())
                .append(",")
                .append(locationModel.getLongitude())
                .toString();
        String exclude = "minutely";
        String units = (locationModel.getUnit() == LocationModel.UNIT_AUTO) ? "auto" : (locationModel.getUnit() == LocationModel.UNIT_IMPERIAL) ? "us" : "si";
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
