package core.api.clients;

import java.io.IOException;
import java.util.Map;

import core.api.MarvelApiConfig;
import core.api.models.DTOs.BaseResponse;
import core.api.models.DTOs.CharacterDto;
import core.api.models.DTOs.CharactersDto;
import core.api.queries.CharactersQuery;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

public class CharacterClient extends BaseClient {
    public CharacterClient(final MarvelApiConfig marvelApiConfig) {
        super(marvelApiConfig);
    }

    public BaseResponse<CharactersDto> getAll(final CharactersQuery charactersQuery)
            throws IOException {
        final CharacterApiRest api = this.getApi(CharacterApiRest.class);

        final Map<String, Object> queryAsMap = charactersQuery.toMap();
        final Call<BaseResponse<CharactersDto>> call = api.getCharacters(queryAsMap);
        return this.execute(call);
    }

    public BaseResponse<CharacterDto> getCharacter(final String characterId) throws HttpException, IOException {
        if (null == characterId || characterId.isEmpty()) {
            throw new IllegalArgumentException("The CharacterId must not be null or empty");
        }
        final CharacterApiRest api = this.getApi(CharacterApiRest.class);

        final Call<BaseResponse<CharactersDto>> call = api.getCharacter(characterId);
        final BaseResponse<CharactersDto> characters = this.execute(call);
        final CharactersDto charactersDto = characters.getResponse();
        if (null != charactersDto && 0 < charactersDto.getCount()) {
            final CharacterDto characterDto = charactersDto.getCharacters().get(0);
            final BaseResponse<CharacterDto> characterResponse = new BaseResponse<>(characters);
            characterResponse.setResponse(characterDto);
            return characterResponse;
        } else {
            throw new HttpException(Response.error(404, null));
        }
    }
}
