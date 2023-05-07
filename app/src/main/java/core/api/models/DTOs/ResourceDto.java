package core.api.models.DTOs;

import com.google.gson.annotations.SerializedName;

public class ResourceDto {
    @SerializedName("resourceURI")
    private String resourceUri;
    @SerializedName("name")
    private String name;

    public String getResourceUri() {
        return this.resourceUri;
    }

    public String getName() {
        return this.name;
    }
}
