package core.api.clients;

import java.io.IOException;
import java.util.Map;

import core.api.MarvelApiConfig;
import core.api.models.DTOs.BaseResponse;
import core.api.models.DTOs.SerieDto;
import core.api.models.DTOs.SeriesDto;
import core.api.queries.SeriesQuery;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

public class SeriesClient extends BaseClient {
    public SeriesClient(final MarvelApiConfig marvelApiConfig) {
        super(marvelApiConfig);
    }

    public BaseResponse<SeriesDto> getAll(final SeriesQuery seriesQuery)
        throws IOException {
        final SeriesApiRest api = this.getApi(SeriesApiRest.class);

        final Map<String, Object> queryAsMap = seriesQuery.toMap();
        final Call<BaseResponse<SeriesDto>> call = api.getSeries(queryAsMap);
        return this.execute(call);
    }

    public BaseResponse<SerieDto> getSerie(final String serieId) throws HttpException, IOException {
        if (null == serieId || serieId.isEmpty()) {
            throw new IllegalArgumentException("The SeriesId must not be null or empty");
        }
        final SeriesApiRest api = this.getApi(SeriesApiRest.class);

        final Call<BaseResponse<SeriesDto>> call = api.getSerie(serieId);
        final BaseResponse<SeriesDto> series = this.execute(call);
        final SeriesDto seriesDto = series.getResponse();
        if (null != seriesDto && 0 < seriesDto.getCount()) {
            final SerieDto serieDto = seriesDto.getSeries().get(0);
            final BaseResponse<SerieDto> seriesResponse = new BaseResponse<>(series);
            seriesResponse.setResponse(serieDto);
            return seriesResponse;
        } else {
            throw new HttpException(Response.error(404, null));
        }
    }
}
