package core.clients;

import java.io.IOException;
import java.util.Map;

import core.MarvelApiConfig;
import core.models.DTOs.BaseResponse;
import core.models.DTOs.CharacterDto;
import core.models.DTOs.CharactersDto;
import core.queries.CharactersQuery;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

public class CharacterClient extends BaseClient {
    public CharacterClient(MarvelApiConfig marvelApiConfig) {
        super(marvelApiConfig);
    }

    public BaseResponse<CharactersDto> getAll(int offset, int limit) throws IOException {
        CharactersQuery query =
                CharactersQuery.Builder.create().withOffset(offset).withLimit(limit).build();
        return getAll(query);
    }

    public BaseResponse<CharactersDto> getAll(CharactersQuery charactersQuery)
            throws IOException {
        CharacterApiRest api = getApi(CharacterApiRest.class);

        Map<String, Object> queryAsMap = charactersQuery.toMap();
        Call<BaseResponse<CharactersDto>> call = api.getCharacters(queryAsMap);
        return execute(call);
    }

    public BaseResponse<CharacterDto> getCharacter(String characterId) throws HttpException, IOException {
        if (characterId == null || characterId.isEmpty()) {
            throw new IllegalArgumentException("The CharacterId must not be null or empty");
        }
        CharacterApiRest api = getApi(CharacterApiRest.class);

        Call<BaseResponse<CharactersDto>> call = api.getCharacter(characterId);
        BaseResponse<CharactersDto> characters = execute(call);
        CharactersDto charactersDto = characters.getResponse();
        if (charactersDto != null && charactersDto.getCount() > 0) {
            CharacterDto characterDto = charactersDto.getCharacters().get(0);
            BaseResponse<CharacterDto> characterResponse = new BaseResponse<>(characters);
            characterResponse.setResponse(characterDto);
            return characterResponse;
        } else {
            throw new HttpException(Response.error(404, null));
        }
    }
}
