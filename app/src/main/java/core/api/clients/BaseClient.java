package core.api.clients;

import java.io.IOException;

import core.api.MarvelApiConfig;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

public class BaseClient {
    private final MarvelApiConfig marvelApiConfig;

    public BaseClient(final MarvelApiConfig marvelApiConfig) {
        this.marvelApiConfig = marvelApiConfig;
    }

    <T> T getApi(final Class<T> apiRest) {
        return this.marvelApiConfig.getRetrofit().create(apiRest);
    }

    public <T> T execute(final Call<T> call) throws IOException {
        Response<T> response;
        try {
            response = call.execute();
        } catch (final IOException e) {
            throw new IOException("Network error", e);
        }
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new HttpException(response);
        }
    }

}
