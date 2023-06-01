package core.api.clients;

import java.io.IOException;
import java.util.Map;

import core.api.MarvelApiConfig;
import core.api.models.DTOs.BaseResponse;
import core.api.models.DTOs.CreatorDto;
import core.api.models.DTOs.CreatorsDto;
import core.api.queries.CreatorsQuery;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

public class CreatorClient extends BaseClient {
    public CreatorClient(final MarvelApiConfig marvelApiConfig) {
        super(marvelApiConfig);
    }

    public BaseResponse<CreatorsDto> getAll(final CreatorsQuery creatorsQuery)
            throws IOException {
        final CreatorApiRest api = this.getApi(CreatorApiRest.class);

        final Map<String, Object> queryAsMap = creatorsQuery.toMap();
        final Call<BaseResponse<CreatorsDto>> call = api.getCreators(queryAsMap);
        return this.execute(call);
    }

    public BaseResponse<CreatorDto> getCreator(final String creatorId) throws HttpException, IOException {
        if(null == creatorId || creatorId.isEmpty()) {
            throw new IllegalArgumentException("The CreatorId must not be null or empty");
        }
        final CreatorApiRest api = this.getApi(CreatorApiRest.class);

        final Call<BaseResponse<CreatorsDto>> call = api.getCreator(creatorId);
        final BaseResponse<CreatorsDto> creators = this.execute(call);
        final CreatorsDto creatorsDto = creators.getResponse();
        if(null != creatorsDto && 0 < creatorsDto.getCount()) {
            final CreatorDto creatorDto = creatorsDto.getCreators().get(0);
            final BaseResponse<CreatorDto> creatorResponse = new BaseResponse<>(creators);
            creatorResponse.setResponse(creatorDto);
            return creatorResponse;
        } else {
            throw new HttpException(Response.error(404, null));
        }
    }
}
