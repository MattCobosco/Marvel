package core.api.clients;

import java.util.Map;

import core.api.models.DTOs.BaseResponse;
import core.api.models.DTOs.CharactersDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface CharacterApiRest {
    @GET("characters") Call<BaseResponse<CharactersDto>> getCharacters(
            @QueryMap Map<String, Object> characterFilter);

    @GET("characters/{id}")
    Call<BaseResponse<CharactersDto>> getCharacter(
            @Path("id") String characterId);
}
