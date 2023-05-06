package core;

import core.utils.AuthInterceptor;
import core.utils.TimeProvider;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MarvelApiConfig {
    private static MarvelApiConfig singleton;
    private final String publicKey;
    private final String privateKey;
    private final Retrofit retrofit;

    public MarvelApiConfig(String publicKey, String privateKey, Retrofit retrofit) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.retrofit = retrofit;
    }

    public static MarvelApiConfig with(String publicKey, String privateKey, Retrofit retrofit) {
        if (singleton == null) {
            singleton = new MarvelApiConfig(publicKey, privateKey, retrofit);
        }

        return singleton;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static class Builder {
        private String baseUrl = "http://gateway.marvel.com/v1/public/";
        private final String publicKey;
        private final String privateKey;
        private Retrofit retrofit;
        private final TimeProvider timeProvider = new TimeProvider();


        public Builder(String publicKey, String privateKey) {
            if (publicKey == null || privateKey == null) {
                throw new IllegalArgumentException("publicKey and privateKey cannot be null");
            }

            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public Builder baseUrl(String baseUrl) {
            if (baseUrl == null) {
                throw new IllegalArgumentException("baseUrl cannot be null");
            }

            this.baseUrl = baseUrl;
            return this;
        }

        public Builder retrofit(Retrofit retrofit) {
            if (retrofit == null) {
                throw new IllegalArgumentException("retrofit cannot be null");
            }

            this.retrofit = retrofit;
            return this;
        }

        public MarvelApiConfig build() {
            if(retrofit == null) {
                retrofit = buildRetrofit();
            }

            return new MarvelApiConfig(publicKey, privateKey, retrofit);
        }

        private Retrofit buildRetrofit() {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(publicKey, privateKey, timeProvider));


            OkHttpClient client = builder.build();

            return new Retrofit.Builder().baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }
}
