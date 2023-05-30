package core.api.clients;

import java.util.Map;

import core.api.models.DTOs.BaseResponse;
import core.api.models.DTOs.CreatorsDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface CreatorApiRest {
    @GET("creators")
    Call<BaseResponse<CreatorsDto>> getCreators(
        @QueryMap
        Map<String, Object> creatorFilter);

    @GET("creators/{id}")
    Call<BaseResponse<CreatorsDto>> getCreator(
            @Path("id") String creatorId);

}
