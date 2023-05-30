package core.api.models.DTOs;

import com.google.gson.annotations.SerializedName;

public class CreatorDto {
    @SerializedName("id")
    private String id;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("middleName")
    private String middleName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("suffix")
    private String suffix;
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("modified")
    private String modified;
    @SerializedName("resourceURI")
    private String resourceUri;
    @SerializedName("urls")
    private UrlDto[] urls;
    @SerializedName("thumbnail")
    private ImageDto thumbnail;
    @SerializedName("series")
    private ResourcesDto<SeriesResourceDto> series;
    @SerializedName("stories")
    private ResourcesDto<StoryResourceDto> stories;
    @SerializedName("comics")
    private ResourcesDto<ComicResourceDto> comics;
    @SerializedName("events")
    private ResourcesDto<EventResourceDto> events;

    public String getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getModified() {
        return this.modified;
    }

    public String getResourceUri() {
        return this.resourceUri;
    }

    public UrlDto[] getUrls() {
        return this.urls;
    }

    public ImageDto getThumbnail() {
        return this.thumbnail;
    }

    public ResourcesDto<SeriesResourceDto> getSeries() {
        return this.series;
    }

    public ResourcesDto<StoryResourceDto> getStories() {
        return this.stories;
    }

    public ResourcesDto<ComicResourceDto> getComics() {
        return this.comics;
    }

    public ResourcesDto<EventResourceDto> getEvents() {
        return this.events;
    }
}
