package core.api.models.DTOs;

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
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getModified() {
        return this.modified;
    }

    public String getResourceUri() {
        return this.resourceUri;
    }

    public List<UrlDto> getUrls() {
        return this.urls;
    }

    public ImageDto getThumbnail() {
        return this.thumbnail;
    }

    public ResourcesDto<ComicResourceDto> getComics() {
        return this.comics;
    }

    public ResourcesDto<StoryResourceDto> getStories() {
        return this.stories;
    }

    public ResourcesDto<EventResourceDto> getEvents() {
        return this.events;
    }

    public ResourcesDto<SeriesResourceDto> getSeries() {
        return this.series;
    }
}
