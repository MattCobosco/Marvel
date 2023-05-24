package core.api.clients;

import java.util.Map;

import core.api.models.DTOs.BaseResponse;
import core.api.models.DTOs.SeriesDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface SeriesApiRest {
    @GET("series")
    Call<BaseResponse<SeriesDto>> getSeries(
            @QueryMap Map<String, Object> seriesFilter);

    @GET("series/{id}")
    Call<BaseResponse<SeriesDto>> getSerie(
            @Path("id") String seriesId);
}
