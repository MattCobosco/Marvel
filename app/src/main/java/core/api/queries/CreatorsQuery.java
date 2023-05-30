package core.api.queries;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreatorsQuery {
    private static final String QUERY_FIRST_NAME = "firstName";
    private static final String QUERY_MIDDLE_NAME = "middleName";
    private static final String QUERY_LAST_NAME = "lastName";
    private static final String QUERY_SUFFIX = "suffix";
    private static final String QUERY_MODIFIED_SINCE = "modifiedSince";
    private static final String QUERY_COMICS = "comics";
    private static final String QUERY_SERIES = "series";
    private static final String QUERY_EVENTS = "events";
    private static final String QUERY_STORIES = "stories";
    private static final String QUERY_ORDER_BY = "orderBy";
    private static final String QUERY_LIMIT = "limit";
    private static final String QUERY_OFFSET = "offset";

    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final String suffix;
    private final String modifiedSince;
    private final String comics;
    private final String series;
    private final String events;
    private final String stories;
    private final String orderBy;
    private final int limit;
    private final int offset;

    private CreatorsQuery(final String firstName, final String middleName, final String lastName, final String suffix,
                          final String modifiedSince, final String comics, final String series, final String events,
                          final String stories, final String orderBy, final int limit, final int offset) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.suffix = suffix;
        this.modifiedSince = modifiedSince;
        this.comics = comics;
        this.series = series;
        this.events = events;
        this.stories = stories;
        this.orderBy = orderBy;
        this.limit = limit;
        this.offset = offset;
    }

    public Map<String, Object> toMap() {
        final Map<String, Object> returnValues = new HashMap<>();

        if (null != firstName) {
            returnValues.put(QUERY_FIRST_NAME, this.firstName);
        }

        if (null != middleName) {
            returnValues.put(QUERY_MIDDLE_NAME, this.middleName);
        }

        if (null != lastName) {
            returnValues.put(QUERY_LAST_NAME, this.lastName);
        }

        if (null != suffix) {
            returnValues.put(QUERY_SUFFIX, this.suffix);
        }

        if (null != modifiedSince) {
            returnValues.put(QUERY_MODIFIED_SINCE, this.modifiedSince);
        }

        if (null != comics) {
            returnValues.put(QUERY_COMICS, this.comics);
        }

        if (null != series) {
            returnValues.put(QUERY_SERIES, this.series);
        }

        if (null != events) {
            returnValues.put(QUERY_EVENTS, this.events);
        }

        if (null != stories) {
            returnValues.put(QUERY_STORIES, this.stories);
        }

        if (null != orderBy) {
            returnValues.put(QUERY_ORDER_BY, this.orderBy);
        }

        if (0 != limit) {
            returnValues.put(QUERY_LIMIT, this.limit);
        }

        if (0 != offset) {
            returnValues.put(QUERY_OFFSET, this.offset);
        }

        return returnValues;
    }

    public static class Builder {
        private String firstName;
        private String middleName;
        private String lastName;
        private String suffix;
        private Date modifiedSince;
        private final List<Integer> comics = new ArrayList<>();
        private final List<Integer> series = new ArrayList<>();
        private final List<Integer> events = new ArrayList<>();
        private final List<Integer> stories = new ArrayList<>();
        private String orderBy;
        private int limit;
        private int offset;

        public static Builder create() {
            return new Builder();
        }

        public Builder withFirstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withMiddleName(final String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Builder withLastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withSuffix(final String suffix) {
            this.suffix = suffix;
            return this;
        }

        public Builder withModifiedSince(final Date modifiedSince) {
            this.modifiedSince = modifiedSince;
            return this;
        }

        public Builder withComics(final List<Integer> comics) {
            this.comics.addAll(comics);
            return this;
        }

        public Builder withSeries(final List<Integer> series) {
            this.series.addAll(series);
            return this;
        }

        public Builder withEvents(final List<Integer> events) {
            this.events.addAll(events);
            return this;
        }

        public Builder withStories(final List<Integer> stories) {
            this.stories.addAll(stories);
            return this;
        }

        public Builder withOrderBy(final String orderBy) {
            this.orderBy = orderBy;
            return this;
        }

        public Builder withLimit(final int limit) {
            this.limit = limit;
            return this;
        }

        public Builder withOffset(final int offset) {
            this.offset = offset;
            return this;
        }

        public CreatorsQuery build() {
            final String plainModifiedSince = Utils.convertDate(this.modifiedSince);
            final String plainComics = Utils.convertToList(this.comics);
            final String plainSeries = Utils.convertToList(this.series);
            final String plainStories = Utils.convertToList(this.stories);
            final String plainEvents = Utils.convertToList(this.events);

            return new CreatorsQuery(this.firstName, this.middleName, this.lastName, this.suffix, plainModifiedSince,
                                     plainComics, plainSeries, plainStories, plainEvents, this.orderBy, this.limit, this.offset);
        }

    }
}
