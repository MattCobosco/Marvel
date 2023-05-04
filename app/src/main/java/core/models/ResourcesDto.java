package core.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResourcesDto<T> {
    @SerializedName("available")
    private int available;
    @SerializedName("returned")
    private int returned;
    @SerializedName("collectionURI")
    private String collectionUri;
    @SerializedName("items")
    private List<T> items;

    public int getAvailable() {
        return available;
    }

    public int getReturned() {
        return returned;
    }

    public String getCollectionUri() {
        return collectionUri;
    }

    public List<T> getItems() {
        return items;
    }
}
