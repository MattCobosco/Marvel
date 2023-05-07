package core.api.models.DTOs;

import com.google.gson.annotations.SerializedName;

public class UrlDto {
    @SerializedName("type")
    private String type;
    @SerializedName("url")
    private String url;

    public String getType() {
        return this.type;
    }

    public String getUrl() {
        return this.url;
    }
}
