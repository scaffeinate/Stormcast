package io.stormcast.app.stormcast.data.forecast.remote;

import io.stormcast.app.stormcast.common.network.Forecast;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sudharti on 8/22/17.
 */

public interface DarkSkyAPI {
    @GET("forecast/{apiKey}/{latLng}")
    Call<Forecast> loadForecast(@Path("apiKey") String apiKey, @Path("latLng") String latLng,
                                @Query("exclude") String exclude, @Query("units") String units);
}
