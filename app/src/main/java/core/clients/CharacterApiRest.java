package core.clients;

import java.util.Map;

import core.models.DTOs.BaseResponse;
import core.models.DTOs.CharactersDto;
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
