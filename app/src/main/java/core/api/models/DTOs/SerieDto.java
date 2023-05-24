package core.api.models.DTOs;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SerieDto {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("resourceURI")
    private String resourceUri;

    @SerializedName("urls")
    private List<UrlDto> urls;

    @SerializedName("startYear")
    private int startYear;

    @SerializedName("endYear")
    private int endYear;

    @SerializedName("rating")
    private String rating;

    @SerializedName("modified")
    private String modified;

    @SerializedName("thumbnail")
    private ImageDto thumbnail;

    @SerializedName("comics")
    private ResourcesDto<ComicResourceDto> comics;

    @SerializedName("stories")
    private ResourcesDto<StoryResourceDto> stories;

    @SerializedName("events")
    private ResourcesDto<EventResourceDto> events;

    @SerializedName("characters")
    private ResourcesDto<CharacterResourceDto> characters;

    @SerializedName("creators")
    private ResourcesDto<CreatorResourceDto> creators;

    @SerializedName("next")
    private SeriesResourceDto next;

    @SerializedName("previous")
    private SeriesResourceDto previous;

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getResourceUri() {
        return this.resourceUri;
    }

    public List<UrlDto> getUrls() {
        return this.urls;
    }

    public int getStartYear() {
        return this.startYear;
    }

    public int getEndYear() {
        return this.endYear;
    }

    public String getRating() {
        return this.rating;
    }

    public String getModified() {
        return this.modified;
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

    public ResourcesDto<CharacterResourceDto> getCharacters() {
        return this.characters;
    }

    public ResourcesDto<CreatorResourceDto> getCreators() {
        return this.creators;
    }

    public SeriesResourceDto getNext() {
        return this.next;
    }

    public SeriesResourceDto getPrevious() {
        return this.previous;
    }
}
