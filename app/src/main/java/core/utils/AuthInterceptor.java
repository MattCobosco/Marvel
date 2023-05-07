package core.utils;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private static final String TIMESTAMP_KEY = "ts";
    private static final String HASH_KEY = "hash";
    private static final String APIKEY_KEY = "apikey";

    private final String publicKey;
    private final String privateKey;
    private final TimeProvider timeProvider;
    private final AuthHashGenerator authHashGenerator = new AuthHashGenerator();

    public AuthInterceptor(final String publicKey, final String privateKey, final TimeProvider timeProvider) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.timeProvider = timeProvider;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull final Chain chain) throws IOException {
        final String timestamp = String.valueOf(this.timeProvider.currentTimeMillis());
        String hash = null;

        try {
            hash = this.authHashGenerator.generateHash(timestamp, this.publicKey, this.privateKey);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        Request request = chain.request();
        final HttpUrl url = request.url()
                .newBuilder()
                .addQueryParameter(AuthInterceptor.TIMESTAMP_KEY, timestamp)
                .addQueryParameter(AuthInterceptor.APIKEY_KEY, this.publicKey)
                .addQueryParameter(AuthInterceptor.HASH_KEY, hash)
                .build();

        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
