package core.clients;

import java.io.IOException;

import core.MarvelApiConfig;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

public class BaseClient {
    private final MarvelApiConfig marvelApiConfig;

    public BaseClient(MarvelApiConfig marvelApiConfig) {
        this.marvelApiConfig = marvelApiConfig;
    }

    <T> T getApi(Class<T> apiRest) {
        return marvelApiConfig.getRetrofit().create(apiRest);
    }

    public <T> T execute(Call<T> call) throws IOException {
        Response<T> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new IOException("Network error", e);
        }
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new HttpException(response);
        }
    }

}
