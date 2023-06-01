package core.api;

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

    public MarvelApiConfig(final String publicKey, final String privateKey, final Retrofit retrofit) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.retrofit = retrofit;
    }

    public static MarvelApiConfig with(final String publicKey, final String privateKey, final Retrofit retrofit) {
        if (null == singleton) {
            MarvelApiConfig.singleton = new MarvelApiConfig(publicKey, privateKey, retrofit);
        }

        return MarvelApiConfig.singleton;
    }

    public Retrofit getRetrofit() {
        return this.retrofit;
    }

    public static class Builder {
        private String baseUrl = "http://gateway.marvel.com/v1/public/";
        private final String publicKey;
        private final String privateKey;
        private Retrofit retrofit;
        private final TimeProvider timeProvider = new TimeProvider();


        public Builder(final String publicKey, final String privateKey) {
            if (null == publicKey || null == privateKey) {
                throw new IllegalArgumentException("publicKey and privateKey cannot be null");
            }

            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public Builder baseUrl(final String baseUrl) {
            if (null == baseUrl) {
                throw new IllegalArgumentException("baseUrl cannot be null");
            }

            this.baseUrl = baseUrl;
            return this;
        }

        public Builder retrofit(final Retrofit retrofit) {
            if (null == retrofit) {
                throw new IllegalArgumentException("retrofit cannot be null");
            }

            this.retrofit = retrofit;
            return this;
        }

        public MarvelApiConfig build() {
            if(null == retrofit) {
                this.retrofit = this.buildRetrofit();
            }

            return new MarvelApiConfig(this.publicKey, this.privateKey, this.retrofit);
        }

        private Retrofit buildRetrofit() {
            final OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(this.publicKey, this.privateKey, this.timeProvider));


            final OkHttpClient client = builder.build();

            return new Retrofit.Builder().baseUrl(this.baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }
}
