package core.api.queries;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeriesQuery {
    private static final String QUERY_TITLE = "title";
    private static final String QUERY_TITLE_START_WITH = "titleStartsWith";
    private static final String QUERY_MODIFIED_SINCE = "modifiedSince";
    private static final String QUERY_COMICS = "comics";
    private static final String QUERY_STORIES = "stories";
    private static final String QUERY_EVENTS = "events";
    private static final String QUERY_CREATORS = "creators";
    private static final String QUERY_CHARACTERS = "characters";
    private static final String QUERY_SERIES_TYPE = "seriesType";
    private static final String QUERY_CONTAINS = "contains";
    private static final String QUERY_ORDER_BY = "orderBy";
    private static final String QUERY_LIMIT = "limit";
    private static final String QUERY_OFFSET = "offset";

    private final String title;
    private final String titleStartWith;
    private final String modifiedSince;
    private final String comics;
    private final String stories;
    private final String events;
    private final String creators;
    private final String characters;
    private final String seriesType;
    private final String contains;
    private final String orderBy;
    private final int limit;
    private final int offset;

    public SeriesQuery(String title, String titleStartWith, String modifiedSince, String comics, String stories, String events, String creators, String characters, String seriesType, String contains, String orderBy, int limit, int offset) {
        this.title = title;
        this.titleStartWith = titleStartWith;
        this.modifiedSince = modifiedSince;
        this.comics = comics;
        this.stories = stories;
        this.events = events;
        this.creators = creators;
        this.characters = characters;
        this.seriesType = seriesType;
        this.contains = contains;
        this.orderBy = orderBy;
        this.limit = limit;
        this.offset = offset;
    }

    public Map<String, Object> toMap() {
        final Map<String, Object> returnValues = new HashMap<>();

        if (null != title) {
            returnValues.put(QUERY_TITLE, this.title);
        }

        if (null != titleStartWith) {
            returnValues.put(QUERY_TITLE_START_WITH, this.titleStartWith);
        }

        if (null != modifiedSince) {
            returnValues.put(QUERY_MODIFIED_SINCE, this.modifiedSince);
        }

        if (null != comics) {
            returnValues.put(QUERY_COMICS, this.comics);
        }

        if (null != stories) {
            returnValues.put(QUERY_STORIES, this.stories);
        }

        if (null != events) {
            returnValues.put(QUERY_EVENTS, this.events);
        }

        if (null != creators) {
            returnValues.put(QUERY_CREATORS, this.creators);
        }

        if (null != characters) {
            returnValues.put(QUERY_CHARACTERS, this.characters);
        }

        if (null != seriesType) {
            returnValues.put(QUERY_SERIES_TYPE, this.seriesType);
        }

        if (null != this.contains) {
            returnValues.put(QUERY_CONTAINS, this.contains);
        }

        if (null != orderBy) {
            returnValues.put(QUERY_ORDER_BY, this.orderBy);
        }

        if (0 < limit) {
            returnValues.put(QUERY_LIMIT, this.limit);
        }

        if (0 < offset) {
            returnValues.put(QUERY_OFFSET, this.offset);
        }

        return returnValues;
    }

    public static class Builder {
        private String title;
        private String titleStartsWith;
        private Date modifiedSince;
        private List<Integer> comics = new ArrayList<>();
        private List<Integer> stories = new ArrayList<>();
        private List<Integer> events = new ArrayList<>();
        private List<Integer> creators = new ArrayList<>();
        private List<Integer> characters = new ArrayList<>();
        private String seriesType;
        private String contains;
        private String orderBy;

        private boolean orderByAscending;
        private int limit;
        private int offset;

        public static Builder create() {
            return new Builder();
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withTitleStartsWith(String titleStartsWith) {
            this.titleStartsWith = titleStartsWith;
            return this;
        }

        public Builder withModifiedSince(Date modifiedSince) {
            this.modifiedSince = modifiedSince;
            return this;
        }

        public Builder addComic(final int comic) {
            this.comics.add(comic);
            return this;
        }

        public Builder addComics(final List<Integer> comics) {
            Utils.checkNull(comics);
            this.comics.addAll(comics);
            return this;
        }

        public Builder addStory(final int story) {
            this.stories.add(story);
            return this;
        }

        public Builder addStories(final List<Integer> stories) {
            Utils.checkNull(stories);
            this.stories.addAll(stories);
            return this;
        }

        public Builder addEvent(final int event) {
            this.events.add(event);
            return this;
        }

        public Builder addEvents(final List<Integer> events) {
            Utils.checkNull(events);
            this.events.addAll(events);
            return this;
        }

        public Builder addCreator(final int creator) {
            this.creators.add(creator);
            return this;
        }

        public Builder addCreators(final List<Integer> creators) {
            Utils.checkNull(creators);
            this.creators.addAll(creators);
            return this;
        }

        public Builder addCharacter(final int character) {
            this.characters.add(character);
            return this;
        }

        public Builder addCharacters(final List<Integer> characters) {
            Utils.checkNull(characters);
            this.characters.addAll(characters);
            return this;
        }

        public Builder withSeriesType(final String seriesType) {
            this.seriesType = seriesType;
            return this;
        }

        public Builder withContains(final String contains) {
            this.contains = contains;
            return this;
        }

        public Builder withOrderBy(final String orderBy) {
            this.orderBy = orderBy;
            orderByAscending = true;
            return this;
        }

        public Builder withOrderBy(final String orderBy, final boolean orderByAscending) {
            this.orderBy = orderBy;
            this.orderByAscending = orderByAscending;
            return this;
        }

        public Builder withLimit(final int limit) {
            Utils.checkLimit(limit);
            this.limit = limit;
            return this;
        }

        public Builder withOffset(final int offset) {
            this.offset = offset;
            return this;
        }

        public SeriesQuery build() {
            final String plainModifiedSince = Utils.convertDate(this.modifiedSince);
            final String plainComics = Utils.convertToList(this.comics);
            final String plainStories = Utils.convertToList(this.stories);
            final String plainEvents = Utils.convertToList(this.events);
            final String plainCreators = Utils.convertToList(this.creators);
            final String plainCharacters = Utils.convertToList(this.characters);
            final String plainOrderBy = Utils.convertOrderBy(this.orderBy, this.orderByAscending);

            return new SeriesQuery(this.title, this.titleStartsWith, plainModifiedSince, plainComics, plainStories,
                    plainEvents, plainCreators, plainCharacters, this.seriesType, this.contains, plainOrderBy, this.limit, this.offset);
        }
    }
}
