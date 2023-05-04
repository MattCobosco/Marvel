package core.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharacterDto {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("modified")
    private String modified;
    @SerializedName("resourceURI")
    private String resourceUri;
    @SerializedName("urls")
    private List<UrlDto> urls;
    @SerializedName("thumbnail")
    private ImageDto thumbnail;
    @SerializedName("comics")
    private ResourcesDto<ComicResourceDto> comics;
    @SerializedName("stories")
    private ResourcesDto<StoryResourceDto> stories;
    @SerializedName("events")
    private ResourcesDto<EventResourceDto> events;
    @SerializedName("series")
    private ResourcesDto<SeriesResourceDto> series;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getModified() {
        return modified;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public List<UrlDto> getUrls() {
        return urls;
    }

    public ImageDto getThumbnail() {
        return thumbnail;
    }

    public ResourcesDto<ComicResourceDto> getComics() {
        return comics;
    }

    public ResourcesDto<StoryResourceDto> getStories() {
        return stories;
    }

    public ResourcesDto<EventResourceDto> getEvents() {
        return events;
    }

    public ResourcesDto<SeriesResourceDto> getSeries() {
        return series;
    }
}
