package core;

public class MarvelApiConfig {
    private static MarvelApiConfig singleton;
    private final String publicKey;
    private final String privateKey;

    public MarvelApiConfig(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public static MarvelApiConfig with(String publicKey, String privateKey) {
        if(singleton == null) {
            singleton = new MarvelApiConfig(publicKey, privateKey);
        }

        return singleton;
    }
}
